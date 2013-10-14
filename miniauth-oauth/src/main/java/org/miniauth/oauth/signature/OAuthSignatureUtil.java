package org.miniauth.oauth.signature;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.miniauth.MiniAuthException;
import org.miniauth.core.CryptoAlgorithm;
import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.ValidationException;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.util.ParameterTransmissionUtil;
import org.miniauth.util.Base64Util;
import org.miniauth.util.FormParamUtil;
import org.miniauth.util.ParamMapUtil;


public final class OAuthSignatureUtil
{
    private static final Logger log = Logger.getLogger(OAuthSignatureUtil.class.getName());

    private OAuthSignatureUtil() {}
    
    public static boolean containsAnyOAuthParam(Map<String,String[]> params) throws MiniAuthException
    {
        if(params == null || params.isEmpty()) {
            return false;
        }
        for(String key : params.keySet()) {
            if(OAuthConstants.isOAuthParam(key)) {
                return true;
            }
        }
        return false;
    }

    public static Map<String,String[]> getOAuthParams(Map<String,String[]> authHeaders, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        Map<String,String[]> params = null;
        String ptType = ParameterTransmissionUtil.getTransmissionType(authHeaders, formParams, queryParams);
        switch(ptType) {
        case ParameterTransmissionType.HEADER:
            params = authHeaders;
            if(containsAnyOAuthParam(formParams) || containsAnyOAuthParam(queryParams)) {
                throw new ValidationException("OAuth paras found in multiple parameter transmission modes.");
            }
            break;  
        case ParameterTransmissionType.FORM:
            params = formParams;
            if(containsAnyOAuthParam(authHeaders) || containsAnyOAuthParam(queryParams)) {
                throw new ValidationException("OAuth paras found in multiple parameter transmission modes.");
            }
            break;  
        case ParameterTransmissionType.QUERY:
        // default:  // ???
            params = queryParams;
            if(containsAnyOAuthParam(authHeaders) || containsAnyOAuthParam(formParams)) {
                throw new ValidationException("OAuth paras found in multiple parameter transmission modes.");
            }
            break;  
        }
        
        return params;
    }

    
    
    public static String getOAuthSignatureMethod(Map<String,String[]> authHeaders, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        // Note: we call getOAuthParams() not mergeRequestParameters()...
        Map<String,String[]> params = getOAuthParams(authHeaders, formParams, queryParams);
        return getOAuthSignatureMethod(params);
    }
    public static String getOAuthSignatureMethod(Map<String,String[]> params) throws MiniAuthException
    {
        String signatureMethod = null;
        if(params != null) {
            String[] valArray = params.get(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD);
            signatureMethod = valArray[0];
        } else {
            // throw exception ???
        }
        
        return signatureMethod;
    }

    // It returns the oauthParam map (instead of boolean, as the method name seems to imply).
    // If validation fails, it throws exception (rather than returning false).
    public static OAuthParamMap validateOAuthParams(Map<String,String[]> authHeaders, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        // Note: we call getOAuthParams() not mergeRequestParameters()...
        Map<String,String[]> authParams = getOAuthParams(authHeaders, formParams, queryParams);
        return validateOAuthParams(authParams);
    }
    public static OAuthParamMap validateOAuthParams(Map<String,String[]> authParams) throws MiniAuthException
    {
        if(authParams == null || authParams.isEmpty()) {
            throw new ValidationException("OAuth param map is null/empty.");
        }
        
        OAuthParamMap oauthParamMap = new OAuthParamMap();
        
        if(!authParams.containsKey(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY)) {
            throw new ValidationException("OAuth consumer key is missing.");                
        } else {
            String[] consumerKeys = authParams.get(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY);
            if(consumerKeys == null || consumerKeys.length != 1 || consumerKeys[0] == null || consumerKeys[0].isEmpty()) {
                throw new ValidationException("Invalid OAuth consumer key..");                
            } else {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, consumerKeys[0]);
            }
        }

