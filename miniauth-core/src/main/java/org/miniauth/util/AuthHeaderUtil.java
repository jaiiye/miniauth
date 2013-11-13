package org.miniauth.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.core.AuthScheme;
import org.miniauth.core.HttpHeader;
import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.exception.BadRequestException;
import org.miniauth.exception.InternalErrorException;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.exception.ValidationException;


/**
 * Various auth-related utility functions.
 */
public final class AuthHeaderUtil
{
    private static final Logger log = Logger.getLogger(AuthHeaderUtil.class.getName());

    private AuthHeaderUtil() {}

    
    public static String getAuthScheme(Map<String,String> header) throws MiniAuthException
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
    
    public static String getAuthScheme(String authHeader) throws MiniAuthException
    {
        if(authHeader == null || authHeader.isEmpty()) {
            // ???
            return null; 
        }
        
        // TBD:
        // String[] parts = authHeader.split("\\s+", 2);
        String[] parts = authHeader.split(" ", 2);
        String authScheme = AuthScheme.getAuthSchemeFromAuthorizationHeaderAuthScheme(parts[0].trim());  // ???
        return authScheme;
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
                    return parseAuthParamsFromAuthorizationString(authHeader);
                } else {
                    // Can this happen???
                    throw new BadRequestException("More than one authorization header found: len = " + len);
                }
            }
        } else {
            return null;
        }
    }
    
    public static Map<String,String> parseAuthParamsFromAuthorizationString(String authHeaderAuthString) throws MiniAuthException
    {
        return parseAuthParamsFromAuthorizationString(authHeaderAuthString, null);
    }
    public static Map<String,String> parseAuthParamsFromAuthorizationString(String authHeaderAuthString, String expectedAuthScheme) throws MiniAuthException
    {
        if(authHeaderAuthString == null || authHeaderAuthString.isEmpty()) {
            // ???
            return null; 
        }
        
        // TBD:
        // String[] parts = authHeader.split("\\s+", 2);
        String[] parts = authHeaderAuthString.split(" ", 2);
        if(parts == null || parts.length < 2) {
            // return null;
            throw new BadRequestException("No OAuth params found in the authorization header.");
        }
        
        String headerAuthScheme = parts[0].trim();
        String authScheme = AuthScheme.getAuthSchemeFromAuthorizationHeaderAuthScheme(headerAuthScheme);
        if(expectedAuthScheme != null) {
            if(! expectedAuthScheme.equals(authScheme)) {
                throw new InvalidInputException("expected: " + expectedAuthScheme + "; Auth scheme found: " + headerAuthScheme);
            }
        }

        String paramString = parts[1].trim();
        Map<String,String> paramMap = parseAuthParams(paramString);
        return paramMap;
    }
    
    // authHeader is a string after the auth scheme prefix (e.g., after "OAuth ", etc.)
    public static Map<String,String> parseAuthParams(String authHeader) throws MiniAuthException
    {
        if(authHeader == null || authHeader.isEmpty()) {
            // ???
            return null; 
        }
        
//      String SEPARATER = "&";
//      String authScheme = AuthScheme.getAuthSchemeFromAuthorizationHeaderAuthScheme(headerAuthScheme);
//      if(AuthScheme.OAUTH.equals(authScheme)) {
//          SEPARATER = ",";
//      }
        // ???
        String SEPARATER = ",";
        // ???
    
        String paramString = authHeader;
        String[] pairs = paramString.split(SEPARATER);
          
        Map<String,String> paramMap = new HashMap<String,String>();
        try {
            for(String p : pairs) {
                String[] pair = p.split("=", 2);
                String key = URLDecoder.decode(pair[0], "UTF-8");
                String val = null;
                if(pair.length > 1) {
                    String quoted = pair[1];
                    if(quoted.length() > 1) {
                        String bare = quoted.substring(1, quoted.length()-1);
                        val = URLDecoder.decode(bare, "UTF-8");
                    } else {
                        // ???
                        val = "";
                    }
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

    // This is really applicable to OAuth only...
    public static String buildAuthString(Map<String,String> authHeader) throws MiniAuthException
    {
        if(authHeader == null) {
            return null;
        }
        final String SEPARATOR = ",";
        StringBuilder sb = new StringBuilder();
        try {
            Iterator<String> it = authHeader.keySet().iterator();
            while(it.hasNext()) {
                String k = it.next();
                String encKey = URLEncoder.encode(k, "UTF-8");
                String v = authHeader.get(k);
                sb.append(encKey).append("=\"");
                if(v != null && !v.isEmpty()) {
                    String encVal = URLEncoder.encode(v, "UTF-8");
                    sb.append(encVal);
                }
                sb.append("\"");
                if(it.hasNext()) {
                    sb.append(SEPARATOR);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new InternalErrorException("URL encoding error.", e);
        }
        return sb.toString();
    }


}
