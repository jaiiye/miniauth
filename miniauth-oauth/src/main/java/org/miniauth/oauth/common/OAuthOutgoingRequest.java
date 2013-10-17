package org.miniauth.oauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.common.OutgoingRequest;


/**
 * Client side request, which is being built by the client, for services that require OAuth.
 * Note that OAuthOutgoingRequest does not represent a full request.
 * It's just a partial wrapper for attributes that are needed for OAuth signing.
 */
public class OAuthOutgoingRequest extends OutgoingRequest
{
    private static final long serialVersionUID = 1L;

    // State variables.
    private boolean endorsed = false;

    public OAuthOutgoingRequest()
    {
        super();
    }
    public OAuthOutgoingRequest(String httpMethod, URI baseURI)
    {
        super(httpMethod, baseURI);
    }
    public OAuthOutgoingRequest(String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> formParams,
            Map<String, String[]> queryParams)
    {
        super(httpMethod, baseURI, authHeader, formParams, queryParams);
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
    public void setEndorsed(boolean endorsed)
    {
        this.endorsed = endorsed;
    }

    
    
}
