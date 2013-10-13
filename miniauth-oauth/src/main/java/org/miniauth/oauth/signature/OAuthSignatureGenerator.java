package org.miniauth.oauth.signature;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.core.BaseURIInfo;
import org.miniauth.credential.AccessCredential;
import org.miniauth.oauth.core.OAuthParamMap;
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
        

        String signatureMethod = OAuthSignatureUtil.getOAuthSignatureMethod(authHeaders, formParams, queryParams);
//        String algoName = SignatureMethod.getAlgorithmName(signatureMethod);
//        if(algoName == null) {
//            throw new AuthSignatureException("Invalid signature method: " + signatureMethod);
//        }
        // ...

        
        OAuthSignatureAlgorithm oauthSignatureAlgorithm = OAuthSignatureAlgorithmFactory.getInstance().getOAuthSignatureAlgorithm(signatureMethod);
        
        String signature = null;
        if(SignatureMethod.PLAINTEXT.equals(signatureMethod)) {
            signature = oauthSignatureAlgorithm.generate(null, credential);
        } else {
            // String consumerKey = null;
            // String consumerSecret = credential.getConsumerSecret();
            // String tokenSecret = credential.getTokenSecret();
            // String keyString = buildKeyString(consumerSecret, tokenSecret);
            String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeaders, formParams, queryParams);
            signature = oauthSignatureAlgorithm.generate(signatureBaseString, credential);
        }

        if(log.isLoggable(Level.FINE)) log.fine("signature = " + signature);
        return signature;
    }
    
    
    // TBD:
    public OAuthParamMap generateOAuthParamMap(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String[]> authHeaders, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        
        
        return null;
    }
    
    
}
