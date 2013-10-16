package org.miniauth.web.oauth.util;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;


public final class OAuthServletRequestUtil
{
    private static final Logger log = Logger.getLogger(OAuthServletRequestUtil.class.getName());

    private OAuthServletRequestUtil() {}

    
    public static boolean isOAuthParamPresent(HttpServletRequest request)
    {
        if(getOAuthParamTransmissionType(request) != null) {
            return true;
        } else {
            return false;
        }
    }

    public static String getOAuthParamTransmissionType(HttpServletRequest request)
    {
        // TBD:
        // ...
        
        return null;
    }


}
