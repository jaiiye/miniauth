package org.miniauth.web.oauth.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.core.AuthScheme;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.oauth.util.ParameterTransmissionUtil;
import org.miniauth.util.AuthHeaderUtil;
import org.miniauth.web.util.ServletRequestUtil;


public final class OAuthServletRequestUtil
{
    private static final Logger log = Logger.getLogger(OAuthServletRequestUtil.class.getName());

    private OAuthServletRequestUtil() {}

    
    public static boolean isOAuthParamPresent(HttpServletRequest request) throws MiniAuthException, IOException
    {
        if(getOAuthParamTransmissionType(request) != null) {
            return true;
        } else {
            return false;
        }
    }

    public static String getOAuthParamTransmissionType(HttpServletRequest request) throws MiniAuthException, IOException
    {
        String httpMethod = request.getMethod();
        String host = request.getRemoteHost();
        String requestUri = request.getRequestURI();
        URI baseUri = null;
        try {
            baseUri = new URI(requestUri);
        } catch (URISyntaxException e) {
            // ??? This cannot happen.
            throw new InvalidInputException("Invalid requestUri = " + requestUri, e);
        }

        // ???
        Map<String,String> authHeader = OAuthServletRequestUtil.getAuthParams(request);
        Map<String,String[]> requestParams = request.getParameterMap();
        Map<String,String[]> queryParams = ServletRequestUtil.getQueryParams(request);
        Map<String,String[]> formParams = ServletRequestUtil.getFormParams(request);
        
        

        // TBD:
        // ...
        

        String transmissionType = ParameterTransmissionUtil.getTransmissionType(authHeader, formParams, queryParams);
        
        
        return transmissionType;
    }

    
    public static Map<String,String> getAuthParams(HttpServletRequest request) throws MiniAuthException
    {
        String authHeaderValue = request.getHeader("Authorization");
        Map<String,String> authParams = AuthHeaderUtil.getAuthParams(authHeaderValue, AuthScheme.OAUTH);
        return authParams;
    }
    

}
