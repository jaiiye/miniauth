package org.miniauth.oauth.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.exception.InternalErrorException;


/**
 * OAuth auth-related utility methods.
 */
public final class OAuthAuthorizationValueUtil
{
    private static final Logger log = Logger.getLogger(OAuthAuthorizationValueUtil.class.getName());

    private OAuthAuthorizationValueUtil() {}

    
    /**
     * It builds a string of (encoded) key-value pairs concatenated using "=" and "," per OAuth Authorization header rule.
     * http://tools.ietf.org/html/rfc5849#section-3.5.1
     * Note that this does not build a full header string (e.g., including "OAuth" etc.).
     * 
     * @param params Map of auth header params.
     * @return OAuth header/query param string.
     * @throws InternalErrorException
     */
    public static String buildOAuthAuthorizationValueString(Map<String,String> params) throws InternalErrorException
    {
        return buildOAuthAuthorizationValueString(params, ParameterTransmissionUtil.getDefaultTransmissionType());
    }
    /**
     * It builds a string of (encoded) key-value pairs concatenated using "=" and "," per OAuth Authorization header rule.
     * http://tools.ietf.org/html/rfc5849#section-3.5.1
     * Note that this does not build a full header string (e.g., including "OAuth" etc.).
     * 
     * @param params Map of auth header params.
     * @param transmissionType header, query, or form.
     * @return OAuth header/query param string.
     * @throws InternalErrorException
     */
    public static String buildOAuthAuthorizationValueString(Map<String,String> params, String transmissionType) throws InternalErrorException
    {
        if(params == null) {
            return null;
        }
        String separator = "&";
        if(ParameterTransmissionType.HEADER.equals(transmissionType)) {
            separator = ",";
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
                    sb.append(separator);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new InternalErrorException("URL encoding error.", e);
        }
        return sb.toString();
    }
    

}
