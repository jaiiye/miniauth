package org.miniauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.BadRequestException;


/**
 * Provider side request, which is sent by a client.
 * Note that IncomingRequest does not represent a full request.
 * It's just a partial wrapper for attributes that are needed for auth handling.
 * 
 * [ The "life cycle" of IncomingRequest ]
 * IncomingRequest goes through a similar life cycle as OutgoingRequest.
 * However, its requirement is not as strict as that of OutgoingRequest.
 * It starts with an "init" or constructed state.
 * Once necessary attributes have been added, the object can be declared as being "ready".
 * If the request is in a ready state, then it can be made "verified".
 * Even after setVerified() is called, however, its internal attributes can be modified,
 *     in which case, the verified flag is set back to false. 
 */
public abstract class IncomingRequest extends RequestBase
{
    private static final long serialVersionUID = 1L;
    
    // ready for verification??
    private boolean ready = false;
    // Already verified??
    private boolean verified = false;

    protected IncomingRequest()
    {
        super();
    }
    protected IncomingRequest(String httpMethod, URI baseURI)
    {
        super(httpMethod, baseURI);
    }
    protected IncomingRequest(String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> formParams,
            Map<String, String[]> queryParams)
    {
        super(httpMethod, baseURI, authHeader, formParams, queryParams);
    }
    protected IncomingRequest(RequestBase request)
    {
        super(request);
    }

    
    @Override
    protected RequestBase setHttpMethod(String httpMethod)
            throws MiniAuthException
    {
        super.setHttpMethod(httpMethod);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setBaseURI(URI baseURI) throws MiniAuthException
    {
        super.setBaseURI(baseURI);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setBaseURI(BaseURIInfo uriInfo)
            throws MiniAuthException
    {
        super.setBaseURI(uriInfo);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setBaseURI(String baseUri) throws MiniAuthException
    {
        super.setBaseURI(baseUri);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setAuthHeader(String authHeader)
            throws MiniAuthException
    {
        super.setAuthHeader(authHeader);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setAuthHeaderAuthorizationString(
            String authHeaderAuthString, String expectedAuthScheme)
            throws MiniAuthException
    {
        super.setAuthHeaderAuthorizationString(authHeaderAuthString,
                expectedAuthScheme);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setAuthHeaderAuthorizationString(String authHeaderAuthString)
            throws MiniAuthException
    {
        super.setAuthHeaderAuthorizationString(authHeaderAuthString);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setAuthHeader(Map<String, String> authHeader)
            throws MiniAuthException
    {
        super.setAuthHeader(authHeader);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase addAuthHeaderParam(String key, String value)
            throws MiniAuthException
    {
        super.addAuthHeaderParam(key, value);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setFormParams(String formBody) throws MiniAuthException
    {
        super.setFormParams(formBody);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        super.setFormParams(formParams);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase addFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        super.addFormParams(formParams);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase addFormParam(String key, String value)
            throws MiniAuthException
    {
        super.addFormParam(key, value);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setQueryParams(String queryString)
            throws MiniAuthException
    {
        super.setQueryParams(queryString);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase setQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        super.setQueryParams(queryParams);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase addQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        super.addQueryParams(queryParams);
        setVerified(false);
        return this;
    }
    @Override
    protected RequestBase addQueryParam(String key, String value)
            throws MiniAuthException
    {
        super.addQueryParam(key, value);
        setVerified(false);
        return this;
    }



    /**
     * Put this request object in a "ready" state.
     * The request object can be verified only if it's in a ready state.
     * @return this object.
     * @throws MiniAuthException if preparation fails, or the object cannot be put into the "ready" state.
     */
    public abstract IncomingRequest prepare() throws MiniAuthException;

    /**
     * Put this request object in a "verified" state. (It does not actually "verify" anything.)
     * This is the second of the two "state changing" operations.
     * This can be called only if the current state == ready.
     * @return this object.
     * @throws MiniAuthException if the IncomingRequest cannot be put into the "verified" state.
     */
    public IncomingRequest verify() throws MiniAuthException
    {
        if(! isReady()) {
            throw new BadRequestException("Cannot change the state to verfied because the IncomingRequest is not ready.");
        }
        setVerified(true);
        return this;
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
    protected void setReady(boolean ready)
    {
        this.ready = ready;
    }

    /**
     * Returns true if this request has been verified.
     *    (e.g., if its oauth_signature param has been verified in the case of OAuth, etc.).
     * @return the "verification" state of this request.
     */
    public boolean isVerified()
    {
        return this.verified;
    }
    public void setVerified(boolean verified)
    {
        this.verified = verified;
    }


    /**
     * Returns true if this request has been "endorsed"
     *    (e.g., if it includes the oauth_signature param in the case of OAuth, etc.).
     *    Endorsed==true does not mean it's verified.
     * @return the "endorsement" state of this request.
     * @throws MiniAuthException TODO
     */
    public abstract boolean isEndorsed() throws MiniAuthException;


    
}
