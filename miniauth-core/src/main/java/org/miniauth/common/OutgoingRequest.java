package org.miniauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.InvalidStateException;


/**
 * Client side request, which is being built by the client.
 * Note that OutgoingRequest does not represent a full request.
 * It's just a partial wrapper for attributes that are needed for auth handling.
 * 
 * [ The "life cycle" of OutgoingRequest ]
 * The request starts with an "init" state.
 * Once all necessary attributes are added, the request can be put into a "ready" state.
 * This can be accomplished by calling prepare() (depending on the implementation of a subclass).
 * Generally, prepare() can be called more than once, if necessary 
 *     (that is, because the object attributes have changed since the last prepare() call). 
 * Once the OutgoingRequest is in a ready state, one can call endorse(),
 *     which adds a signature, etc. to the request object (in the case of OAuth).
 * If it is endorsed, then the OutgoingRequest object can no longer be changed.
 * One can only get/retrieve relevant parameters from the object.
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
    protected RequestBase setHttpMethod(String httpMethod)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setHttpMethod(httpMethod);
    }
    @Override
    protected RequestBase setBaseURI(URI baseURI) throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setBaseURI(baseURI);
    }
    @Override
    protected RequestBase setBaseURI(String baseUri) throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setBaseURI(baseUri);
    }
    @Override
    protected RequestBase setAuthHeader(String authHeaderStr)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setAuthHeader(authHeaderStr);
    }
    @Override
    protected RequestBase setAuthHeader(Map<String, String> authHeader)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setAuthHeader(authHeader);
    }
    @Override
    protected RequestBase addAuthHeaderParam(String key, String value)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.addAuthHeaderParam(key, value);
    }
    @Override
    protected RequestBase setFormParams(String formBody) throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setFormParams(formBody);
    }
    @Override
    protected RequestBase setFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setFormParams(formParams);
    }
    @Override
    protected RequestBase addFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.addFormParams(formParams);
    }
    @Override
    protected RequestBase addFormParam(String key, String value)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.addFormParam(key, value);
    }
    @Override
    protected RequestBase setQueryParams(String queryString)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setQueryParams(queryString);
    }
    @Override
    protected RequestBase setQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.setQueryParams(queryParams);
    }
    @Override
    protected RequestBase addQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.addQueryParams(queryParams);
    }
    @Override
    protected RequestBase addQueryParam(String key, String value)
            throws MiniAuthException
    {
        if(isEndorsed()) {
            throw new InvalidStateException("The request is already endorsed. Param cannot be changed.");
        }
        return super.addQueryParam(key, value);
    }

    

    /**
     * Put this request object in a "ready" state.
     * This is the first of the two "state changing" operations.
     * @param accessIdentity Auth identity to use in "preparing" this request.
     * @return this object.
     * @throws MiniAuthException if preparation fails, or the object cannot be into the "ready" state.
     */
    public abstract OutgoingRequest prepare(AccessIdentity accessIdentity) throws MiniAuthException;

    /**
     * Put this request object in a "endorsed" state.
     * This is the second of the two "state changing" operations.
     * This can be called only if the current state == ready.
     * @param signature Signature to use for endorsing. Could be null, if the auth scheme does not require signature.
     * @return this object.
     * @throws MiniAuthException if endorsement fails.
     */
    public abstract OutgoingRequest endorse(String signature) throws MiniAuthException;

    
    /**
     * Returns true if this request has been "endorsed"
     *    (e.g., if it includes the oauth_signature param in the case of OAuth, etc.).
     * Note that the request can be endorsed only if it is "ready".
     * Also, once it's endorsed, it cannot be endorsed again or it cannot be changed.
     * @return the "endorsement" state of this request.
     */
    public abstract boolean isEndorsed();
    
    
    
}
