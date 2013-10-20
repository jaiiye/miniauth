package org.miniauth.web.oauth.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.core.AuthScheme;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.util.OAuthSignatureUtil;
import org.miniauth.oauth.util.ParameterTransmissionUtil;
import org.miniauth.util.AuthHeaderUtil;
import org.miniauth.web.util.ServletRequestUtil;
import org.miniauth.web.util.URLConnectionUtil;


public final class OAuthURLConnectionUtil
{
    private static final Logger log = Logger.getLogger(OAuthURLConnectionUtil.class.getName());

    private OAuthURLConnectionUtil() {}

    
    // TBD:
    // Not fully implemented..
    public static boolean isOAuthParamPresent(HttpURLConnection conn) throws MiniAuthException, IOException
    {
//        if(getOAuthParamTransmissionType(conn) != null) {
//            return true;
//        } else {
//            return false;
//        }
        if(conn == null) {
            return false;
        }

        // TBD:
        // Need to check the header first...

        // Note: getRequestParams probably does not work... 
        Map<String,String[]> params = URLConnectionUtil.getRequestParams(conn);
        if(params == null) {
            return false;
        }
        return OAuthSignatureUtil.containsAnyOAuthParam(params);
    }

    // TBD:
    // Not fully implemented..
    public static boolean isOAuthSignaturePresent(HttpURLConnection conn) throws MiniAuthException, IOException
    {
        if(conn == null) {
            return false;
        }

        // TBD:
        // Need to check the header first...
        // Note: getRequestParams probably does not work... 
        Map<String,String[]> params = URLConnectionUtil.getRequestParams(conn);
        if(params == null) {
            return false;
        }
        return params.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE);
    }

    // This does not work since Java URLConnection does not return auth related params (e.g., those in Authorization header...)
    public static String getOAuthSignature(HttpURLConnection conn) throws MiniAuthException, IOException
    {
        // ???
        Map<String,String> authHeader = OAuthURLConnectionUtil.getAuthParams(conn);
        // Map<String,String[]> requestParams = URLConnectionUtil.getRequestParams(conn);
        Map<String,String[]> formParams = URLConnectionUtil.getFormParams(conn);
        Map<String,String[]> queryParams = URLConnectionUtil.getQueryParams(conn);
        
        Map<String,String> oauthParams = OAuthSignatureUtil.getOAuthParams(authHeader, formParams, queryParams);
        OAuthParamMap oauthParamMap = new OAuthParamMap(oauthParams);
        
        String signature = oauthParamMap.getSignature();
        if(log.isLoggable(Level.FINER)) log.finer("signature = " + signature);
        return signature;
    }

    public static String getOAuthParamTransmissionType(HttpURLConnection conn) throws MiniAuthException, IOException
    {
        // ???
        Map<String,String> authHeader = OAuthURLConnectionUtil.getAuthParams(conn);
        // Map<String,String[]> requestParams = URLConnectionUtil.getRequestParams(conn);
        Map<String,String[]> formParams = URLConnectionUtil.getFormParams(conn);
        Map<String,String[]> queryParams = URLConnectionUtil.getQueryParams(conn);

        String transmissionType = ParameterTransmissionUtil.getTransmissionType(authHeader, formParams, queryParams);
        if(log.isLoggable(Level.FINER)) log.finer("transmissionType = " + transmissionType);
        return transmissionType;
    }

    
    public static Map<String,String> getAuthParams(HttpURLConnection conn) throws MiniAuthException
    {
        String authHeaderValue = conn.getRequestProperty("Authorization");  // ????
        Map<String,String> authParams = AuthHeaderUtil.parseAuthParamsFromAuthorizationString(authHeaderValue, AuthScheme.OAUTH);
        return authParams;
    }
    

}
