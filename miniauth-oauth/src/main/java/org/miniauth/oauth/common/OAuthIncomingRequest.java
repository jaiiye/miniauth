package org.miniauth.oauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.common.IncomingRequest;
import org.miniauth.common.RequestBase;
import org.miniauth.oauth.service.OAuthRequestUtil;


/**
 * Provider side request, which is sent by a client, for services that use OAuth.
 * Note that OAuthIncomingRequest does not represent a full request.
 * It's just a partial wrapper for attributes that are needed for OAuth signature verification.
 */
public class OAuthIncomingRequest extends IncomingRequest
{
    private static final long serialVersionUID = 1L;

    // State variables.
    private boolean endorsed = false;
    
    // OAuth parameter wrapper
    private OAuthParamMap oauthParamMap = null;
    // private AccessIdentity accessIdentity = null;    // Just use oauthParamMap.accessIdentity


    // Note that Ctor's are not public.
    // Use the builder class to create OAuthIncomingRequest objects.
    protected OAuthIncomingRequest()
    {
        super();
    }
    protected OAuthIncomingRequest(String httpMethod, URI baseURI)
    {
        super(httpMethod, baseURI);
    }
    protected OAuthIncomingRequest(String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> formParams,
            Map<String, String[]> queryParams)
    {
        super(httpMethod, baseURI, authHeader, formParams, queryParams);
    }
    protected OAuthIncomingRequest(RequestBase request)
    {
        super(request);
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
