package org.miniauth.oauth.common;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;


/**
 * OAuth incoming/outgoing request related utility methods.
 */
public final class OAuthRequestUtil
{
    private static final Logger log = Logger.getLogger(OAuthRequestUtil.class.getName());

    private OAuthRequestUtil() {};

    public static Map<String,String> updateOAuthHeaderWithOAuthParamMap(Map<String,String> authHeader, OAuthParamMap oauthParamMap) throws MiniAuthException
    {
        if(oauthParamMap == null || oauthParamMap.isEmpty()) {
            return authHeader;
        }
        Map<String,Object> oauthParams = oauthParamMap.toReadOnlyMap();
        if(authHeader == null) {
            authHeader = new HashMap<>();
        }
        for(String key : oauthParams.keySet()) {
            Object obj = oauthParams.get(key);
            String val = null;
            if(obj == null) {
                val = "";     // ????
            } else {
                val = obj.toString();
            }
            authHeader.put(key, val);
        }
        return authHeader;
    }

    public static Map<String,String[]> updateParamsWithOAuthParamMap(Map<String,String[]> params, OAuthParamMap oauthParamMap) throws MiniAuthException
    {
        if(oauthParamMap == null || oauthParamMap.isEmpty()) {
            return params;
        }
        Map<String,Object> oauthParams = oauthParamMap.toReadOnlyMap();
        if(params == null) {
            params = new HashMap<>();
        }
        for(String key : oauthParams.keySet()) {
            Object obj = oauthParams.get(key);
            String val = null;
            if(obj == null) {
                val = "";     // ????
            } else {
                val = obj.toString();
            }
            // No need to check the old values.
            // At this point, it should all have been validated...
//            String[] oldValues = params.get(key);
//            if(oldValues == null || oldValues.length == 0) {
//            } else {
//            }
            params.put(key, new String[]{val});
        }
        return params;
    }

    
}
