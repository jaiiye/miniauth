package org.miniauth.oauth.signature;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.miniauth.MiniAuthException;
import org.miniauth.core.CryptoAlgorithm;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.BadRequestException;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.OAuthParamMap;
import org.miniauth.oauth.core.ParameterTransmissionType;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.util.ParameterTransmissionUtil;
import org.miniauth.util.Base64Util;
import org.miniauth.util.FormParamUtil;


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
                throw new BadRequestException("OAuth paras found in multiple parameter transmission modes.");
            }
            break;  
        case ParameterTransmissionType.FORM:
            params = formParams;
            if(containsAnyOAuthParam(authHeaders) || containsAnyOAuthParam(queryParams)) {
                throw new BadRequestException("OAuth paras found in multiple parameter transmission modes.");
            }
            break;  
        case ParameterTransmissionType.QUERY:
        // default:  // ???
            params = queryParams;
            if(containsAnyOAuthParam(authHeaders) || containsAnyOAuthParam(formParams)) {
                throw new BadRequestException("OAuth paras found in multiple parameter transmission modes.");
            }
            break;  
        }
        
        return params;
    }

    public static String getOAuthSignatureMethod(Map<String,String[]> authHeaders, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        Map<String,String[]> params = getOAuthParams(authHeaders, formParams, queryParams);
        
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
        Map<String,String[]> authParams = getOAuthParams(authHeaders, formParams, queryParams);
        return validateOAuthParams(authParams);
    }
    public static OAuthParamMap validateOAuthParams(Map<String,String[]> authParams) throws MiniAuthException
    {
        if(authParams == null || authParams.isEmpty()) {
            throw new BadRequestException("OAuth param map is null/empty.");
        }
        
        OAuthParamMap oauthParamMap = new OAuthParamMap();
        
        if(!authParams.containsKey(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY)) {
            throw new BadRequestException("OAuth consumer key is missing.");                
        } else {
            String[] consumerKeys = authParams.get(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY);
            if(consumerKeys == null || consumerKeys.length != 1 || consumerKeys[0] == null || consumerKeys[0].isEmpty()) {
                throw new BadRequestException("Invalid OAuth consumer key..");                
            } else {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, consumerKeys[0]);
            }
        }

        if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_TOKEN)) {
            String[] tokens = authParams.get(OAuthConstants.PARAM_OAUTH_TOKEN);
            if(tokens == null || tokens.length != 1 || tokens[0].isEmpty()) {
                throw new BadRequestException("Invalid OAuth access token.");                
            } else {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_TOKEN, tokens[0]);
            }
        }
        
        String signatureMethod = null;
        if(!authParams.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD)) {
            throw new BadRequestException("OAuth signature method is missing.");                
        } else {
            String[] sigMethods = authParams.get(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD);
            if(sigMethods == null || sigMethods.length != 1) {
                throw new BadRequestException("OAuth signature method param is invalid.");                
            } else {
                signatureMethod = sigMethods[0];
                if(! SignatureMethod.isValid(signatureMethod)) {
                    throw new BadRequestException("Invalid OAuth signature method.");                
                } else {
                    oauthParamMap.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, signatureMethod);
                }
            }
        }

        if(SignatureMethod.requiresNonceAndTimestamp(signatureMethod) && !authParams.containsKey(OAuthConstants.PARAM_OAUTH_TIMESTAMP)) {
            throw new BadRequestException("OAuth timestamp is missing.");                
        } else {
            if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_TIMESTAMP)) {
                String[] timestamps = authParams.get(OAuthConstants.PARAM_OAUTH_TIMESTAMP);
                if(timestamps == null || timestamps.length != 1 || timestamps[0].isEmpty()) {
                    throw new BadRequestException("Invalid OAuth timestamp.");                
                } else {
                    String strTS = timestamps[0];
                    try {
                        int ts = Integer.parseInt(strTS);
                        if(ts <= 0) {
                            throw new BadRequestException("Non-positive OAuth timestamp.");                
                        } else {
                            oauthParamMap.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, ts);
                        }
                    } catch(NumberFormatException e) {
                        throw new BadRequestException("Invalid OAuth timestamp.", e); 
                    }
                }
            }
        }

        if(SignatureMethod.requiresNonceAndTimestamp(signatureMethod) && !authParams.containsKey(OAuthConstants.PARAM_OAUTH_NONCE)) {
            throw new BadRequestException("OAuth nonce is missing.");                
        } else {
            if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_NONCE)) {
                String[] nonces = authParams.get(OAuthConstants.PARAM_OAUTH_NONCE);
                if(nonces == null || nonces.length != 1 || nonces[0].isEmpty()) {
                    throw new BadRequestException("Invalid OAuth nonce.");       
                } else {
                    oauthParamMap.put(OAuthConstants.PARAM_OAUTH_NONCE, nonces[0]);
                }
            }
        }

        if(authParams.containsKey(OAuthConstants.PARAM_OAUTH_VERSION)) {
            String[] versions = authParams.get(OAuthConstants.PARAM_OAUTH_VERSION);
            if(versions == null || versions.length != 1 || !versions[0].equals("1.0")) {
                throw new BadRequestException("Invalid OAuth version.");                
            } else {
                oauthParamMap.put(OAuthConstants.PARAM_OAUTH_VERSION, versions[0]);
            }
        }
        
        // signature?
        // ...
        if(!authParams.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE)) {
            throw new BadRequestException("OAuth signature is missing.");                
        } else {
            String[] signatures = authParams.get(OAuthConstants.PARAM_OAUTH_SIGNATURE);
            if(signatures == null || signatures.length != 1 || signatures[0] == null || signatures[0].isEmpty()) {
                throw new BadRequestException("Invalid OAuth signature..");                
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

    
    
    @Deprecated
    public static String computeHmacSHA1(String text, String key) throws AuthSignatureException
    {
        String signature = null;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), CryptoAlgorithm.HMAC_SHA1_ALGORITHM);
    
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(CryptoAlgorithm.HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
    
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(text.getBytes("UTF-8"));
    
            // base64-encode the hmac
            signature = Base64Util.encodeBase64(rawHmac);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new AuthSignatureException("Failed to generate HMAC.", e);
        }
        return signature;
    }
    
    @Deprecated
    public static boolean verifyHmacSHA1(String text, String key, String signature) throws AuthSignatureException
    {
        String expectedSignature = computeHmacSHA1(text, key);
        return expectedSignature.equals(signature);
    }
    
    
}
