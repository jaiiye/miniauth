package org.miniauth.common;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.miniauth.MiniAuthException;


/**
 * Base class for "Incoming" and "Outgoing" request beans.
 */
public abstract class RequestBase implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // To be used for List.toArray() function.
    private static final String[] EMPTY_STRING_ARRAY = new String[]{};

    private String httpMethod = null;
    private URI baseURI = null;   // without the query string part, which is included in queryParams.
    private Map<String,String> authHeader = null;
    private Map<String,String[]> formParams = null;
    private Map<String,String[]> queryParams = null;


    public RequestBase()
    {
        this(null, null);
    }
    public RequestBase(String httpMethod, URI baseURI)
    {
        this(httpMethod, baseURI, null, null, null);
    }
    public RequestBase(String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> formParams,
            Map<String, String[]> queryParams)
    {
        super();
        this.httpMethod = httpMethod;
        this.baseURI = baseURI;
        this.authHeader = authHeader;
        this.formParams = formParams;
        this.queryParams = queryParams;
    }

    
    public String getHttpMethod()
    {
        return httpMethod;
    }
    public RequestBase setHttpMethod(String httpMethod) throws MiniAuthException
    {
        this.httpMethod = httpMethod;
        return this;
    }

    public URI getBaseURI()
    {
        return baseURI;
    }
    public RequestBase setBaseURI(URI baseURI) throws MiniAuthException
    {
        this.baseURI = baseURI;
        return this;
    }

    public Map<String, String> getAuthHeader()
    {
        return authHeader;
    }
    public RequestBase setAuthHeader(Map<String, String> authHeader) throws MiniAuthException
    {
        this.authHeader = authHeader;
        return this;
    }
    public RequestBase addAuthHeaderParam(String key, String value) throws MiniAuthException
    {
        if(this.authHeader == null) {
            this.authHeader = new HashMap<>();
        }
        this.authHeader.put(key, value);
        return this;
    }

    public Map<String, String[]> getFormParams()
    {
        return formParams;
    }
    public RequestBase setFormParams(Map<String, String[]> formParams) throws MiniAuthException
    {
        this.formParams = formParams;
        return this;
    }
    public RequestBase addFormParams(Map<String, String[]> formParams) throws MiniAuthException
    {
        if(this.formParams == null) {
            this.formParams = new HashMap<>(formParams);
        } else {
            this.formParams.putAll(formParams);
        }
        return this;
    }
    public RequestBase addFormParam(String key, String value) throws MiniAuthException
    {
        if(this.formParams == null) {
            this.formParams = new HashMap<>();
        }
        String[] values = this.formParams.get(key);
        if(values == null || values.length == 0) {
            values = new String[]{value};
        } else {
            List<String> list = Arrays.asList(values);
            list.add(value);
            values = list.toArray(EMPTY_STRING_ARRAY);
        }
        this.formParams.put(key, values);
        return this;
    }

    public Map<String, String[]> getQueryParams()
    {
        return queryParams;
    }
    public RequestBase setQueryParams(Map<String, String[]> queryParams) throws MiniAuthException
    {
        this.queryParams = queryParams;
        return this;
    }
    public RequestBase addQueryParams(Map<String, String[]> queryParams) throws MiniAuthException
    {
        if(this.queryParams == null) {
            this.queryParams = new HashMap<>(queryParams);
        } else {
            this.queryParams.putAll(queryParams);
        }
        return this;
    }
    public RequestBase addQueryParam(String key, String value) throws MiniAuthException
    {
        if(this.queryParams == null) {
            this.queryParams = new HashMap<>();
        }
        String[] values = this.queryParams.get(key);
        if(values == null || values.length == 0) {
            values = new String[]{value};
        } else {
            List<String> list = Arrays.asList(values);
            list.add(value);
            values = list.toArray(EMPTY_STRING_ARRAY);
        }
        this.queryParams.put(key, values);
        return this;
    }


    /**
     * Returns true if the request is in a state where the main operations can be performed.
     * Its meaning changes depending on whether this object is an instanceOf IncomingRequest or OutgoingRequest.
     * @return true if it is ready.
     */
    public abstract boolean isReady();

    /**
     * Returns true if this request has been "endorsed".
     * Its meaning changes depending on whether this object is an instanceOf IncomingRequest or OutgoingRequest.
     * @return the "endorsement" state of this request.
     */
    public abstract boolean isEndorsed();


    // For debugging...
    @Override
    public String toString()
    {
        return "RequestBase [httpMethod=" + httpMethod + ", baseURI=" + baseURI
                + ", authHeader=" + authHeader + ", formParams=" + formParams
                + ", queryParams=" + queryParams + "]";
    }


    
}
