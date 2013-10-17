package org.miniauth.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.exception.InternalErrorException;


/**
 * Utility methods related URL query params.
 */
public final class QueryParamUtil
{
    private static final Logger log = Logger.getLogger(QueryParamUtil.class.getName());

    private QueryParamUtil() {}


    public static Map<String,String[]> parseQueryParams(String queryString) throws MiniAuthException
    {
        String[] pairs = queryString.split("&");
        
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
