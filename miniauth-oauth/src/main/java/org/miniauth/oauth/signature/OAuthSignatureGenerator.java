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
import org.miniauth.signature.SignatureGenerator;


// http://tools.ietf.org/html/rfc5849#section-3.4
public class OAuthSignatureGenerator extends OAuthSignatureBase implements SignatureGenerator
{
    private static final Logger log = Logger.getLogger(OAuthSignatureGenerator.class.getName());

    public OAuthSignatureGenerator()
    {
    }

    // oauthParams does not include oauth_signature.
    @Override
    public String generate(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        // For now, we do not distinguish formParams and queryParams.
        // TBD: OAuth spec requires they should be treated separately...
        Map<String,String[]> requestParams = OAuthSignatureUtil.mergeRequestParameters(formParams, queryParams);
        return generate(credential, httpMethod, uriInfo, authHeader, requestParams);
    }
    @Override
    public String generate(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException
    {
        // String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeaders, formParams, queryParams);
        String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeader, requestParams);
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
            String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeader, requestParams);
            signature = oauthSignatureAlgorithm.generate(signatureBaseString, credential);
        }

        if(log.isLoggable(Level.FINE)) log.fine("signature = " + signature);
        return signature;
    }
    
    
    
    // TBD:
    public OAuthParamMap generateOAuthParamMap(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        Map<String,String[]> requestParams = OAuthSignatureUtil.mergeRequestParameters(formParams, queryParams);
        return generateOAuthParamMap(credential, httpMethod, uriInfo, authHeader, requestParams);
    }
    public OAuthParamMap generateOAuthParamMap(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException
    {
        // String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeaders, formParams, queryParams);
        String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeader, requestParams);
        OAuthSignatureAlgorithm oauthSignatureAlgorithm = OAuthSignatureAlgorithmFactory.getInstance().getOAuthSignatureAlgorithm(signatureMethod);
      
        OAuthParamMap oAuthParamMap = new OAuthParamMap();
        if(SignatureMethod.PLAINTEXT.equals(signatureMethod)) {
            oAuthParamMap = oauthSignatureAlgorithm.generateOAuthParamMap(null, credential, authHeader, requestParams);
        } else {
            // String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeaders, formParams, queryParams);
            String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeader, requestParams);
            oAuthParamMap = oauthSignatureAlgorithm.generateOAuthParamMap(signatureBaseString, credential, authHeader, requestParams);
        }
        
        return oAuthParamMap;
    }
    
    
}
