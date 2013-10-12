package org.miniauth.oauth.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.exception.InternalErrorException;
// import org.miniauth.oauth.core.OAuthConstants;


public final class OAuthAuthorizationHeaderUtil
{
    private static final Logger log = Logger.getLogger(OAuthAuthorizationHeaderUtil.class.getName());

    private OAuthAuthorizationHeaderUtil() {}

    
    
    // It builds a string of (encoded) key-value pairs concatenated using "=" and "," per OAuth Authorization header rule.
    // http://tools.ietf.org/html/rfc5849#section-3.5.1
    // Note that this does not build a full header string (e.g., including "OAuth" etc.).
    public static String buildOAuthAuthorizationValueString(Map<String,String> params) throws InternalErrorException
    {
        if(params == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            Iterator<String> it = params.keySet().iterator();
            while(it.hasNext()) {
                String k = it.next();
                String encKey = URLEncoder.encode(k, "UTF-8");
                String v = params.get(k);
                sb.append(encKey).append("=\"");
                if(v != null && !v.isEmpty()) {
                    String encVal = URLEncoder.encode(v, "UTF-8");
                    sb.append(encVal);
                }
                sb.append("\"");
                if(it.hasNext()) {
                    sb.append(",");
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new InternalErrorException("URL encoding error.", e);
        }
        return sb.toString();
    }
    

}
