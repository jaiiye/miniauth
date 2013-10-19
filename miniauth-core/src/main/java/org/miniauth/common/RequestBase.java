package org.miniauth.common;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.util.AuthHeaderUtil;
import org.miniauth.util.FormParamUtil;
import org.miniauth.util.QueryParamUtil;


/**
 * Base class for "Incoming" and "Outgoing" request beans.
 */
public abstract class RequestBase implements Serializable
{
    private static final Logger log = Logger.getLogger(RequestBase.class.getName());
    private static final long serialVersionUID = 1L;
    
    // To be used for List.toArray() function.
    private static final String[] EMPTY_STRING_ARRAY = new String[]{};

    private String httpMethod = null;
    private volatile URI baseURI = null;  // without the query string part, which is included in queryParams.
    private Map<String,String> authHeader = null;
    private Map<String,String[]> formParams = null;
    private Map<String,String[]> queryParams = null;


    protected RequestBase()
    {
        this(null, null);
    }
    protected RequestBase(String httpMethod, URI baseURI)
    {
        this(httpMethod, baseURI, null, null, null);
    }
    protected RequestBase(String httpMethod, URI baseURI,
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
    // Copy constructor. (Uses deep copy.)
    // We can create a new RequestBase object using the given object as a template.
    // Note that we allow for a partial/incomplete copy instead of throwing an exception. 
    protected RequestBase(RequestBase request)
    {
        if(request != null) {
            this.httpMethod = request.getHttpMethod();
            URI targetURI = request.getBaseURI();
            if(targetURI != null) {
                try {
                    this.baseURI = new URI(request.getBaseURI().toString());
                } catch (URISyntaxException e) {
                    // Ignore. This should not normally happen, anyway.
                    log.log(Level.WARNING, "Failed to copy baseURI.", e);
                }
            }
            Map<String,String> requestAuthHeader = request.getAuthHeader();
            if(requestAuthHeader != null) {
                this.authHeader = new HashMap<>();
                for(String k : requestAuthHeader.keySet()) {
                    String v = requestAuthHeader.get(k);
                    this.authHeader.put(k, v);
                }
            }
            Map<String,String[]> requestFormParams = request.getFormParams();
            if(requestFormParams != null) {
                this.formParams = new HashMap<>();
                for(String k : requestFormParams.keySet()) {
                    String[] values = requestFormParams.get(k);
                    String[] vs = null;
                    if(values != null) {
                        int vLen = values.length;
                        vs = new String[vLen];
                        if(vLen > 0) {
                            System.arraycopy(values, 0, vs, 0, vLen);
                        }
                    }
                    this.formParams.put(k, vs);
                }
            }
            Map<String,String[]> requestQueryParams = request.getQueryParams();
            if(requestQueryParams != null) {
                this.queryParams = new HashMap<>();
                for(String k : requestQueryParams.keySet()) {
                    String[] values = requestQueryParams.get(k);
                    String[] vs = null;
                    if(values != null) {
                        int vLen = values.length;
                        vs = new String[vLen];
                        if(vLen > 0) {
                            System.arraycopy(values, 0, vs, 0, vLen);
                        }
                    }
                    this.queryParams.put(k, vs);
                }
            }
        }
    }

    
    public String getHttpMethod()
    {
        return httpMethod;
    }
    protected RequestBase setHttpMethod(String httpMethod) throws MiniAuthException
    {
        this.httpMethod = httpMethod;
        return this;
    }

    public URI getBaseURI()
    {
        return baseURI;
    }
    public URL getBaseURL()
    {
        if(baseURI == null) {
            return null;
        }
        URL url = null;
        try {
            url = baseURI.toURL();
        } catch (MalformedURLException e) {
            // What to do ???
            log.log(Level.INFO, "Failed to build URL.", e);
        }
        return url;
    }
    public BaseURIInfo getBaseURIInfo()
    {
        return new BaseURIInfo(baseURI);
    }
    public String getBaseURIString()
    {
        return (baseURI == null ? null : baseURI.toString());
    }
    public URI getURI()
    {
        if(baseURI == null) {
            return null;
        }
        if(queryParams == null || queryParams.isEmpty()) {
            return baseURI;
        }
        BaseURIInfo uriInfo = new BaseURIInfo(baseURI);
        URI uri = null;
        try {
            uri = uriInfo.buildURI(queryParams);
        } catch (MiniAuthException e) {
            // What to do ???
            log.log(Level.INFO, "Failed to build URI with queryParams = " + queryParams, e);
        }
        return uri;
    }
    public URL getURL()
    {
        URI uri = getURI();
        if(uri == null) {
            return null;
        }
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            // What to do ???
            log.log(Level.INFO, "Failed to build URL with queryParams = " + queryParams, e);
        }
        return url;
    }
    public String getURIString()
    {
        URI uri = getURI();
        return (uri == null ? null : uri.toString());
    }
    

