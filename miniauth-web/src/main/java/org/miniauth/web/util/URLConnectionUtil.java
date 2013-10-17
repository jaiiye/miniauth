package org.miniauth.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.util.FormParamUtil;
import org.miniauth.util.QueryParamUtil;


public final class URLConnectionUtil
{
    private static final Logger log = Logger.getLogger(URLConnectionUtil.class.getName());

    private URLConnectionUtil() {}
    
    // Returns the query params without the POST form params.
    public static Map<String,String[]> getQueryParams(HttpURLConnection conn) throws MiniAuthException
    {
        URL url = conn.getURL();
        if(url == null) {
            return null;
        }
        String queryString = url.getQuery();
        if(queryString == null) {
            return null;
        }
        Map<String,String[]> queryParams = QueryParamUtil.parseQueryParams(queryString);
        if(log.isLoggable(Level.FINER)) log.finer("queryParams = " + queryParams);
        return queryParams;
    }
    
    // TBD:
    // This method is to be used while creating a connection.
    // Does it make sense to read the content ????
    public static Map<String,String[]> getFormParams(HttpURLConnection conn) throws MiniAuthException, IOException
    {
        String contentType = conn.getContentType();
        if(contentType == null || !contentType.equals("application/x-www-form-urlencoded")) {
            return null;
        }
        
        String formBody = readFormBody(conn);
        Map<String,String[]> formParams = FormParamUtil.parseUrlEncodedFormParams(contentType, formBody);
        if(log.isLoggable(Level.FINER)) log.finer("formParams = " + formParams);
        return formParams;
    }
    
    // Note: formParams "+" queryParams == requestParams.
    public static Map<String,String[]> getRequestParams(HttpURLConnection conn) throws MiniAuthException, IOException
    {
        Map<String,String[]> queryParams = getQueryParams(conn);
        Map<String,String[]> formParams = getFormParams(conn);
        if(queryParams == null && formParams == null) {
            return null;
        }
        Map<String,String[]> requestParams = new HashMap<>();
        if(formParams != null) {
            requestParams.putAll(formParams);
        }
        if(queryParams != null) {
            requestParams.putAll(queryParams);
        }
        if(log.isLoggable(Level.FINER)) log.finer("requestParams = " + requestParams);
        return requestParams;        
    }
    
    // TBD:
    // Does this make sense???
    public static String readFormBody(HttpURLConnection conn) throws IOException 
    {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = conn.getInputStream();
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
    
    
    // ....
    

}
