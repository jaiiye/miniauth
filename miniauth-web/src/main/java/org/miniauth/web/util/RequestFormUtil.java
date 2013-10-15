package org.miniauth.web.util;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletRequest;


// TBD:
public final class RequestFormUtil
{
    private static final Logger log = Logger.getLogger(RequestFormUtil.class.getName());

    private RequestFormUtil() {}

    public static boolean isUsableForAuth(ServletRequest request)
    {
        String contentType = request.getContentType();
        if(contentType == null || !contentType.equals("application/x-www-form-urlencoded")) {
            return false;
        }
        // TBD:...
        
        return true;
    }

    public static Map<String,String[]> parseUrlEncodedForm(ServletRequest request)
    {
        if(isUsableForAuth(request) == false) {
            return null;  // ???
        }

        // TBD: ...
        Map<String,String[]> params = null;
        
        // ....
        
        return params;
    }

    

}
