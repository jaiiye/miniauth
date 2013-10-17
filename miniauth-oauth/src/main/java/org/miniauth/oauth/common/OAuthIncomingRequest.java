package org.miniauth.oauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.common.IncomingRequest;


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
    
    public OAuthIncomingRequest()
    {
        super();
    }
    public OAuthIncomingRequest(String httpMethod, URI baseURI)
    {
        super(httpMethod, baseURI);
    }
    public OAuthIncomingRequest(String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> formParams,
            Map<String, String[]> queryParams)
    {
        super(httpMethod, baseURI, authHeader, formParams, queryParams);
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
