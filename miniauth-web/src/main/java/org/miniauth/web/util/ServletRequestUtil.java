package org.miniauth.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.util.FormParamUtil;
import org.miniauth.util.QueryParamUtil;


public final class ServletRequestUtil
{
    private static final Logger log = Logger.getLogger(ServletRequestUtil.class.getName());

    private ServletRequestUtil() {}
    
    // Returns the query params without the POST form params.
    public static Map<String,String[]> getQueryParams(HttpServletRequest request) throws MiniAuthException
    {
        String queryString = request.getQueryString();
        if(queryString == null) {
            return null;
        }
        Map<String,String[]> queryParams = QueryParamUtil.parseQueryParams(queryString);
        if(log.isLoggable(Level.FINER)) log.finer("queryParams = " + queryParams);
        return queryParams;
    }
    
    public static Map<String,String[]> getFormParams(HttpServletRequest request) throws MiniAuthException, IOException
    {
        String contentType = request.getContentType();
        if(contentType == null || !contentType.equals("application/x-www-form-urlencoded")) {
            return null;
        }
        
        String formBody = readFormBody(request);
        Map<String,String[]> formParams = FormParamUtil.parseUrlEncodedFormParams(contentType, formBody);
        if(log.isLoggable(Level.FINER)) log.finer("formParams = " + formParams);
        return formParams;
    }
    
    
    public static String readFormBody(HttpServletRequest request) throws IOException 
    {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = request.getInputStream();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            if (inputStream != null) {
                char[] charBuffer = new char[512];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    sb.append(charBuffer, 0, bytesRead);
                }
            }
        }
        String formBody = sb.toString();
        if(log.isLoggable(Level.FINER)) log.finer("formBody = " + formBody);
        return formBody;
    }
    
    
    
    
    // TBD:
    public static Map<String,String[]> parseServletRequest(ServletRequest request)
    {
        // TBD: ...
        Map<String,String[]> params = request.getParameterMap();
        
        String contentType = request.getContentType();
        
        
        // ....
        
        return params;
    }


}