    protected RequestBase setBaseURI(URI baseURI) throws MiniAuthException
    {
        this.baseURI = baseURI;
        return this;
    }
    protected RequestBase setBaseURI(BaseURIInfo uriInfo) throws MiniAuthException
    {
        this.baseURI = (uriInfo == null ? null : uriInfo.buildURI());
        return this;
    }
    protected RequestBase setBaseURI(String baseUri) throws MiniAuthException
    {
        try {
            this.baseURI = new URI(baseUri);
        } catch (URISyntaxException e) {
            throw new InvalidInputException("Invalid baseUri = " + baseUri, e);
        }
        return this;
    }

    public Map<String, String> getAuthHeader()
    {
        return authHeader;
    }
    public String getAuthHeaderString()
    {
        String authHeaderStr = null;
        try {
            authHeaderStr = AuthHeaderUtil.buildAuthString(authHeader);
        } catch (MiniAuthException e) {
            // What to do ???
            log.log(Level.INFO, "Failed to build auth String.", e);
        }
        return authHeaderStr;
    }
    public String getAuthHeaderAuthorizationString(String authScheme)
    {
        String authorizationString = null;
        String authHeaderStr = getAuthHeaderString();
        if(authHeaderStr != null) {
            authorizationString = authScheme + " " + authHeaderStr;
        }
        return authorizationString;
    }
    protected RequestBase setAuthHeader(String authHeader) throws MiniAuthException
    {
        this.authHeader = AuthHeaderUtil.parseAuthParams(authHeader);
        return this;
    }
    protected RequestBase setAuthHeaderAuthorizationString(String authHeaderAuthString) throws MiniAuthException
    {
        return setAuthHeaderAuthorizationString(authHeaderAuthString, null);
    }
    protected RequestBase setAuthHeaderAuthorizationString(String authHeaderAuthString, String expectedAuthScheme) throws MiniAuthException
    {
        this.authHeader = AuthHeaderUtil.parseAuthParamsFromAuthorizationString(authHeaderAuthString, expectedAuthScheme);
        return this;
    }
    protected RequestBase setAuthHeader(Map<String, String> authHeader) throws MiniAuthException
    {
        this.authHeader = authHeader;
        return this;
    }
    protected RequestBase addAuthHeaderParam(String key, String value) throws MiniAuthException
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
    public String getFormParamString()
    {
        String formParamStr = null;
        try {
            formParamStr = FormParamUtil.buildUrlEncodedFormParamString(formParams);
        } catch (MiniAuthException e) {
            // What to do ???
            log.log(Level.INFO, "Failed to build form param String.", e);
        }
        return formParamStr;
    }
    protected RequestBase setFormParams(String formBody) throws MiniAuthException
    {
        this.formParams = FormParamUtil.parseUrlEncodedFormBody(formBody);
        return this;
    }
    protected RequestBase setFormParams(Map<String, String[]> formParams) throws MiniAuthException
    {
        this.formParams = formParams;
        return this;
    }
    protected RequestBase addFormParams(Map<String, String[]> formParams) throws MiniAuthException
    {
        if(this.formParams == null) {
            this.formParams = new HashMap<>(formParams);
        } else {
            this.formParams.putAll(formParams);
        }
        return this;
    }
    protected RequestBase addFormParam(String key, String value) throws MiniAuthException
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
    public String getQueryParamString()
    {
        String queryParamStr = null;
        try {
            queryParamStr = FormParamUtil.buildUrlEncodedFormParamString(queryParams);
        } catch (MiniAuthException e) {
            // What to do ???
            log.log(Level.INFO, "Failed to build query param String.", e);
        }
        return queryParamStr;
    }
    protected RequestBase setQueryParams(String queryString) throws MiniAuthException
    {
        this.queryParams = QueryParamUtil.parseQueryParams(queryString);
        return this;
    }
    protected RequestBase setQueryParams(Map<String, String[]> queryParams) throws MiniAuthException
    {
        this.queryParams = queryParams;
        return this;
    }
    protected RequestBase addQueryParams(Map<String, String[]> queryParams) throws MiniAuthException
    {
        if(this.queryParams == null) {
            this.queryParams = new HashMap<>(queryParams);
        } else {
            this.queryParams.putAll(queryParams);
        }
        return this;
    }
    protected RequestBase addQueryParam(String key, String value) throws MiniAuthException
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

//    /**
//     * Returns true if this request has been "endorsed".
//     * Its meaning changes depending on whether this object is an instanceOf IncomingRequest or OutgoingRequest.
//     * @return the "endorsement" state of this request.
//     */
//    public abstract boolean isEndorsed();


    // For debugging...
    @Override
    public String toString()
    {
        return "RequestBase [httpMethod=" + httpMethod + ", baseURI=" + baseURI
                + ", authHeader=" + authHeader + ", formParams=" + formParams
                + ", queryParams=" + queryParams + "]";
    }


    
}
