package org.miniauth.web.util;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletRequest;


public final class ServletRequestUtil
{
    private static final Logger log = Logger.getLogger(ServletRequestUtil.class.getName());

    private ServletRequestUtil() {}
    
    public static Map<String,String[]> parseServletRequest(ServletRequest request)
    {
        // TBD: ...
        Map<String,String[]> params = request.getParameterMap();
        
        String contentType = request.getContentType();
        
        
        // ....
        
        return params;
    }


}
