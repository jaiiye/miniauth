package org.miniauth.core;

import java.util.HashMap;
import java.util.Map;


/**
 * Defines the HTTP url scheme constants.
 */
public final class UriScheme
{
    // Note the lowercase.
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    // etc. ...
   
    private static final Map<String,Integer> DEFAULT_PORT;
    static {
        DEFAULT_PORT = new HashMap<String,Integer>();
        DEFAULT_PORT.put(HTTP, 80);
        DEFAULT_PORT.put(HTTPS, 443);
        // etc.
    }

    private UriScheme() {}

    /**
     * Returns the default port for the given scheme.
     * @param scheme URL scheme, http or https.
     * @return Default port of the given URL scheme.
     */
    public static int getDefaultPort(String scheme)
    {
        if(DEFAULT_PORT.containsKey(scheme)) {
            return DEFAULT_PORT.get(scheme);
        } else {
            // ????
            return -1;
        }
    }
    
}
