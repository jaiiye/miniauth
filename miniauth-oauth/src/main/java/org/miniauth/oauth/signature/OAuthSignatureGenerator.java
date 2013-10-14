package org.miniauth.oauth.signature;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.core.BaseURIInfo;
import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.crypto.OAuthSignatureAlgorithm;
import org.miniauth.oauth.crypto.OAuthSignatureAlgorithmFactory;


// http://tools.ietf.org/html/rfc5849#section-3.4
public class OAuthSignatureGenerator extends OAuthSignatureBase
{
    private static final Logger log = Logger.getLogger(OAuthSignatureGenerator.class.getName());

    public OAuthSignatureGenerator()
    {
    }

    // oauthParams does not include oauth_signature.
    public String generate(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String[]> authHeaders, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        Map<String,String[]> requestParams = OAuthSignatureUtil.mergeRequestParameters(authHeaders, formParams, queryParams);
        return generate(credential, httpMethod, uriInfo, requestParams);
    }
    public String generate(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String[]> requestParams) throws MiniAuthException
    {
        // String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeaders, formParams, queryParams);
        String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(requestParams);
        if(! SignatureMethod.isValid(signatureMethod)) {
            throw new AuthSignatureException("Invalid signature method: " + signatureMethod);
        }
        // ...
        
        OAuthSignatureAlgorithm oauthSignatureAlgorithm = OAuthSignatureAlgorithmFactory.getInstance().getOAuthSignatureAlgorithm(signatureMethod);
        
        String signature = null;
        if(SignatureMethod.PLAINTEXT.equals(signatureMethod)) {
            signature = oauthSignatureAlgorithm.generate(null, credential);
        } else {
            // String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeaders, formParams, queryParams);
            String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, requestParams);
            signature = oauthSignatureAlgorithm.generate(signatureBaseString, credential);
        }

        if(log.isLoggable(Level.FINE)) log.fine("signature = " + signature);
        return signature;
    }
    
    
    
    // TBD:
    public OAuthParamMap generateOAuthParamMap(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String[]> authHeaders, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        Map<String,String[]> requestParams = OAuthSignatureUtil.mergeRequestParameters(authHeaders, formParams, queryParams);
        return generateOAuthParamMap(credential, httpMethod, uriInfo, requestParams);
    }
    public OAuthParamMap generateOAuthParamMap(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String[]> requestParams) throws MiniAuthException
    {
        
        
//        
//
//        
//        String signature = null;
//        if(paramMap.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE)) {
//            List<String> vals = paramMap.get(OAuthConstants.PARAM_OAUTH_SIGNATURE);
//            if(vals != null && !vals.isEmpty()) {
//                if(vals.size() > 1) {
//                    // error...
//                    throw new ValidationException("More than one OAuth signature found in the request parameters.");
//                } else {
//                    signature = vals.get(0);
//                }
//            } else {
//                // ???
//                throw new ValidationException("OAuth signature param is in the request parameters, but its value is null/empty.");
//            }
//        }
//        if(signature == null) {
//            // ????
//            signature = generate(credential, httpMethod, uriInfo, authHeaders, formParams, queryParams);
//        }
//        oAuthParamMap.setSignature(signature);
//        
//        String consumerKey = null;
//        String accessToken = null;
//        for(String key : paramMap.keySet()) {
//            if(key.equals(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY)) {
//                List<String> vals = paramMap.get(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY);
//                if(vals != null && !vals.isEmpty()) {
//                    if(vals.size() > 1) {
//                        // error...
//                        throw new ValidationException("More than one OAuth consumer key found in the request parameters.");
//                    } else {
//                        consumerKey = vals.get(0);
//                    }
//                }
//            } else if(key.equals(OAuthConstants.PARAM_OAUTH_TOKEN)) {
//                List<String> vals = paramMap.get(OAuthConstants.PARAM_OAUTH_TOKEN);
//                if(vals != null && !vals.isEmpty()) {
//                    if(vals.size() > 1) {
//                        // error...
//                        throw new ValidationException("More than one OAuth access token found in the request parameters.");
//                    } else {
//                        accessToken = vals.get(0);
//                    }
//                }
//            } else {
//                // ???
//            }
//            // etc...
//            
//        }
//
//        if(consumerKey == null) {
//            throw new ValidationException("OAuth consumer key is not set.");
//        }
//        if(accessToken == null) {
//            throw new ValidationException("OAuth access token is not set.");
//        }
//        
//
//        oAuthParamMap.setConsumerKey(consumerKey);
//        oAuthParamMap.setToken(accessToken);
//        
        

        // String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeaders, formParams, queryParams);
        String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(requestParams);
        OAuthSignatureAlgorithm oauthSignatureAlgorithm = OAuthSignatureAlgorithmFactory.getInstance().getOAuthSignatureAlgorithm(signatureMethod);
      
        OAuthParamMap oAuthParamMap = new OAuthParamMap();
        if(SignatureMethod.PLAINTEXT.equals(signatureMethod)) {
            oAuthParamMap = oauthSignatureAlgorithm.generateOAuthParamMap(null, credential, requestParams);
        } else {
            // String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeaders, formParams, queryParams);
            String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, requestParams);
            oAuthParamMap = oauthSignatureAlgorithm.generateOAuthParamMap(signatureBaseString, credential, requestParams);
        }
        
        return oAuthParamMap;
    }
    
    
}
