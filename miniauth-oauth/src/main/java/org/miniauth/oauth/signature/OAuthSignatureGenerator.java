package org.miniauth.oauth.signature;

import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.core.BaseURIInfo;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AuthCredentialConstants;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.credential.OAuthAccessCredential;
import org.miniauth.oauth.crypto.OAuthSignatureAlgorithm;
import org.miniauth.oauth.crypto.OAuthSignatureAlgorithmFactory;
import org.miniauth.oauth.util.OAuthSignatureUtil;
import org.miniauth.signature.SignatureGenerator;


/**
 * Signature generator implementation for OAuth.
 * Cf. http://tools.ietf.org/html/rfc5849#section-3.4 
 */
public class OAuthSignatureGenerator extends OAuthSignatureBase implements SignatureGenerator
{
    private static final Logger log = Logger.getLogger(OAuthSignatureGenerator.class.getName());

    public OAuthSignatureGenerator()
    {
    }

    // request header/params do not include oauth_signature.
    @Override
    public String generate(Map<String, String> authCredential, String httpMethod, URI baseUri, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        // For now, we do not distinguish formParams and queryParams.
        // TBD: OAuth spec requires they should be treated separately...
        Map<String,String[]> requestParams = OAuthSignatureUtil.mergeRequestParameters(formParams, queryParams);
        return generate(authCredential, httpMethod, baseUri, authHeader, requestParams);
    }
    @Override
    public String generate(Map<String, String> authCredential, String httpMethod, URI baseUri, Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException
    {
        // String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeaders, formParams, queryParams);
        String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeader, requestParams);
        if(! SignatureMethod.isValid(signatureMethod)) {
            throw new AuthSignatureException("Invalid signature method: " + signatureMethod);
        }
        // ...
        AccessCredential accessCredential = null;
        if(authCredential != null) {
            String consumerSecret = null;
            if(authCredential.containsKey(AuthCredentialConstants.CONSUMER_SECRET)) {
                consumerSecret = authCredential.get(AuthCredentialConstants.CONSUMER_SECRET);
            }
            String tokenSecret = null;
            if(authCredential.containsKey(AuthCredentialConstants.TOKEN_SECRET)) {
                tokenSecret = authCredential.get(AuthCredentialConstants.TOKEN_SECRET);
            }
            accessCredential = new OAuthAccessCredential(consumerSecret, tokenSecret);
        }
        
        OAuthSignatureAlgorithm oauthSignatureAlgorithm = OAuthSignatureAlgorithmFactory.getInstance().getOAuthSignatureAlgorithm(signatureMethod);
        
        String signature = null;
        if(SignatureMethod.PLAINTEXT.equals(signatureMethod)) {
            signature = oauthSignatureAlgorithm.generate(null, accessCredential);
        } else {
            BaseURIInfo uriInfo = new BaseURIInfo(baseUri);
            // String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeaders, formParams, queryParams);
            String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeader, requestParams);
            signature = oauthSignatureAlgorithm.generate(signatureBaseString, accessCredential);
        }

        if(log.isLoggable(Level.FINE)) log.fine("signature = " + signature);
        return signature;
    }
    
    
    
    // Request header/params do not include oauth_signature.
    // Returned oauthParam map should include oauth_signature.
    @Override
    public Map<String,Object> generateOAuthParams(Map<String, String> authCredential, String httpMethod, URI baseUri, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        OAuthParamMap oAuthParamMap = generateOAuthParamMap(authCredential, httpMethod, baseUri, authHeader, formParams, queryParams);
        return oAuthParamMap.toReadOnlyMap();
    }
    public OAuthParamMap generateOAuthParamMap(Map<String, String> authCredential, String httpMethod, URI baseUri, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        Map<String,String[]> requestParams = OAuthSignatureUtil.mergeRequestParameters(formParams, queryParams);
        return generateOAuthParamMap(authCredential, httpMethod, baseUri, authHeader, requestParams);
    }
    @Override
    public Map<String,Object> generateOAuthParams(Map<String, String> authCredential, String httpMethod, URI baseUri, Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException
    {
        OAuthParamMap oAuthParamMap = generateOAuthParamMap(authCredential, httpMethod, baseUri, authHeader, requestParams);
        return oAuthParamMap.toReadOnlyMap();
    }
    public OAuthParamMap generateOAuthParamMap(Map<String, String> authCredential, String httpMethod, URI baseUri, Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException
    {
        // ...
        AccessCredential accessCredential = null;
        if(authCredential != null) {
            String consumerSecret = null;
            if(authCredential.containsKey(AuthCredentialConstants.CONSUMER_SECRET)) {
                consumerSecret = authCredential.get(AuthCredentialConstants.CONSUMER_SECRET);
            }
            String tokenSecret = null;
            if(authCredential.containsKey(AuthCredentialConstants.TOKEN_SECRET)) {
                tokenSecret = authCredential.get(AuthCredentialConstants.TOKEN_SECRET);
            }
            accessCredential = new OAuthAccessCredential(consumerSecret, tokenSecret);
        }

        // String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeaders, formParams, queryParams);
        String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeader, requestParams);
        OAuthSignatureAlgorithm oauthSignatureAlgorithm = OAuthSignatureAlgorithmFactory.getInstance().getOAuthSignatureAlgorithm(signatureMethod);
      
        OAuthParamMap oAuthParamMap = new OAuthParamMap();
        if(SignatureMethod.PLAINTEXT.equals(signatureMethod)) {
            oAuthParamMap = oauthSignatureAlgorithm.generateOAuthParamMap(null, accessCredential, authHeader, requestParams);
        } else {
            BaseURIInfo uriInfo = new BaseURIInfo(baseUri);
            // String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeaders, formParams, queryParams);
            String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeader, requestParams);
            oAuthParamMap = oauthSignatureAlgorithm.generateOAuthParamMap(signatureBaseString, accessCredential, authHeader, requestParams);
        }
        
        return oAuthParamMap;
    }
    
    
}
