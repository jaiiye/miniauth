package org.miniauth.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.exception.BadRequestException;
import org.miniauth.exception.InternalErrorException;
// import org.miniauth.oauth.core.OAuthConstants;


public final class FormParamUtil
{
    private static final Logger log = Logger.getLogger(FormParamUtil.class.getName());

    private FormParamUtil() {}

    // http://tools.ietf.org/html/rfc5849#section-3.5.2
    public static boolean isUsableForAuth(String contentType, String formBody)
    {
        if(contentType == null || !contentType.equals("application/x-www-form-urlencoded")) {
            return false;
        }
        
        return true;
    }

    public static Map<String,String[]> getFormParams(String contentType, String formBody) throws MiniAuthException
    {
        if(contentType == null || !contentType.equals("application/x-www-form-urlencoded")) {
            return null;
        }
        
        String[] pairs = formBody.split("&");
        
        Map<String,String[]> paramMap = new HashMap<>();
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
                    // if(OAuthConstants.isOAuthParam(key)) {   // ???
                    //     throw new BadRequestException("Duplicate OAuth params found in the authorization header: key = " + key);
                    // } else {
                        // TBD:
                        // This is probably very inefficient if the number of params with the same key increases...
                        String[] oldArr = paramMap.get(key);
                        List<String> list = Arrays.asList(oldArr);
                        list.add(val);
                        String[] newArr = list.toArray(new String[]{});
                        paramMap.put(key, newArr);
                    // }
                } else {
                    paramMap.put(key, new String[]{val});
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new InternalErrorException("URL decoding error.", e);
        }

        return paramMap;
    }
    
    

}
