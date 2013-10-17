package org.miniauth.common;

import java.net.URI;
import java.util.Map;


/**
 * Provider side request, which is sent by a client.
 * Note that IncomingRequest does not represent a full request.
 * It's just a partial wrapper for attributes that are needed for auth handling.
 */
public abstract class IncomingRequest extends RequestBase
{
    private static final long serialVersionUID = 1L;
    
    // ready for verification??
    private boolean ready = false;

    public IncomingRequest()
    {
        super();
    }
    public IncomingRequest(String httpMethod, URI baseURI)
    {
        super(httpMethod, baseURI);
    }
    public IncomingRequest(String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> formParams,
            Map<String, String[]> queryParams)
    {
        super(httpMethod, baseURI, authHeader, formParams, queryParams);
    }

    /**
     * Returns true if the request is in a state where it can be verified.
     * @return true if it is ready for verification.
     */
    @Override
    public boolean isReady()
    {
        return ready;
    }
    
    /**
     * Sets the "ready" state. If it is true, then it can be endorsed. 
     * @param ready The ready state of this request.
     */
    public void setReady(boolean ready)
    {
        this.ready = ready;
    }


    /**
     * Returns true if this request has been "endorsed"
     *    (e.g., if it includes the oauth_signature param in the case of OAuth, etc.).
     *    Endorsed==true does not mean it's verified.
     * @return the "endorsement" state of this request.
     */
    @Override
    public abstract boolean isEndorsed();


    /**
     * Returns true if this request has been verified.
     *    (e.g., if its oauth_signature param has been verified in the case of OAuth, etc.).
     * @return the "verification" state of this request.
     */
    public abstract boolean isVerified();

    
}
