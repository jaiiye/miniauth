package org.miniauth.oauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessIdentity;


/**
 * Builds an OAuthOutgoingRequest object.
 * (a) Construct OAuthOutgoingRequestBuilder or call init() on existing Builder.
 * (b) Set attributes.
 * (c) Call build() to create OAuthOutgoingRequest.
 */
public final class OAuthOutgoingRequestBuilder
{
    // Not thread-safe....
    private OAuthOutgoingRequest outgoingRequest;

    public OAuthOutgoingRequestBuilder()
    {
        init();
    }
    
    public void init()
    {
        init(null);
    }
    public void init(OAuthOutgoingRequest outgoingRequest)
    {
        this.outgoingRequest = new OAuthOutgoingRequest(outgoingRequest);
    }

    public OAuthOutgoingRequestBuilder setHttpMethod(String httpMethod)
            throws MiniAuthException
    {
        outgoingRequest.setHttpMethod(httpMethod);
        return this;
    }
    public OAuthOutgoingRequestBuilder setBaseURI(URI baseURI) throws MiniAuthException
    {
        outgoingRequest.setBaseURI(baseURI);
        return this;
    }
    public OAuthOutgoingRequestBuilder setBaseURI(String baseUri) throws MiniAuthException
    {
        outgoingRequest.setBaseURI(baseUri);
        return this;
    }
    public OAuthOutgoingRequestBuilder setAuthHeader(String authHeaderStr)
            throws MiniAuthException
    {
        outgoingRequest.setAuthHeader(authHeaderStr);
        return this;
    }
    public OAuthOutgoingRequestBuilder setAuthHeader(Map<String, String> authHeader)
            throws MiniAuthException
    {
        outgoingRequest.setAuthHeader(authHeader);
        return this;
    }
    public OAuthOutgoingRequestBuilder addAuthHeaderParam(String key, String value)
            throws MiniAuthException
    {
        outgoingRequest.addAuthHeaderParam(key, value);
        return this;
    }
    public OAuthOutgoingRequestBuilder setFormParams(String formBody)
            throws MiniAuthException
    {
        outgoingRequest.setFormParams(formBody);
        return this;
    }
    public OAuthOutgoingRequestBuilder setFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        outgoingRequest.setFormParams(formParams);
        return this;
    }
    public OAuthOutgoingRequestBuilder addFormParams(Map<String, String[]> formParams)
            throws MiniAuthException
    {
        outgoingRequest.addFormParams(formParams);
        return this;
    }
    public OAuthOutgoingRequestBuilder addFormParam(String key, String value)
            throws MiniAuthException
    {
        outgoingRequest.addFormParam(key, value);
        return this;
    }
    public OAuthOutgoingRequestBuilder setQueryParams(String queryString)
            throws MiniAuthException
    {
        outgoingRequest.setQueryParams(queryString);
        return this;
    }
    public OAuthOutgoingRequestBuilder setQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        outgoingRequest.setQueryParams(queryParams);
        return this;
    }
    public OAuthOutgoingRequestBuilder addQueryParams(Map<String, String[]> queryParams)
            throws MiniAuthException
    {
        outgoingRequest.addQueryParams(queryParams);
        return this;
    }
    public OAuthOutgoingRequestBuilder addQueryParam(String key, String value)
            throws MiniAuthException
    {
        outgoingRequest.addQueryParam(key, value);
        return this;
    }

    public OAuthOutgoingRequest build() throws MiniAuthException
    {
        return build(null);
    }
    public OAuthOutgoingRequest build(AccessIdentity accessIdentity) throws MiniAuthException
    {
        outgoingRequest.buildOAuthParamMap(accessIdentity);
        return outgoingRequest;
    }

}
