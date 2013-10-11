package org.miniauth.oauth.signature;

import java.net.URI;
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
import org.miniauth.core.UriScheme;
import org.miniauth.exception.BadRequestException;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.credential.AccessCredential;
import org.miniauth.oauth.crypto.OAuthSignatureAlgorithm;
import org.miniauth.oauth.crypto.OAuthSignatureAlgorithmFactory;
import org.miniauth.oauth.util.PercentEncoder;


// http://tools.ietf.org/html/rfc5849#section-3.4
public abstract class OAuthSignatureBase
{
    private static final Logger log = Logger.getLogger(OAuthSignatureBase.class.getName());

    protected OAuthSignatureBase()
    {
    }
    

    @Deprecated
    private String buildKeyString(String consumerSecret, String tokenSecret) throws MiniAuthException
    {
        StringBuilder sb = new StringBuilder();

        sb.append(PercentEncoder.encode(consumerSecret));
        sb.append("&");
        sb.append(PercentEncoder.encode(tokenSecret));
        
        String keyString = sb.toString();
        if(log.isLoggable(Level.FINER)) log.finer("keyString = " + keyString);
        return keyString;
    }
    
    // Builds a "signature base string" from the oauthParams.
    // oauth_signature, if present, should be excluded.
    // authHeaders excludes "realm".
    // formParams are used only if content-type is application/x-www-form-urlencoded, and the body is a single part.
    // It's the caller's responsibility to pass in the proper params.
    protected String buildSignatureBaseString(String httpMethod, BaseURIInfo uriInfo, Map<String,String[]> authHeaders, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
//        // tbd
//        // String httpMethod = uriInfo.getHttpMethod();
//        String urlScheme = uriInfo.getUriScheme();
//        String userInfo = uriInfo.getUserInfo();
//        String host = uriInfo.getHost();
//        int port = uriInfo.getPort();
//        String path = uriInfo.getPath();
        
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
        
        String requestParamString = normalizeRequestParameters(authHeaders, formParams, queryParams);
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

    protected String normalizeRequestParameters(Map<String,String[]> authHeaders, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
//        Map<String,String> superMap = new HashMap<>();
//        if(authHeaders != null) {
//            for(String key : authHeaders.keySet()) {
//                String[] values = authHeaders.get(key);
//                String encodedKey = PercentEncoder.encode(key);
//                for(String v : values) {
//                    String encodedValue = PercentEncoder.encode(v);
//                    superMap.put(encodedKey, encodedValue);
//                }
//            }
//        }
//        if(formParams != null) {
//            for(String key : formParams.keySet()) {
//                String[] values = formParams.get(key);
//                String encodedKey = PercentEncoder.encode(key);
//                for(String v : values) {
//                    String encodedValue = PercentEncoder.encode(v);
//                    superMap.put(encodedKey, encodedValue);
//                }
//            }
//        }
//        if(queryParams != null) {
//            for(String key : queryParams.keySet()) {
//                String[] values = queryParams.get(key);
//                String encodedKey = PercentEncoder.encode(key);
//                for(String v : values) {
//                    String encodedValue = PercentEncoder.encode(v);
//                    superMap.put(encodedKey, encodedValue);
//                }
//            }
//        }

        Map<String,List<String>> paramMap = new HashMap<>();
        if(authHeaders != null) {
            for(String key : authHeaders.keySet()) {
                if(OAuthConstants.PARAM_OAUTH_SIGNATURE.equals(key) || OAuthConstants.PARAM_REALM.equals(key)) {
                    // exclude these...
                    continue;
                }
                String[] values = authHeaders.get(key);
                String encodedKey = PercentEncoder.encode(key);
                List<String> valueList = paramMap.get(encodedKey);
                if(valueList == null) {
                    valueList = new ArrayList<>();
                    paramMap.put(encodedKey, valueList);
                }
                // valueList.addAll(Arrays.asList(values));
                for(String v : values) {
                    String encodedValue = PercentEncoder.encode(v);
                    valueList.add(encodedValue);
                }
            }
        }
        if(formParams != null) {
            for(String key : formParams.keySet()) {
                if(OAuthConstants.PARAM_OAUTH_SIGNATURE.equals(key)) {
                    // exclude this..
                    continue;
                }
                String[] values = formParams.get(key);
                String encodedKey = PercentEncoder.encode(key);
                List<String> valueList = paramMap.get(encodedKey);
                if(valueList == null) {
                    valueList = new ArrayList<>();
                    paramMap.put(encodedKey, valueList);
                }
                // valueList.addAll(Arrays.asList(values));
                for(String v : values) {
                    String encodedValue = PercentEncoder.encode(v);
                    valueList.add(encodedValue);
                }
            }
        }
        if(queryParams != null) {
            for(String key : queryParams.keySet()) {
                if(OAuthConstants.PARAM_OAUTH_SIGNATURE.equals(key)) {
                    // exclude this..
                    continue;
                }
                String[] values = queryParams.get(key);
                String encodedKey = PercentEncoder.encode(key);
                List<String> valueList = paramMap.get(encodedKey);
                if(valueList == null) {
                    valueList = new ArrayList<>();
                    paramMap.put(encodedKey, valueList);
                }
                // valueList.addAll(Arrays.asList(values));
                for(String v : values) {
                    String encodedValue = PercentEncoder.encode(v);
                    valueList.add(encodedValue);
                }
            }
        }

        Comparator<String> byteComparator = new BytewiseComparator();
        StringBuilder sb = new StringBuilder();
        // SortedSet<String> sortedKeys = new TreeSet<String>(paramMap.keySet());
        SortedSet<String> sortedKeys = new TreeSet<String>(byteComparator);
        sortedKeys.addAll(paramMap.keySet());
        for(String eKey : sortedKeys) {
            List<String> eValues = paramMap.get(eKey);
            if(eValues.isEmpty()) {
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
     
    
    
}
