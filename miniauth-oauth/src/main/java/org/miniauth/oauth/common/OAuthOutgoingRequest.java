package org.miniauth.oauth.common;

import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.common.RequestBase;
import org.miniauth.core.AuthScheme;
import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.InternalErrorException;
import org.miniauth.exception.InvalidStateException;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.nonce.NonceGenerator;
import org.miniauth.oauth.util.ParameterTransmissionUtil;


/**
 * Client side request, which is being built by the client, for services that require OAuth.
 * Note that OAuthOutgoingRequest does not represent a full request.
 * It's just a partial wrapper for attributes that are needed for OAuth signing.
 */
public class OAuthOutgoingRequest extends OutgoingRequest
{
    private static final Logger log = Logger.getLogger(OAuthOutgoingRequest.class.getName());
    private static final long serialVersionUID = 1L;
    
    // TBD:
    private volatile String authParamTransmissionType = null;
    // ...

    // State variables.
    private boolean endorsed = false;
    
    // OAuth parameter wrapper. "Cache".
    // This can be generated based on the internal attributes.
    // And, it can be overridden by a setter (endorse()), which can make it "out of sync" with the itnernal attributes.
    private volatile OAuthParamMap oauthParamMap = null;
    // private AccessIdentity accessIdentity = null;    // Just use oauthParamMap.accessIdentity


    // Note that Ctor's are not public.
    // Use the builder class to create OAuthOutgoingRequest objects.
    protected OAuthOutgoingRequest()
    {
        this(null, null);
    }
    protected OAuthOutgoingRequest(String httpMethod, URI baseURI)
    {
        this(httpMethod, baseURI, null, null, null);
    }
    protected OAuthOutgoingRequest(String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> formParams,
            Map<String, String[]> queryParams)
    {
        super(httpMethod, baseURI, authHeader, formParams, queryParams);
        // initAuthParamTransmissionType();
    }
    protected OAuthOutgoingRequest(RequestBase request)
    {
        super(request);
        // initAuthParamTransmissionType();
    }

