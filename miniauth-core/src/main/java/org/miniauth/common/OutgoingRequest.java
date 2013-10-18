package org.miniauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.exception.InvalidStateException;


/**
 * Client side request, which is being built by the client.
 * Note that OutgoingRequest does not represent a full request.
 * It's just a partial wrapper for attributes that are needed for auth handling.
 */
public abstract class OutgoingRequest extends RequestBase
{
    private static final long serialVersionUID = 1L;
    
    // ready for endorsing??
    private boolean ready = false;

    protected OutgoingRequest()
    {
        super();
    }
    protected OutgoingRequest(String httpMethod, URI baseURI)
    {
        super(httpMethod, baseURI);
    }
    protected OutgoingRequest(String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> formParams,
            Map<String, String[]> queryParams)
    {
        super(httpMethod, baseURI, authHeader, formParams, queryParams);
    }
    protected OutgoingRequest(RequestBase request)
    {
        super(request);
    }

    
    /**
     * Returns true if the request is in a state where it can be endorsed.
     * @return true if it is ready for endorsement.
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


    @Override
    public RequestBase setHttpMethod(String httpMethod)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setHttpMethod(httpMethod);
    }
    @Override
    public RequestBase setBaseURI(URI baseURI) throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setBaseURI(baseURI);
    }
    @Override
    public RequestBase setBaseURI(String baseUri) throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setBaseURI(baseUri);
    }
    @Override
    public RequestBase setAuthHeader(String authHeaderStr)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setAuthHeader(authHeaderStr);
    }
    @Override
    public RequestBase setAuthHeader(Map<String, String> authHeader)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setAuthHeader(authHeader);
    }
    @Override
    public RequestBase addAuthHeaderParam(String key, String value)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.addAuthHeaderParam(key, value);
    }
    @Override
    public RequestBase setFormParams(String formBody) throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setFormParams(formBody);
    }
    @Override
    public RequestBase setFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setFormParams(formParams);
    }
    @Override
    public RequestBase addFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.addFormParams(formParams);
    }
    @Override
    public RequestBase addFormParam(String key, String value)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.addFormParam(key, value);
    }
    @Override
    public RequestBase setQueryParams(String queryString)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setQueryParams(queryString);
    }
    @Override
    public RequestBase setQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setQueryParams(queryParams);
    }
    @Override
    public RequestBase addQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.addQueryParams(queryParams);
    }
    @Override
    public RequestBase addQueryParam(String key, String value)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.addQueryParam(key, value);
    }

    
    
    
    /**
     * Returns true if this request has been "endorsed"
     *    (e.g., if it includes the oauth_signature param in the case of OAuth, etc.).
     * Note that the request can be endorsed only if it is "ready".
     * Also, once it's endorsed, it cannot be endorsed again or it cannot be changed.
     * @return the "endorsement" state of this request.
     */
    public abstract boolean isEndorsed();

    
}
