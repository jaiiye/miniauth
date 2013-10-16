package org.miniauth.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.core.HttpHeader;
import org.miniauth.exception.BadRequestException;
import org.miniauth.exception.InternalErrorException;
import org.miniauth.exception.ValidationException;


public final class AuthHeaderUtil
{
    private static final Logger log = Logger.getLogger(AuthHeaderUtil.class.getName());

    private AuthHeaderUtil() {}

    
    public static String getAuthScheme(Map<String,String> header) throws BadRequestException
    {
        if(header != null) {
            String authHeader = header.get(HttpHeader.AUTHORIZATION);
            if(authHeader == null) {
                return null;
            } else {
                return getAuthScheme(authHeader);
            }
        } else {
            return null;
        }
    }
    
    // NOte:
    // This deos not work for OAuth2...
    public static String getAuthScheme(String authHeader) throws BadRequestException
    {
        if(authHeader == null || authHeader.isEmpty()) {
            // ???
            return null; 
        }
        
        // TBD:
        // String[] parts = authHeader.split("\\s+", 2);
        String[] parts = authHeader.split(" ", 2);
        return parts[0].trim();  // ???
    }

    public static Map<String,String> getAuthParams(Map<String,String[]> headers) throws MiniAuthException
    {
        if(headers != null) {
            String[] authHeaders = headers.get(HttpHeader.AUTHORIZATION);
            if(authHeaders == null) {
                return null;
            } else {
                int len = authHeaders.length;
                if(len == 1) {
                    String authHeader = authHeaders[0];
                    return getAuthParams(authHeader);
                } else {
                    // Can this happen???
                    throw new BadRequestException("More than one authorization header found: len = " + len);
                }
            }
        } else {
            return null;
        }
    }
    
    public static Map<String,String> getAuthParams(String authHeader) throws MiniAuthException
    {
        if(authHeader == null || authHeader.isEmpty()) {
            // ???
            return null; 
        }
        
        // TBD:
        // String[] parts = authHeader.split("\\s+", 2);
        String[] parts = authHeader.split(" ", 2);
        if(parts == null || parts.length < 2) {
            // return null;
            throw new BadRequestException("No OAuth params found in the authorization header.");
        }
        
        String paramString = parts[1].trim();
        String[] pairs = paramString.split("&");
        
        Map<String,String> paramMap = new HashMap<>();
        try {
            for(String p : pairs) {
                String[] pair = p.split("=", 2);
                String key = URLDecoder.decode(pair[0], "UTF-8");
                String val = null;
                if(pair.length > 1) {
                    val = URLDecoder.decode(pair[1], "UTF-8");
                } else {
                    val = "";   // ???
                }
                if(paramMap.containsKey(key)) {
                    throw new ValidationException("Duplicate OAuth params found in the authorization header: key = " + key);
                } else {
                    // ????
                    paramMap.put(key, val);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new InternalErrorException("URL decoding error.", e);
        }

        return paramMap;
    }

}
