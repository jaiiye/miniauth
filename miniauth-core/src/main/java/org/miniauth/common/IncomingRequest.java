package org.miniauth.common;

import java.net.URI;


// Provider side request, which is sent by a client.
// Note that IncomingRequest does not represent a full request.
// It's just a partial wrapper for attributes that are needed for auth handling.
public abstract class IncomingRequest extends RequestBase
{
    private static final long serialVersionUID = 1L;

    public IncomingRequest()
    {
        super();
    }
    public IncomingRequest(String httpMethod, URI baseUri)
    {
        super(httpMethod, baseUri);
    }

    /**
     * Returns true if this request has been "endorsed"
     *    (e.g., if it includes the oauth_signature param in the case of OAuth, etc.).
     *    Endorsed==true does not mean it's verified.
     * @return the "endorsement" state of this request.
     */
    public abstract boolean isEndorsed();


    /**
     * Returns true if this request has been verified.
     *    (e.g., if its oauth_signature param has been verified in the case of OAuth, etc.).
     * @return the "verification" state of this request.
     */
    public abstract boolean isVerified();

    
}
