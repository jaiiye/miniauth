package org.miniauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;


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
    public RequestBase setHttpMethod(String httpMethod)
            throws MiniAuthException
    {
        super.setHttpMethod(httpMethod);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase setBaseURI(URI baseURI) throws MiniAuthException
    {
        super.setBaseURI(baseURI);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase setBaseURI(String baseUri) throws MiniAuthException
    {
        super.setBaseURI(baseUri);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase setAuthHeader(String authHeaderStr)
            throws MiniAuthException
    {
        super.setAuthHeader(authHeaderStr);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase setAuthHeader(Map<String, String> authHeader)
            throws MiniAuthException
    {
        super.setAuthHeader(authHeader);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase addAuthHeaderParam(String key, String value)
            throws MiniAuthException
    {
        super.addAuthHeaderParam(key, value);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase setFormParams(String formBody) throws MiniAuthException
    {
        super.setFormParams(formBody);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase setFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        super.setFormParams(formParams);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase addFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        super.addFormParams(formParams);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase addFormParam(String key, String value)
            throws MiniAuthException
    {
        super.addFormParam(key, value);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase setQueryParams(String queryString)
            throws MiniAuthException
    {
        super.setQueryParams(queryString);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase setQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        super.setQueryParams(queryParams);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase addQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        super.addQueryParams(queryParams);
        setVerified(false);
        return this;
    }
    @Override
    public RequestBase addQueryParam(String key, String value)
            throws MiniAuthException
    {
        super.addQueryParam(key, value);
        setVerified(false);
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
     */
    @Override
    public abstract boolean isEndorsed();



    
}
