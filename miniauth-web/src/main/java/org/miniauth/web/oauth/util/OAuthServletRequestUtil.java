package org.miniauth.web.oauth.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.core.AuthScheme;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.signature.OAuthSignatureUtil;
import org.miniauth.oauth.util.ParameterTransmissionUtil;
import org.miniauth.util.AuthHeaderUtil;
import org.miniauth.web.util.ServletRequestUtil;


public final class OAuthServletRequestUtil
{
    private static final Logger log = Logger.getLogger(OAuthServletRequestUtil.class.getName());

    private OAuthServletRequestUtil() {}

    
    public static boolean isOAuthParamPresent(HttpServletRequest request) throws MiniAuthException //, IOException
    {
        // TBD: need to check the header first...
        
//        if(getOAuthParamTransmissionType(request) != null) {
//            return true;
//        } else {
//            return false;
//        }
        if(request == null) {
            return false;
        }
        Map<String,String[]> params = request.getParameterMap();
        if(params == null) {
            return false;
        }
        return OAuthSignatureUtil.containsAnyOAuthParam(params);
    }
    public static boolean isOAuthSignaturePresent(HttpServletRequest request)
    {
        if(request == null) {
            return false;
        }

        // TBD: need to check the header first...
        
        Map<String,String[]> params = request.getParameterMap();
        if(params == null) {
            return false;
        }
        return params.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE);
    }

    public static String getOAuthParamTransmissionType(HttpServletRequest request) throws MiniAuthException, IOException
    {
        // ???
        Map<String,String> authHeader = OAuthServletRequestUtil.getAuthParams(request);
        Map<String,String[]> requestParams = request.getParameterMap();
        Map<String,String[]> queryParams = ServletRequestUtil.getQueryParams(request);
        Map<String,String[]> formParams = ServletRequestUtil.getFormParams(request);

        String transmissionType = ParameterTransmissionUtil.getTransmissionType(authHeader, formParams, queryParams);
        if(log.isLoggable(Level.FINER)) log.finer("transmissionType = " + transmissionType);
        return transmissionType;
    }

    
    public static Map<String,String> getAuthParams(HttpServletRequest request) throws MiniAuthException
    {
        String authHeaderValue = request.getHeader("Authorization");
        Map<String,String> authParams = AuthHeaderUtil.getAuthParams(authHeaderValue, AuthScheme.OAUTH);
        return authParams;
    }
    

}