        if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_TOKEN)) {
            String[] tokens = authParams.get(OAuthConstants.PARAM_OAUTH_TOKEN);
            if(tokens == null || tokens.length != 1 || tokens[0].isEmpty()) {
                throw new ValidationException("Invalid OAuth access token.");                
            } else {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_TOKEN, tokens[0]);
            }
        }
        
        String signatureMethod = null;
        if(!authParams.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD)) {
            throw new ValidationException("OAuth signature method is missing.");                
        } else {
            String[] sigMethods = authParams.get(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD);
            if(sigMethods == null || sigMethods.length != 1) {
                throw new ValidationException("OAuth signature method param is invalid.");                
            } else {
                signatureMethod = sigMethods[0];
                if(! SignatureMethod.isValid(signatureMethod)) {
                    throw new ValidationException("Invalid OAuth signature method.");                
                } else {
                    oauthParamMap.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, signatureMethod);
                }
            }
        }

        if(SignatureMethod.requiresNonceAndTimestamp(signatureMethod) && !authParams.containsKey(OAuthConstants.PARAM_OAUTH_TIMESTAMP)) {
            throw new ValidationException("OAuth timestamp is missing.");                
        } else {
            if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_TIMESTAMP)) {
                String[] timestamps = authParams.get(OAuthConstants.PARAM_OAUTH_TIMESTAMP);
                if(timestamps == null || timestamps.length != 1 || timestamps[0].isEmpty()) {
                    throw new ValidationException("Invalid OAuth timestamp.");                
                } else {
                    String strTS = timestamps[0];
                    try {
                        int ts = Integer.parseInt(strTS);
                        if(ts <= 0) {
                            throw new ValidationException("Non-positive OAuth timestamp.");                
                        } else {
                            oauthParamMap.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, ts);
                        }
                    } catch(NumberFormatException e) {
                        throw new ValidationException("Invalid OAuth timestamp.", e); 
                    }
                }
            }
        }

        if(SignatureMethod.requiresNonceAndTimestamp(signatureMethod) && !authParams.containsKey(OAuthConstants.PARAM_OAUTH_NONCE)) {
            throw new ValidationException("OAuth nonce is missing.");                
        } else {
            if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_NONCE)) {
                String[] nonces = authParams.get(OAuthConstants.PARAM_OAUTH_NONCE);
                if(nonces == null || nonces.length != 1 || nonces[0].isEmpty()) {
                    throw new ValidationException("Invalid OAuth nonce.");       
                } else {
                    oauthParamMap.put(OAuthConstants.PARAM_OAUTH_NONCE, nonces[0]);
                }
            }
        }

        if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_VERSION)) {
            String[] versions = authParams.get(OAuthConstants.PARAM_OAUTH_VERSION);
            if(versions == null || versions.length != 1 || !versions[0].equals("1.0")) {
                throw new ValidationException("Invalid OAuth version.");                
            } else {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_VERSION, versions[0]);
            }
        }
        
        // signature?
        // ...
        if(!authParams.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE)) {
            throw new ValidationException("OAuth signature is missing.");                
        } else {
            String[] signatures = authParams.get(OAuthConstants.PARAM_OAUTH_SIGNATURE);
            if(signatures == null || signatures.length != 1 || signatures[0] == null || signatures[0].isEmpty()) {
                throw new ValidationException("Invalid OAuth signature..");                
            } else {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_SIGNATURE, signatures[0]);
            }
        }
        
        if(log.isLoggable(Level.FINER)) log.finer("oauthParamMap = " + oauthParamMap);
        return oauthParamMap;
    }
    
    
    // Builds a URL encoded param string so that it can be included in an Authorization header (or, query/form param).
    public static String buildOAuthParamString(Map<String,Object> oauthParams) throws MiniAuthException
    {
        return buildOAuthParamString(new OAuthParamMap(oauthParams));
    }
    public static String buildOAuthParamString(OAuthParamMap oauthParamMap) throws MiniAuthException
    {
        if(oauthParamMap == null) {
            return null;
        }
        return oauthParamMap.buildUrlEncodedParamString();
    }    

    
    // Merge params without percent encoding...
    // TBD:
    // oauth_signature and realm should be filtered while generating the "signature base string"???
    // unfortunately, once params are merged, we have no way to tell where the "realm" param came from originally...
    // ....
    public static Map<String,String[]> mergeRequestParameters(Map<String,String[]> authHeaders, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        Map<String,List<String>> paramMap = new HashMap<>();
        if(authHeaders != null) {
            for(String key : authHeaders.keySet()) {
//                if(OAuthConstants.PARAM_OAUTH_SIGNATURE.equals(key) || OAuthConstants.PARAM_REALM.equals(key)) {
//                    // exclude these...
//                    continue;
//                }
                String[] values = authHeaders.get(key);
                List<String> valueList = paramMap.get(key);
                if(valueList == null) {
                    valueList = new ArrayList<>();
                    paramMap.put(key, valueList);
                }
                // valueList.addAll(Arrays.asList(values));
                for(String v : values) {
                    valueList.add(v);
                }
            }
        }
        if(formParams != null) {
            for(String key : formParams.keySet()) {
//                if(OAuthConstants.PARAM_OAUTH_SIGNATURE.equals(key)) {
//                    // exclude this..
//                    continue;
//                }
                String[] values = formParams.get(key);
                List<String> valueList = paramMap.get(key);
                if(valueList == null) {
                    valueList = new ArrayList<>();
                    paramMap.put(key, valueList);
                }
                // valueList.addAll(Arrays.asList(values));
                for(String v : values) {
                    valueList.add(v);
                }
            }
        }
        if(queryParams != null) {
            for(String key : queryParams.keySet()) {
//                if(OAuthConstants.PARAM_OAUTH_SIGNATURE.equals(key)) {
//                    // exclude this..
//                    continue;
//                }
                String[] values = queryParams.get(key);
                List<String> valueList = paramMap.get(key);
                if(valueList == null) {
                    valueList = new ArrayList<>();
                    paramMap.put(key, valueList);
                }
                // valueList.addAll(Arrays.asList(values));
                for(String v : values) {
                    valueList.add(v);
                }
            }
        }
        Map<String,String[]> requestParams = ParamMapUtil.convertStringListMapToStringArrayMap(paramMap);
        return requestParams;
    }
    
    
}
