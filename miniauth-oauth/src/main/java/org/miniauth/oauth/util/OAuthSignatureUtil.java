package org.miniauth.oauth.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.exception.ValidationException;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.util.ParamMapUtil;


/**
 * OAuth signature-related utility methods.
 */
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

    // TBD:
    // Need to re-implement all these methods (for getting transmissionType, oauthParams, etc.)... 
    public static Map<String,String> getOAuthParams(Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException
    {
        Map<String,String> params = null;
        if(authHeader != null && !authHeader.isEmpty()) {
            params = authHeader;
        } else if(requestParams != null && requestParams.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD) ) {
            params = new HashMap<String,String>();
            for(String q : requestParams.keySet()) {
                if(OAuthConstants.isOAuthParam(q)) {
                    String[] valArr = requestParams.get(q);
                    if(valArr == null || valArr.length != 1) {
                        throw new ValidationException("Multiple values found for the OAuth param: " + q);
                    }
                    params.put(q, valArr[0]);
                }
            }
        }
        return params;
    }
    public static Map<String,String> getOAuthParams(Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        Map<String,String> params = null;
        String ptType = ParameterTransmissionUtil.getTransmissionType(authHeader, formParams, queryParams);
        if(ptType == null) {
            return authHeader;   // ????
        }
        // switch(ptType) {
        // case ParameterTransmissionType.HEADER:
        if(ptType.equals(ParameterTransmissionType.HEADER)) {
            params = authHeader;
//            if(containsAnyOAuthParam(formParams) || containsAnyOAuthParam(queryParams)) {
//                throw new ValidationException("OAuth params found in multiple parameter transmission modes.");
//            }
            // break;  
        // case ParameterTransmissionType.FORM:
        } else if(ptType.equals(ParameterTransmissionType.FORM)) {
//            params = formParams;
//            if(containsAnyOAuthParam(authHeader) || containsAnyOAuthParam(queryParams)) {
//                throw new ValidationException("OAuth params found in multiple parameter transmission modes.");
//            }
            params = new HashMap<String,String>();
            for(String k : formParams.keySet()) {
                if(OAuthConstants.isOAuthParam(k)) {
                    String[] valArr = formParams.get(k);
                    if(valArr == null || valArr.length != 1) {
                        throw new ValidationException("Multiple values found for the OAuth param: " + k);
                    }
                    params.put(k, valArr[0]);
                }
            }
            // break;  
        // case ParameterTransmissionType.QUERY:
        // default:  // ???
        } else if(ptType.equals(ParameterTransmissionType.QUERY)) {
//            params = queryParams;
//            if(containsAnyOAuthParam(authHeader) || containsAnyOAuthParam(formParams)) {
//                throw new ValidationException("OAuth params found in multiple parameter transmission modes.");
//            }
            params = new HashMap<String,String>();
            for(String q : queryParams.keySet()) {
                if(OAuthConstants.isOAuthParam(q)) {
                    String[] valArr = queryParams.get(q);
                    if(valArr == null || valArr.length != 1) {
                        throw new ValidationException("Multiple values found for the OAuth param: " + q);
                    }
                    params.put(q, valArr[0]);
                }
            }
            // break;  
        }
        
        return params;
    }

    
    
    public static String getOAuthSignatureMethod(Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        Map<String,String[]> requestParams = mergeRequestParameters(formParams, queryParams);
        return getOAuthSignatureMethod(authHeader, requestParams);
    }
    public static String getOAuthSignatureMethod(Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException
    {
        // Note: we call getOAuthParams() not mergeRequestParameters()...
        Map<String,String> oauthParams = getOAuthParams(authHeader, requestParams);
        return getOAuthSignatureMethod(oauthParams);
    }
    public static String getOAuthSignatureMethod(Map<String,String> oauthParams) throws MiniAuthException
    {
        String signatureMethod = null;
        if(oauthParams != null) {
            signatureMethod = oauthParams.get(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD);
        } else {
            // throw exception ??? Just return null?
        }
        
        return signatureMethod;
    }


    // TBD:
    // We need to keep formParams and queryParams separate for validation purposes...
    // Currently, we merge them in the early stage of validation, which makes it impossible to validate against errors like
    //  (1) oauth_X params cannot be put into more than one part.. e.g., if one oauth_x is in form param, and another oauth_y is in query param,
    //         then that is an error according to the (extremely messy) OAuth1 spec.
    //         However, once we merge these two param set we have no way to detect errors like this...
    //  etc...
    // ....

    
    // It returns the oauthParam map (instead of boolean, as the method name seems to imply).
    // If validation fails, it throws exception (rather than returning false).
    // signatureRequired==false means we are validating request before the full request has been constructed.
    // Normally (e.g., when validating request on the provider side), signatureRequired should be true.
    public static OAuthParamMap validateOAuthParams(Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        return validateOAuthParams(authHeader, formParams, queryParams, true);
    }
    public static OAuthParamMap validateOAuthParams(Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams, boolean signatureRequired) throws MiniAuthException
    {
        // Note: we call getOAuthParams() not mergeRequestParameters()...
        Map<String,String> authParams = getOAuthParams(authHeader, formParams, queryParams);
        return validateOAuthParams(authParams, signatureRequired);
    }

    public static OAuthParamMap validateOAuthParams(Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException
    {
        return validateOAuthParams(authHeader, requestParams, true);
    }
    public static OAuthParamMap validateOAuthParams(Map<String,String> authHeader, Map<String,String[]> requestParams, boolean signatureRequired) throws MiniAuthException
    {
        // Note: we call getOAuthParams() not mergeRequestParameters()...
        Map<String,String> authParams = getOAuthParams(authHeader, requestParams);
        return validateOAuthParams(authParams, signatureRequired);
    }

    public static OAuthParamMap validateOAuthParams(Map<String,String> authParams) throws MiniAuthException
    {
        return validateOAuthParams(authParams, true);
    }
    public static OAuthParamMap validateOAuthParams(Map<String,String> authParams, boolean signatureRequired) throws MiniAuthException
    {
        if(authParams == null || authParams.isEmpty()) {
            throw new ValidationException("OAuth param map is null/empty.");
        }

        OAuthParamMap oauthParamMap = new OAuthParamMap();
        
        if(!authParams.containsKey(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY)) {
            throw new ValidationException("OAuth consumer key is missing.");                
        } else {
            String consumerKey = authParams.get(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY);
            if(consumerKey == null || consumerKey.isEmpty()) {
                throw new ValidationException("Invalid OAuth consumer key..");                
            } else {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, consumerKey);
            }
        }

        if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_TOKEN)) {
            String token = authParams.get(OAuthConstants.PARAM_OAUTH_TOKEN);
            if(token == null || token.isEmpty()) {
                throw new ValidationException("Invalid OAuth access token.");                
            } else {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_TOKEN, token);
            }
        }

        String signatureMethod = null;
        if(!authParams.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD)) {
            throw new ValidationException("OAuth signature method is missing.");                
        } else {
            String sigMethod = authParams.get(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD);
            if(sigMethod == null || sigMethod.isEmpty()) {
                throw new ValidationException("OAuth signature method param is invalid.");                
            } else {
                if(! SignatureMethod.isValid(sigMethod)) {
                    throw new ValidationException("Invalid OAuth signature method: " + sigMethod);                
                } else {
                    signatureMethod = sigMethod;
                    oauthParamMap.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, signatureMethod);
                }
            }
        }

        if(SignatureMethod.requiresNonceAndTimestamp(signatureMethod) && !authParams.containsKey(OAuthConstants.PARAM_OAUTH_TIMESTAMP)) {
            throw new ValidationException("OAuth timestamp is missing.");                
        } else {
            if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_TIMESTAMP)) {
                String timestampStr = authParams.get(OAuthConstants.PARAM_OAUTH_TIMESTAMP);
                if(timestampStr == null || timestampStr.isEmpty()) {
                    throw new ValidationException("Invalid OAuth timestamp.");                
                } else {
                    try {
                        int ts = Integer.parseInt(timestampStr);
                        if(ts <= 0) {
                            throw new ValidationException("Non-positive OAuth timestamp: " + timestampStr);                
                        } else {
                            oauthParamMap.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, ts);
                        }
                    } catch(NumberFormatException e) {
                        throw new ValidationException("Invalid OAuth timestamp: " + timestampStr, e); 
                    }
                }
            }
        }

        if(SignatureMethod.requiresNonceAndTimestamp(signatureMethod) && !authParams.containsKey(OAuthConstants.PARAM_OAUTH_NONCE)) {
            throw new ValidationException("OAuth nonce is missing.");                
        } else {
            if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_NONCE)) {
                String nonce = authParams.get(OAuthConstants.PARAM_OAUTH_NONCE);
                if(nonce == null || nonce.isEmpty()) {
                    throw new ValidationException("Invalid OAuth nonce.");       
                } else {
                    oauthParamMap.put(OAuthConstants.PARAM_OAUTH_NONCE, nonce);
                }
            }
        }

        if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_VERSION)) {
            String version = authParams.get(OAuthConstants.PARAM_OAUTH_VERSION);
            if(version == null || !version.equals(OAuthConstants.OAUTH_VERSION_STRING)) {
                throw new ValidationException("Invalid OAuth version.");                
            } else {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_VERSION, version);
            }
        }
        
        // signature?
        if(!authParams.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE)) {
            if(signatureRequired) {
                throw new ValidationException("OAuth signature is missing.");
            }
        } else {
            String signature = authParams.get(OAuthConstants.PARAM_OAUTH_SIGNATURE);
            if(signatureRequired && (signature == null || signature.isEmpty())) {
                throw new ValidationException("Invalid OAuth signature.");                
            }
            // ???
            // Just keep the param even if the value is null/empty ???
            // if(signature != null && !signature.isEmpty()) {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_SIGNATURE, signature);
            // }
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
    // No "filtering" (for signature and realm).
    // This method may be used generically (not in the context of gnerating a signature),
    //   and hence params should not be filtered...
    public static Map<String,String[]> mergeRequestParameters(Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        return mergeRequestParameters(null, formParams, queryParams);
    }
    public static Map<String,String[]> mergeRequestParameters(Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        Map<String,List<String>> paramMap = new HashMap<String,List<String>>();
        if(authHeader != null) {
            for(String key : authHeader.keySet()) {
                String value = authHeader.get(key);
                List<String> valueList = new ArrayList<String>();
                valueList.add(value);
                paramMap.put(key, valueList);
            }
        }
        if(formParams != null) {
            for(String key : formParams.keySet()) {
                String[] values = formParams.get(key);
                List<String> valueList = paramMap.get(key);
                if(valueList == null) {
                    valueList = new ArrayList<String>();
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
                String[] values = queryParams.get(key);
                List<String> valueList = paramMap.get(key);
                if(valueList == null) {
                    valueList = new ArrayList<String>();
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