    // TBD:
    // Calling this in ctor's does not work.
    // We need to call this every time setters are called for authHeader, formParams, and queryParams...
    // --> we'll need to call this in getAuthParamTransmissionType().
    protected void initAuthParamTransmissionType()
    {
        // TBD:
        try {
            authParamTransmissionType = ParameterTransmissionUtil.getTransmissionType(getAuthHeader(), getFormParams(), getQueryParams());
        } catch (MiniAuthException e) {
            // ???
            log.log(Level.INFO, "Failed to detect authParamTransmissionType.", e);
        }
        if(authParamTransmissionType == null) {
            authParamTransmissionType = ParameterTransmissionUtil.getDefaultTransmissionType();
        }
    }
    protected void setAuthParamTransmissionType(String authParamTransmissionType) throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. authParamTransmissionType cannot be changed.");
        }
        this.authParamTransmissionType = authParamTransmissionType;
    }
    // TBD: We need a better way....
    public String getAuthParamTransmissionType()
    {
        if(!isEndorsed()) {
            initAuthParamTransmissionType();
        }
        return authParamTransmissionType;
    }

    
    // TBD: Who calls this method?
    // Build OAuthParamMap (excluding the signature)...
    protected void buildOAuthParamMap() throws MiniAuthException
    {
        buildOAuthParamMap(null);
    }
    protected void buildOAuthParamMap(AccessIdentity accessIdentity) throws MiniAuthException
    {
        oauthParamMap = OAuthParamMapUtil.buildOAuthParams(this, accessIdentity);
        initAuthParamTransmissionType();

        // TBD:
        // buildOAuthParams() might have changed some of (oauth_x) param values.
        // We need to update the internal vars based on the new oauthParamMap
        // because signature is generated based on authHeader/formParams/queryParams, but on oauthParamMap;
        // TBD: --> to utilize oauthParamMap in generating signature???
        refreshHeadersAndParams();

        setReady(true);
    }
    public OAuthParamMap getOauthParamMap()
    {
        return oauthParamMap;
    }


    // Note that authHeader/formParams/queryParams are "primary" variables. oauthParamMap is a "derived" variable.
    // This method performs an action in the reverse direction.
    // It "refreshes" authHeader/formParams/queryParams based on the current oauthParamMap.
    // It's sort of the reverse of buildOAuthParamMap().
    private void refreshHeadersAndParams() throws MiniAuthException
    {
        switch(authParamTransmissionType) {
        case ParameterTransmissionType.HEADER:
            Map<String,String> newAuthHeader = OAuthRequestUtil.updateOAuthHeaderWithOAuthParamMap(getAuthHeader(), oauthParamMap);
            setAuthHeader(newAuthHeader);
            break;
        case ParameterTransmissionType.FORM:
            Map<String,String[]> newFormParams = OAuthRequestUtil.updateParamsWithOAuthParamMap(getFormParams(), oauthParamMap);
            setFormParams(newFormParams);
            break;
        case ParameterTransmissionType.QUERY:
            Map<String,String[]> newQueryParams = OAuthRequestUtil.updateParamsWithOAuthParamMap(getQueryParams(), oauthParamMap);
            setQueryParams(newQueryParams);
            break;
        default:
            // ??? This should not happen...
            throw new InternalErrorException("Invalid authParamTransmissionType: " + authParamTransmissionType);
        }
    }

    
    /**
     * Put this request object in a "ready" state.
     * This is the first of the two "state changing" operations.
     * @param accessIdentity A pair of consumer key/token to use in "preparing" this request.
     * @return this object.
     */
    @Override
    public OutgoingRequest prepare(AccessIdentity accessIdentity) throws MiniAuthException
    {
        // Note:
        // Regardless of the current state (ready==true|false, endorsed=true|false)
        // prepare() can be always called.
        setEndorsed(false);
        setReady(false);

        buildOAuthParamMap(accessIdentity);

        setReady(true);
        return this;
    }
    
    
    // The arg oauthParamMap should contain the generated signature
    //     as well as other required oauth_x params.
    public void endorse(OAuthParamMap oauthParamMap) throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Cannot endorse it again.");
        }
        if(! isReady()) {
            throw new InvalidStateException("The request is not prepared. Cannot perform endorse().");
        }

        // TBD: What about other OAuth required params ???
        if(oauthParamMap == null || ! oauthParamMap.isSignatureSet()) {
            // Can this happen???
            // If we have failed to generate a signature, 
            //    we should have thrown exception earlier in the call chain.
            throw new AuthSignatureException("Signature is not set. The request cannot be endorsed.");
        }

        if(this.oauthParamMap == null) {
            this.oauthParamMap = new OAuthParamMap(oauthParamMap);
        } else {
            this.oauthParamMap.updateParams(oauthParamMap);
        }
        
        // Now, we need to update the internal vars based on the new oauthParamMap
        refreshHeadersAndParams();

        setEndorsed(true);
    }

    /**
     * "Sign" this request object, 
     * and put this request in a "endorsed" state.
     * This is the second of the two "state changing" operations.
     * This can be called only if the current state == ready.
     * @param signature Signature to use for signing this request.
     * @return this object.
     */
    @Override
    public OutgoingRequest endorse(String signature) throws MiniAuthException
    {
//        if(isEndorsed()) {
//            throw new InvalidStateException("The request is already endorsed. Cannot endorse it again.");
//        }
//        if(! isReady()) {
//            throw new InvalidStateException("The request is not prepared. Cannot perform endorse().");
//        }
//
//        if(signature == null || signature.isEmpty()) {
//            throw new AuthSignatureException("Signature is not set. The request cannot be endorsed.");
//        }
//
//        if(this.oauthParamMap == null) {
//            this.oauthParamMap = new OAuthParamMap();
//        }
//        this.oauthParamMap.setSignature(signature);
//        
//        // Now, we need to update the internal vars based on the new oauthParamMap
//        refreshHeadersAndParams();
//
//        setEndorsed(true);
//        return this;

        OAuthParamMap oauthParamMap = new OAuthParamMap();
        oauthParamMap.setSignature(signature);
        endorse(oauthParamMap);
        return this;
    }

    
    
    /**
     * Returns true if this request has been "endorsed"
     *    (e.g., if it includes the oauth_signature param in the case of OAuth, etc.).
     * Note that the request can be endorsed only if it is "ready".
     * Also, once it's endorsed, it cannot be endorsed again or it cannot be changed.
     * @return the "endorsement" state of this request.
     */
    @Override
    public boolean isEndorsed()
    {
        return this.endorsed;
    }
    protected void setEndorsed(boolean endorsed)
    {
        this.endorsed = endorsed;
    }


    
    // This is necessary to make these setters accessible from the builder class.
    // Also, set ready to false whenver setters are used.

    @Override
    protected RequestBase setHttpMethod(String httpMethod)
            throws MiniAuthException
    {
        super.setHttpMethod(httpMethod);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase setBaseURI(URI baseURI) throws MiniAuthException
    {
        super.setBaseURI(baseURI);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase setBaseURI(String baseUri) throws MiniAuthException
    {
        super.setBaseURI(baseUri);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase setAuthHeader(String authHeaderStr)
            throws MiniAuthException
    {
        super.setAuthHeader(authHeaderStr, AuthScheme.OAUTH);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase setAuthHeader(Map<String, String> authHeader)
            throws MiniAuthException
    {
        super.setAuthHeader(authHeader);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase addAuthHeaderParam(String key, String value)
            throws MiniAuthException
    {
        super.addAuthHeaderParam(key, value);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase setFormParams(String formBody) throws MiniAuthException
    {
        super.setFormParams(formBody);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase setFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        super.setFormParams(formParams);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase addFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        super.addFormParams(formParams);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase addFormParam(String key, String value)
            throws MiniAuthException
    {
        super.addFormParam(key, value);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase setQueryParams(String queryString)
            throws MiniAuthException
    {
        super.setQueryParams(queryString);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase setQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        super.setQueryParams(queryParams);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase addQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        super.addQueryParams(queryParams);
        setReady(false);
        return this;
    }
    @Override
    protected RequestBase addQueryParam(String key, String value)
            throws MiniAuthException
    {
        super.addQueryParam(key, value);
        setReady(false);
        return this;
    }


    // For debugging...
    @Override
    public String toString()
    {
        return "OAuthOutgoingRequest [authParamTransmissionType="
                + authParamTransmissionType + ", endorsed=" + endorsed
                + ", oauthParamMap=" + oauthParamMap + ", getHttpMethod()="
                + getHttpMethod() + ", getBaseURI()=" + getBaseURI()
                + ", getAuthHeader()=" + getAuthHeader() + ", getFormParams()="
                + getFormParams() + ", getQueryParams()=" + getQueryParams()
                + "]";
    }

    
}
