package org.miniauth.core;

import java.util.HashMap;
import java.util.Map;


// 
public final class UriScheme
{
    // Note the lowercase.
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    // etc. ...
   
    private static final Map<String,Integer> DEFAULT_PORT;
    static {
        DEFAULT_PORT = new HashMap<>();
        DEFAULT_PORT.put(HTTP, 80);
        DEFAULT_PORT.put(HTTPS, 443);
        // etc.
    }

    private UriScheme() {}

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
