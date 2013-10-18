package org.miniauth.oauth.common;

import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.IncomingRequest;
import org.miniauth.common.RequestBase;
import org.miniauth.core.AuthScheme;
import org.miniauth.oauth.service.OAuthRequestUtil;
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

    // State variables.
    private boolean endorsed = false;
    
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
        initAuthParamTransmissionType();
    }
    protected OAuthIncomingRequest(RequestBase request)
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
            // What to do ???
            // This is an error...
            // authParamTransmissionType = ParameterTransmissionUtil.getDefaultTransmissionType();
            // TBD: throw exception ???
        }
    }
    protected String getAuthParamTransmissionType()
    {
        return authParamTransmissionType;
    }

    
    // TBD: Who calls this method?
    // Build OAuthParamMap (including the signature)...
    public void buildOAuthParamMap() throws MiniAuthException
    {
        oauthParamMap = OAuthRequestUtil.buildOAuthParams(this);
        setReady(true);
    }
    public OAuthParamMap getOauthParamMap()
    {
        return oauthParamMap;
    }

    
    
    @Override
    public RequestBase setAuthHeader(String authHeaderStr)
            throws MiniAuthException
    {
        super.setAuthHeader(authHeaderStr, AuthScheme.OAUTH);
//        setVerified(false);   // Done in super.
        return this;
    }



    /**
     * Returns true if this request has been "endorsed"
     *    (e.g., if it includes the oauth_signature param in the case of OAuth, etc.).
     *    Endorsed==true does not mean it's verified.
     * @return the "endorsement" state of this request.
     */
    @Override
    public boolean isEndorsed()
    {
        return this.endorsed;
    }
    public void setEndorsed(boolean endorsed)
    {
        this.endorsed = endorsed;
    }

    
}
