package org.miniauth.oauth.common;

import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.IncomingRequest;
import org.miniauth.common.RequestBase;
import org.miniauth.core.AuthScheme;
import org.miniauth.exception.BadRequestException;
import org.miniauth.oauth.util.ParameterTransmissionUtil;


/**
 * Provider side request, which is sent by a client, for services that use OAuth.
 * Note that OAuthIncomingRequest does not represent a full request.
 * It's just a partial wrapper for attributes that are needed for OAuth signature verification.
 */
public class OAuthIncomingRequest extends IncomingRequest
{
    private static final Logger log = Logger.getLogger(OAuthIncomingRequest.class.getName());
    private static final long serialVersionUID = 1L;
    
    // TBD:
    private volatile String authParamTransmissionType = null;
    // ...

//    // State variables.
//    private boolean endorsed = false;
    
    // OAuth parameter wrapper
    private OAuthParamMap oauthParamMap = null;
    // private AccessIdentity accessIdentity = null;    // Just use oauthParamMap.accessIdentity


    // Note that Ctor's are not public.
    // Use the builder class to create OAuthIncomingRequest objects.
    protected OAuthIncomingRequest()
    {
        this(null, null);
    }
    protected OAuthIncomingRequest(String httpMethod, URI baseURI)
    {
        this(httpMethod, baseURI, null, null, null);
    }
    protected OAuthIncomingRequest(String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> formParams,
            Map<String, String[]> queryParams)
    {
        super(httpMethod, baseURI, authHeader, formParams, queryParams);
        // initAuthParamTransmissionType();
    }
    protected OAuthIncomingRequest(RequestBase request)
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
            // What to do ???
            // This is an error...
            // authParamTransmissionType = ParameterTransmissionUtil.getDefaultTransmissionType();
            // TBD: throw exception ???
        }
    }
    // TBD: We need a better way....
    public String getAuthParamTransmissionType()
    {
        if(!isReady()) {
            initAuthParamTransmissionType();
        }
        return authParamTransmissionType;
    }

    
    // TBD: Who calls this method?
    // Build OAuthParamMap (including the signature)...
    public void buildOAuthParamMap() throws MiniAuthException
    {
        oauthParamMap = OAuthParamMapUtil.buildOAuthParams(this);
        initAuthParamTransmissionType();
        setReady(true);
    }
    public OAuthParamMap getOauthParamMap()
    {
        return oauthParamMap;
    }


    /**
     * Put this request object in a "ready" state.
     * The request object can be verified only if it's in a ready state.
     * @return this object.
     * @throws MiniAuthException if preparation fails, or the object cannot be put into the "ready" state.
     */
    @Override
    public IncomingRequest prepare() throws MiniAuthException
    {
        buildOAuthParamMap();
        return this;
    }

    /**
     * Put this request object in a "verified" state. 
     * This is the second of the two "state changing" operations.
     * This can be called only if the current state == ready.
     * Note that it does not actually "verify" anything.
     * The verification should be done by the caller.
     * @return this object.
     * @throws MiniAuthException if the IncomingRequest cannot be put into the "verified" state.
     */
    @Override
    public IncomingRequest verify() throws MiniAuthException
    {
        if(! isEndorsed()) {
            throw new BadRequestException("Cannot change the state to verfied because the IncomingRequest is not endorsed in the first place.");
        }
        return super.verify();
    }

    
    
    /**
     * Returns true if this incoming request has been "endorsed" (e.g., by the client/caller)
     *    (e.g., if it includes the oauth_signature param in the case of OAuth, etc.).
     *    Endorsed==true does not mean it's verified.
     * @return the "endorsement" state of this request.
     */
    @Override
    public boolean isEndorsed() throws MiniAuthException
    {
        // if(! isReady()) {
        //     return false;    // ????
        // }
        return (oauthParamMap != null && oauthParamMap.isSignatureSet());
//        return this.endorsed;
    }
//    protected void setEndorsed(boolean endorsed)
//    {
//        this.endorsed = endorsed;
//    }


    // This is necessary to make these setters accessible from the builder class.

    @Override
    protected RequestBase setHttpMethod(String httpMethod)
            throws MiniAuthException
    {
        return super.setHttpMethod(httpMethod);
    }
    @Override
    protected RequestBase setBaseURI(URI baseURI) throws MiniAuthException
    {
        return super.setBaseURI(baseURI);
    }
    @Override
    protected RequestBase setBaseURI(String baseUri) throws MiniAuthException
    {
        return super.setBaseURI(baseUri);
    }
    @Override
    protected RequestBase setAuthHeader(String authHeaderStr)
            throws MiniAuthException
    {
        super.setAuthHeader(authHeaderStr, AuthScheme.OAUTH);     // Note. Oauth hard-coded here.
        return this;
    }
    @Override
    protected RequestBase setAuthHeader(Map<String, String> authHeader)
            throws MiniAuthException
    {
        return super.setAuthHeader(authHeader);
    }
    @Override
    protected RequestBase addAuthHeaderParam(String key, String value)
            throws MiniAuthException
    {
        return super.addAuthHeaderParam(key, value);
    }
    @Override
    protected RequestBase setFormParams(String formBody)
            throws MiniAuthException
    {
        return super.setFormParams(formBody);
    }
    @Override
    protected RequestBase setFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        return super.setFormParams(formParams);
    }
    @Override
    protected RequestBase addFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        return super.addFormParams(formParams);
    }
    @Override
    protected RequestBase addFormParam(String key, String value)
            throws MiniAuthException
    {
        return super.addFormParam(key, value);
    }
    @Override
    protected RequestBase setQueryParams(String queryString)
            throws MiniAuthException
    {
        return super.setQueryParams(queryString);
    }
    @Override
    protected RequestBase setQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        return super.setQueryParams(queryParams);
    }
    @Override
    protected RequestBase addQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        return super.addQueryParams(queryParams);
    }
    @Override
    protected RequestBase addQueryParam(String key, String value)
            throws MiniAuthException
    {
        return super.addQueryParam(key, value);
    }


    
    // For debugging...
    @Override
    public String toString()
    {
        return "OAuthIncomingRequest [authParamTransmissionType="
                + authParamTransmissionType + ", oauthParamMap="
                + oauthParamMap + ", getHttpMethod()=" + getHttpMethod()
                + ", getBaseURI()=" + getBaseURI() + ", getAuthHeader()="
                + getAuthHeader() + ", getFormParams()=" + getFormParams()
                + ", getQueryParams()=" + getQueryParams() + "]";
    }
   
    
}
