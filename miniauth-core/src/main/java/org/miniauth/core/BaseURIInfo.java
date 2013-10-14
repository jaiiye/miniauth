package org.miniauth.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;


// Note: Although this is in the auth.core package (and it can be used in a general context),
//       its implementation is specifically tailored for OAuth (OAuth 1.0a).
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
        // TBD: check if uri != null ???
        this(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath());
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


    public static String buildBaseURIString(String uriScheme, String userInfo, String host, int port, String path) throws URISyntaxException 
    {
        BaseURIInfo uriInfo = new BaseURIInfo(uriScheme, userInfo, host, port, path);
        return uriInfo.buildURIString();
    }


    public URI buildURI() throws URISyntaxException 
    {
        return buildURI(null, null);
    }
    public URI buildURI(String query, String fragment) throws URISyntaxException 
    {
        // Remove the default port from the url (per OAuth requirements during signing).
        int uriPort = this.port;
        if(UriScheme.getDefaultPort(this.uriScheme) == this.port) {
            uriPort = -1;
        }
        URI uri = new URI(this.uriScheme, this.userInfo, this.host, uriPort, this.path, query, fragment);
        return uri;
    }

    public String buildURIString() throws URISyntaxException 
    {
        return buildURIString(null, null);
    }
    public String buildURIString(String query, String fragment) throws URISyntaxException 
    {
        URI uri = buildURI(query, fragment);
        return uri.toString();
    }

    @Override
    public String toString()
    {
        String str = "";
        try {
            str = buildURIString();
        } catch (URISyntaxException e) {
            // ????
            log.log(Level.WARNING, "Failed to build URI string.", e);
        }
        return str;
    }
    
    
}
