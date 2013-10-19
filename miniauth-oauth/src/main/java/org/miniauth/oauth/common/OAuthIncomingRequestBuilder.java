package org.miniauth.oauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;


/**
 * Builds an OAuthIncomingRequest object.
 * (a) Construct OAuthIncomingRequestBuilder or call init() on existing Builder.
 * (b) Set attributes.
 * (c) Call build() to create OAuthIncomingRequest.
 */
public final class OAuthIncomingRequestBuilder
{
    // Not thread-safe....
    private OAuthIncomingRequest incomingRequest;

    public OAuthIncomingRequestBuilder()
    {
        init();
    }
    
    public OAuthIncomingRequestBuilder init()
    {
        return init(null);
    }
    public OAuthIncomingRequestBuilder init(OAuthIncomingRequest incomingRequest)
    {
        this.incomingRequest = new OAuthIncomingRequest(incomingRequest);
        return this;
    }

    public OAuthIncomingRequestBuilder setHttpMethod(String httpMethod)
            throws MiniAuthException
    {
        incomingRequest.setHttpMethod(httpMethod);
        return this;
    }
    public OAuthIncomingRequestBuilder setBaseURI(URI baseURI) throws MiniAuthException
    {
        incomingRequest.setBaseURI(baseURI);
        return this;
    }
    public OAuthIncomingRequestBuilder setBaseURI(String baseUri) throws MiniAuthException
    {
        incomingRequest.setBaseURI(baseUri);
        return this;
    }
    public OAuthIncomingRequestBuilder setAuthHeader(String authHeaderStr)
            throws MiniAuthException
    {
        incomingRequest.setAuthHeader(authHeaderStr);
        return this;
    }
    public OAuthIncomingRequestBuilder setAuthHeader(Map<String, String> authHeader)
            throws MiniAuthException
    {
        incomingRequest.setAuthHeader(authHeader);
        return this;
    }
    public OAuthIncomingRequestBuilder addAuthHeaderParam(String key, String value)
            throws MiniAuthException
    {
        incomingRequest.addAuthHeaderParam(key, value);
        return this;
    }
    public OAuthIncomingRequestBuilder setFormParams(String formBody)
            throws MiniAuthException
    {
        incomingRequest.setFormParams(formBody);
        return this;
    }
    public OAuthIncomingRequestBuilder setFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        incomingRequest.setFormParams(formParams);
        return this;
    }
    public OAuthIncomingRequestBuilder addFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        incomingRequest.addFormParams(formParams);
        return this;
    }
    public OAuthIncomingRequestBuilder addFormParam(String key, String value)
            throws MiniAuthException
    {
        incomingRequest.addFormParam(key, value);
        return this;
    }
    public OAuthIncomingRequestBuilder setQueryParams(String queryString)
            throws MiniAuthException
    {
        incomingRequest.setQueryParams(queryString);
        return this;
    }
    public OAuthIncomingRequestBuilder setQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        incomingRequest.setQueryParams(queryParams);
        return this;
    }
    public OAuthIncomingRequestBuilder addQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        incomingRequest.addQueryParams(queryParams);
        return this;
    }
    public OAuthIncomingRequestBuilder addQueryParam(String key, String value)
            throws MiniAuthException
    {
        incomingRequest.addQueryParam(key, value);
        return this;
    }

    public OAuthIncomingRequest build() throws MiniAuthException
    {
        incomingRequest.prepare();
        return incomingRequest;
    }

}
