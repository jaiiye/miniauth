package org.miniauth.common;

import java.net.URI;


// Client side request, which is being built by the client.
// Note that OutgoingRequest does not represent a full request.
// It's just a partial wrapper for attributes that are needed for auth handling.
public abstract class OutgoingRequest extends RequestBase
{
    private static final long serialVersionUID = 1L;

    public OutgoingRequest()
    {
        super();
    }
    public OutgoingRequest(String httpMethod, URI baseUri)
    {
        super(httpMethod, baseUri);
    }

    /**
     * Returns true if this request has been "endorsed"
     *    (e.g., if it includes the oauth_signature param in the case of OAuth, etc.).
     * @return the "endorsement" state of this request.
     */
    public abstract boolean isEndorsed();

    
}
