package org.miniauth.common;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;


public abstract class RequestBase implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String httpMethod;
    private URI baseUri;   // without the query string part, which is included in queryParams.
    private Map<String,String> authHeader;
    private Map<String,String[]> formParams;
    private Map<String,String[]> queryParams;

    public RequestBase()
    {
        this(null, null);
    }
    public RequestBase(String httpMethod, URI baseUri)
    {
        super();
        this.httpMethod = httpMethod;
        this.baseUri = baseUri;
    }

    
    public String getHttpMethod()
    {
        return httpMethod;
    }
    public void setHttpMethod(String httpMethod)
    {
        this.httpMethod = httpMethod;
    }

    public URI getBaseUri()
    {
        return baseUri;
    }
    public void setBaseUri(URI baseUri)
    {
        this.baseUri = baseUri;
    }

    public Map<String, String> getAuthHeader()
    {
        return authHeader;
    }
    public void setAuthHeader(Map<String, String> authHeader)
    {
        this.authHeader = authHeader;
    }

    public Map<String, String[]> getFormParams()
    {
        return formParams;
    }
    public void setFormParams(Map<String, String[]> formParams)
    {
        this.formParams = formParams;
    }

    public Map<String, String[]> getQueryParams()
    {
        return queryParams;
    }
    public void setQueryParams(Map<String, String[]> queryParams)
    {
        this.queryParams = queryParams;
    }


    // For debugging...
    @Override
    public String toString()
    {
        return "RequestBase [httpMethod=" + httpMethod + ", baseUri=" + baseUri
                + ", authHeader=" + authHeader + ", formParams=" + formParams
                + ", queryParams=" + queryParams + "]";
    }


    
}
