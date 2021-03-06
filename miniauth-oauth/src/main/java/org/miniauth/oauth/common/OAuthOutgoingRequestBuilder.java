package org.miniauth.oauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.common.BaseURIInfo;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.oauth.credential.OAuthAccessIdentity;


/**
 * Builds an OAuthOutgoingRequest object.
 * (a) Construct OAuthOutgoingRequestBuilder or call init() on existing Builder.
 * (b) Set attributes. Note that later setter call overwrites any previously set values.
 * (c) Then, call build() to create OAuthOutgoingRequest.
 */
public final class OAuthOutgoingRequestBuilder
{
    // Not thread-safe....
    private OAuthOutgoingRequest outgoingRequest;

    public OAuthOutgoingRequestBuilder()
    {
        init();
    }
    
    public OAuthOutgoingRequestBuilder init()
    {
        return init(null);
    }
    public OAuthOutgoingRequestBuilder init(OAuthOutgoingRequest outgoingRequest)
    {
        this.outgoingRequest = new OAuthOutgoingRequest(outgoingRequest);
        return this;
    }


    public OAuthOutgoingRequestBuilder setAccessIdentity(AccessIdentity accessIdentity) throws MiniAuthException
    {
        // TBD:
        outgoingRequest.buildOAuthParamMap(accessIdentity);
        return this;
    }
    public OAuthOutgoingRequestBuilder setConsumerKey(String consumerKey) throws MiniAuthException
    {
        // TBD:
        outgoingRequest.buildOAuthParamMap(new OAuthAccessIdentity(consumerKey, null));
        return this;
    }
    public OAuthOutgoingRequestBuilder setAccessToken(String accessToken) throws MiniAuthException
    {
        // TBD:
        outgoingRequest.buildOAuthParamMap(new OAuthAccessIdentity(null, accessToken));
        return this;
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
    public OAuthOutgoingRequestBuilder setBaseURI(BaseURIInfo uriInfo)
            throws MiniAuthException
    {
        outgoingRequest.setBaseURI(uriInfo);
        return this;
    }
    public OAuthOutgoingRequestBuilder setAuthHeader(String authHeader)
            throws MiniAuthException
    {
        // TBD: validate OAuth authHeader keys ???
        outgoingRequest.setAuthHeader(authHeader);
        return this;
    }
    public OAuthOutgoingRequestBuilder setAuthHeaderAuthorizationString(String authHeaderStr)
            throws MiniAuthException
    {
        outgoingRequest.setAuthHeaderAuthorizationString(authHeaderStr);
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
        outgoingRequest.prepare(accessIdentity);
        return outgoingRequest;
    }

}
