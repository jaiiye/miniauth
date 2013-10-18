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
        initAuthParamTransmissionType();
    }
    protected OAuthOutgoingRequest(RequestBase request)
    {
        super(request);
        initAuthParamTransmissionType();
    }

    // TBD:
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
    protected String getAuthParamTransmissionType()
    {
        return authParamTransmissionType;
    }

    
    // TBD: Who calls this method?
    // Build OAuthParamMap (excluding the signature)...
    public void buildOAuthParamMap() throws MiniAuthException
    {
        buildOAuthParamMap(null);
    }
    public void buildOAuthParamMap(AccessIdentity accessIdentity) throws MiniAuthException
    {
        oauthParamMap = OAuthParamMapUtil.buildOAuthParams(this, accessIdentity);
        setReady(true);
    }
    public OAuthParamMap getOauthParamMap()
    {
        return oauthParamMap;
    }
    
    // The arg oauthParamMap should contain the generated signature
    //     as well as other required oauth_x params.
    public void endorse(OAuthParamMap oauthParamMap) throws MiniAuthException
    {
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
        
        setEndorsed(true);
    }


    @Override
    public RequestBase setAuthHeader(String authHeaderStr)
            throws MiniAuthException
    {
//        // Checked in super.
//        if(isEndorsed()) {
//            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
//        }
        return super.setAuthHeader(authHeaderStr, AuthScheme.OAUTH);
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

    
    
}
