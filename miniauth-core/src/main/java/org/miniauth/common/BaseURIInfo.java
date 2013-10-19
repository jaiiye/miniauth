package org.miniauth.common;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.core.UriScheme;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.util.FormParamUtil;


/**
 * Note: Although this is in the auth.core package (and it can be used in a general context),
 *       its implementation is specifically tailored for OAuth (OAuth 1.0a).
 * This is really a wrapper of java.net.URI.
 * OAuth puts many small/messy requirements, and URI is no exception.
 * BaseURIInfo encapsulates this requirement.
 */
public final class BaseURIInfo
{
    private static final Logger log = Logger.getLogger(BaseURIInfo.class.getName());

    private String uriScheme;
    private String userInfo;
    private String host;
    private int port;
    private String path;    // path should be null/empty or it should start with "/".

    public BaseURIInfo()
    {
        this(null, null, null, -1, null);
    }
    public BaseURIInfo(URI uri) 
    {
        this(uri==null?null:uri.getScheme(), 
                uri==null?null:uri.getUserInfo(), 
                uri==null?null:uri.getHost(), 
                uri==null?-1:uri.getPort(), 
                uri==null?null:uri.getPath());
    }
    public BaseURIInfo(String uriScheme, String userInfo, String host, int port, String path)
    {
        super();
        this.uriScheme = (uriScheme == null) ? null : uriScheme.toLowerCase();
        this.userInfo = (userInfo == null) ? null : userInfo.toLowerCase();
        this.host = (host == null) ? null : host.toLowerCase();
        this.port = port;
        this.path = path;
    }

    public String getUriScheme()
    {
        return uriScheme;
    }
    public void setUriScheme(String uriScheme)
    {
        this.uriScheme = (uriScheme == null) ? null : uriScheme.toLowerCase();
    }

    public String getUserInfo()
    {
        return userInfo;
    }
    public void setUserInfo(String userInfo)
    {
        this.userInfo = (userInfo == null) ? null : userInfo.toLowerCase();
    }

    public String getHost()
    {
        return host;
    }
    public void setHost(String host)
    {
        this.host = (host == null) ? null : host.toLowerCase();
    }

    public int getPort()
    {
        return port;
    }
    public void setPort(int port)
    {
        this.port = port;
    }

    public String getPath()
    {
        return path;
    }
    public void setPath(String path)
    {
        // TBD: Validate?
        this.path = path;
    }


    // Builds a url string consistent with the OAuth requirements.
    public static String buildBaseURIString(String uriScheme, String userInfo, String host, int port, String path) throws MiniAuthException 
    {
        BaseURIInfo uriInfo = new BaseURIInfo(uriScheme, userInfo, host, port, path);
        return uriInfo.buildURIString();
    }


    /**
     * Builds the URI according to the OAuth the requirements. 
     * @return The URI equivalent to this BaseUriInfo object.
     * @throws MiniAuthException 
     */
    public URI buildURI() throws MiniAuthException 
    {
        return buildURI(null, null);
    }
    /**
     * Builds the URI according to the OAuth the requirements after adding the query string to the base URI. 
     * @param query Query string.
     * @return The URI equivalent to this BaseUriInfo object.
     * @throws MiniAuthException 
     */
    public URI buildURI(String query) throws MiniAuthException 
    {
        return buildURI(query, null);
    }
    /**
     * Builds the URI according to the OAuth the requirements after adding the queryParams. 
     * @param queryParams Query params.
     * @return The URI equivalent to this BaseUriInfo object.
     * @throws MiniAuthException
     */
    public URI buildURI(Map<String,String[]> queryParams) throws MiniAuthException 
    {
        String query = FormParamUtil.buildUrlEncodedFormParamString(queryParams);
        return buildURI(query, null);
    }
    /**
     * Builds the URI according to the OAuth the requirements. 
     * @param query Query string.
     * @param fragment URL fragment.
     * @return The URI equivalent to this BaseUriInfo object.
     * @throws MiniAuthException 
     */
    public URI buildURI(String query, String fragment) throws MiniAuthException 
    {
        // NOTE:
        // Remove the default port from the url (per OAuth requirements during signing).
        int uriPort = this.port;
        if(UriScheme.getDefaultPort(this.uriScheme) == this.port) {
            uriPort = -1;
        }
        URI uri = null;
        try {
            uri = new URI(this.uriScheme, this.userInfo, this.host, uriPort, this.path, query, fragment);
        } catch (URISyntaxException e) {
            throw new InvalidInputException("Failed to construrct URI.", e);
        }
        return uri;
    }

    /**
     * Builds a URL string.
     * @return The URL string representation of this object.
     * @throws MiniAuthException
     */
    public String buildURIString() throws MiniAuthException 
    {
        return buildURIString(null, null);
    }
    /**
     * Builds a URL string after appending the given query string.
     * @param query QueryString.
     * @return The URL string representation of this object.
     * @throws MiniAuthException
     */
    public String buildURIString(String query) throws MiniAuthException 
    {
        return buildURIString(query, null);
    }
    /**
     * Builds a URL string after appending the given query parameter.
     * @param queryParams Map of query params.
     * @return The URL string representation of this object.
     * @throws MiniAuthException
     */
    public String buildURIString(Map<String,String[]> queryParams) throws MiniAuthException 
    {
        String query = FormParamUtil.buildUrlEncodedFormParamString(queryParams);
        return buildURIString(query, null);
    }
    /**
     * Builds a URL string after adding the given query and fragment string.
     * @param query QueryString.
     * @param fragment Fragment.
     * @return The URL string representation of this object.
     * @throws MiniAuthException
     */
    public String buildURIString(String query, String fragment) throws MiniAuthException 
    {
        URI uri = buildURI(query, fragment);
        return uri.toString();
    }
    
    /**
     * Builds the URL according to the OAuth the requirements. 
     * @return The URL equivalent to this BaseUriInfo object.
     * @throws MiniAuthException 
     */
    public URL buildURL() throws MiniAuthException 
    {
        return buildURL(null, null);
    }
    /**
     * Builds the URL according to the OAuth the requirements after adding the query string to the base URI. 
     * @param query Query string.
     * @return The URL equivalent to this BaseUriInfo object.
     * @throws MiniAuthException 
     */
    public URL buildURL(String query) throws MiniAuthException 
    {
        return buildURL(query, null);
    }
    /**
     * Builds the URL according to the OAuth the requirements after adding the queryParams. 
     * @param queryParams Query params.
     * @return The URL equivalent to this BaseUriInfo object.
     * @throws MiniAuthException
     */
    public URL buildURL(Map<String,String[]> queryParams) throws MiniAuthException 
    {
        String query = FormParamUtil.buildUrlEncodedFormParamString(queryParams);
        return buildURL(query, null);
    }
    /**
     * Builds the URL according to the OAuth the requirements. 
     * @param query Query string.
     * @param fragment URL fragment.
     * @return The URL equivalent to this BaseUriInfo object.
     * @throws MiniAuthException 
     */
    public URL buildURL(String query, String fragment) throws MiniAuthException 
    {
        URL url = null;;
        try {
            URI uri = buildURI(query, fragment);
            url = uri.toURL();
        } catch (MalformedURLException e) {
            throw new InvalidInputException("Failed to construrct URL.", e);
        }
        return url;
    }
    
    
    
    @Override
    public String toString()
    {
        String str = "";
        try {
            str = buildURIString();
        } catch (MiniAuthException e) {
            log.log(Level.WARNING, "Failed to build URI string.", e);
        }
        return str;
    }
    
    
}
