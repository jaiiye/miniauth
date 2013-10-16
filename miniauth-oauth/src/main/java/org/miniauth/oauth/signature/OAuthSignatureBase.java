package org.miniauth.oauth.signature;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.core.BaseURIInfo;
import org.miniauth.exception.BadRequestException;
import org.miniauth.exception.ValidationException;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.util.PercentEncoder;


// http://tools.ietf.org/html/rfc5849#section-3.4
public abstract class OAuthSignatureBase
{
    private static final Logger log = Logger.getLogger(OAuthSignatureBase.class.getName());

    protected OAuthSignatureBase()
    {
    }
    
   
    // Builds a "signature base string" from the oauthParams.
    // oauth_signature, if present, should be excluded.
    // authHeaders excludes "realm".
    // formParams are used only if content-type is application/x-www-form-urlencoded, and the body is a single part.
    // It's the caller's responsibility to pass in the proper params.
    protected String buildSignatureBaseString(String httpMethod, BaseURIInfo uriInfo, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(httpMethod.toUpperCase(Locale.US)).append("&");  // httpMethod all caps
        
        String baseUriString = null;
        try {
            baseUriString = uriInfo.buildURIString();
            if(log.isLoggable(Level.FINER)) log.finer("baseUriString = " + baseUriString);
        } catch (URISyntaxException e) {
            throw new BadRequestException("Invalid request URL.", e);
        }
        String encBaseUriString = PercentEncoder.encode(baseUriString);
        sb.append(encBaseUriString).append("&");
        
        // String requestParamString = normalizeRequestParameters(authHeaders, formParams, queryParams);
        String requestParamString = normalizeRequestParameters(authHeader, formParams, queryParams);
        sb.append(requestParamString);
        
        String signatureBaseString = sb.toString();
        if(log.isLoggable(Level.FINE)) log.fine("signatureBaseString = " + signatureBaseString);

        return signatureBaseString;
    }

//    @Deprecated
//    /* private */ String buildBaseUriString(String urlScheme, String host, int port, String path) throws BadRequestException
//    {
//        if((UriScheme.HTTP.equals(urlScheme) && port == 80) || (UriScheme.HTTPS.equals(urlScheme) && port == 443)) {
//            port = -1;
//        }
//        URI baseUri = null;
//        try {
//            baseUri = new URI(urlScheme.toLowerCase(), null, host.toLowerCase(), port, path, null, null);
//        } catch (URISyntaxException e) {
//            throw new BadRequestException("Invalid url.", e);
//        }
//        String baseUriString = baseUri.toString();
//        if(log.isLoggable(Level.FINER)) log.finer("baseUriString = " + baseUriString);
//
//        return baseUriString;
//    }


    protected String normalizeRequestParameters(Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        // Note: Percent encoding is done before sorting...
        Map<String,List<String>> paramMap = percentEncodeRequestParams(authHeader, formParams, queryParams);
        if(paramMap == null) {
            // ???
            return null;
        }

        // Sort by the keys (and by the values for the same keys).
        Comparator<String> byteComparator = new BytewiseComparator();
        StringBuilder sb = new StringBuilder();
        // SortedSet<String> sortedKeys = new TreeSet<String>(paramMap.keySet());
        SortedSet<String> sortedKeys = new TreeSet<String>(byteComparator);
        sortedKeys.addAll(paramMap.keySet());
        for(String eKey : sortedKeys) {
            List<String> eValues = paramMap.get(eKey);
            if(eValues == null || eValues.isEmpty()) {
                sb.append(eKey).append("=").append("&");
            } else {
                Collections.sort(eValues, byteComparator);
                for(String eVal : eValues) {
                    // Can eVal be null at this point???
                    //  (empty string is ok.)
                    sb.append(eKey).append("=").append(eVal).append("&");
                }
            }            
        }

        String paramString = null;
        int buffLen = sb.length();
        if(buffLen > 0 && sb.charAt(buffLen-1) == '&') {
            paramString = sb.substring(0, buffLen-1);
        } else {
            paramString = sb.toString();
        }
        if(log.isLoggable(Level.FINER)) log.finer("paramString = " + paramString);

        return paramString;
    }
     
    // Percent encode params,
    // and filter out signature (from header and params) and realm (in the auth header only).
    protected Map<String,List<String>> percentEncodeRequestParams(Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        boolean oauthParamFoundInHeader = false;
        Map<String,List<String>> paramMap = null;
        if(authHeader != null) {
            paramMap = new HashMap<>();
            for(String h : authHeader.keySet()) {
                if(OAuthConstants.isOAuthParam(h)) {
                    oauthParamFoundInHeader = true;
                }
                if(OAuthConstants.PARAM_OAUTH_SIGNATURE.equals(h) || OAuthConstants.PARAM_REALM.equals(h)) {
                    continue;
                }
                String encodedKey = PercentEncoder.encode(h);
                String value = authHeader.get(h);
                List<String> encodedList = null;
                if(value != null) {
                    encodedList = new ArrayList<>();
                    String encodedValue = PercentEncoder.encode(value);
                    encodedList.add(encodedValue);
                }
                paramMap.put(encodedKey, encodedList);
            }
        }
        boolean oauthParamFoundInForm = false;
        if(formParams != null) {
            if(paramMap == null) {
                paramMap = new HashMap<>();
            }
            for(String k : formParams.keySet()) {
                if(OAuthConstants.isOAuthParam(k)) {
                    if(oauthParamFoundInHeader) {
                        // OAuth param should be only in one part, auth header, form param, or query param.
                        throw new ValidationException("OAuth param already present in header. But found in form again: param = " + k);
                    } else {
                        oauthParamFoundInForm = true;
                    }
                }
                if(OAuthConstants.PARAM_OAUTH_SIGNATURE.equals(k)) {
                    // exclude these...
                    continue;
                }
                String encodedKey = PercentEncoder.encode(k);
                // if(paramMap.containsKey(encodedKey)) {
                //     // This cannot happen.
                //     // If any of the oauth_* param is present in the header
                // }
                String[] values = formParams.get(k);
                List<String> encodedList = null;
                if(values != null) {
                    encodedList = new ArrayList<>();
                    for(String v : values) {
                        String encodedValue = PercentEncoder.encode(v);
                        encodedList.add(encodedValue);
                    }
                }
                paramMap.put(encodedKey, encodedList);
            }
        }
        if(queryParams != null) {
            if(paramMap == null) {
                paramMap = new HashMap<>();
            }
            for(String q : queryParams.keySet()) {
                if(OAuthConstants.isOAuthParam(q)) {
                    if(oauthParamFoundInHeader || oauthParamFoundInForm) {
                        // OAuth param should be only in one part, auth header, form param, or query param.
                        throw new ValidationException("OAuth param already present in header/form. But found in query again: param = " + q);
                    }
                }
                if(OAuthConstants.PARAM_OAUTH_SIGNATURE.equals(q)) {
                    // exclude these...
                    continue;
                }
                String encodedKey = PercentEncoder.encode(q);
                List<String> encodedList = paramMap.get(encodedKey);
                String[] values = queryParams.get(q);
                if(values != null) {
                    if(encodedList == null) {
                        encodedList = new ArrayList<>();
                        paramMap.put(encodedKey, encodedList);
                    }
                    for(String v : values) {
                        String encodedValue = PercentEncoder.encode(v);
                        encodedList.add(encodedValue);
                    }
                } else {
                    // ???
                    if(encodedList == null) {
                        paramMap.put(encodedKey, null);
                    } else {
                        // What to do?
                        // Just ignore, or add an empty string element to the list?
                        // ????
                    }
                }
            }
        }
        return paramMap;
    }

}
