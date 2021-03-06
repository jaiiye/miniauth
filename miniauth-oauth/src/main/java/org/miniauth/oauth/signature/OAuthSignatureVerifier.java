package org.miniauth.oauth.signature;

import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.BaseURIInfo;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.credential.AuthCredentialConstants;
import org.miniauth.exception.UnauthorizedException;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.credential.OAuthAccessCredential;
import org.miniauth.oauth.credential.OAuthAccessIdentity;
import org.miniauth.oauth.crypto.OAuthSignatureAlgorithm;
import org.miniauth.oauth.crypto.OAuthSignatureAlgorithmFactory;
import org.miniauth.oauth.nonce.NonceStore;
import org.miniauth.oauth.nonce.NonceStorePool;
import org.miniauth.oauth.util.OAuthSignatureUtil;
import org.miniauth.signature.SignatureVerifier;


/**
 * Signature verifier implementation for OAuth.
 * Cf. http://tools.ietf.org/html/rfc5849#section-3.2 
 */
public class OAuthSignatureVerifier extends OAuthSignatureBase implements SignatureVerifier
{
    private static final Logger log = Logger.getLogger(OAuthSignatureVerifier.class.getName());
    private static final long serialVersionUID = 1L;

    public OAuthSignatureVerifier()
    {
    }

    // oauthParams must include oauth_signature.
    // It returns true if signature is verfied against the given auth credential
    // Otherwise, it throws MiniAuthAuthException (rather than returning false). 
    @Override
    public boolean verify(Map<String, String> authCredential, String httpMethod, URI baseUri, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException
    {
        // For now, we do not distinguish formParams and queryParams.
        // TBD: OAuth spec requires they should be treated separately...
        Map<String,String[]> requestParams = OAuthSignatureUtil.mergeRequestParameters(formParams, queryParams);
        return verify(authCredential, httpMethod, baseUri, authHeader, requestParams);
    }
    @Override
    public boolean verify(Map<String, String> authCredential, String httpMethod, URI baseUri, Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException
    {
        // Steps:
        // validate params
        //    oauth params in only one param set
        //    no duplicates..
        // oauth version
        // signature method
        // check required params for given signature method
        // check timestamp    > 0 or cutoff.
        // <-- badrequest exception
        // ....
        // --> unauthorized exeption
        // check nonce        unique for given timestamp, client, token.
        // token scope ????
        // check signature
        // ...
        
        
        OAuthParamMap oauthParamMap = OAuthSignatureUtil.validateOAuthParams(authHeader, requestParams);
        String signatureMethod = oauthParamMap.getSignatureMethod();
        
        if(SignatureMethod.requiresNonceAndTimestamp(signatureMethod)) {
            
            String nonce = oauthParamMap.getNonce();
            int timestamp = oauthParamMap.getTimestamp();
            
//            AccessIdentity identity = authCredential.getAccessIdentity();
            AccessIdentity identity = null;
            if(authCredential != null) {
                String consumerKey = null;
                if(authCredential.containsKey(AuthCredentialConstants.CONSUMER_KEY)) {
                    consumerKey = authCredential.get(AuthCredentialConstants.CONSUMER_KEY);
                }
                String accessToken = null;
                if(authCredential.containsKey(AuthCredentialConstants.ACCESS_TOKEN)) {
                    accessToken = authCredential.get(AuthCredentialConstants.ACCESS_TOKEN);
                }
                identity = new OAuthAccessIdentity(consumerKey, accessToken);
            }
            NonceStore nonceStore = NonceStorePool.getInstance().getNonceStore(identity);
            
            boolean isNonceNew = nonceStore.check(nonce, timestamp);
            if(isNonceNew == false) {
                throw new UnauthorizedException("Nonce has been used before: nonce = " + nonce + "; timestamp = " + timestamp);
            }
        }
        
        String signature = oauthParamMap.getSignature();

//        AccessCredential accessCredential = authCredential.getAccessCredential();
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

        boolean verified = false;
        if(SignatureMethod.PLAINTEXT.equals(signatureMethod)) {
            verified = oauthSignatureAlgorithm.verify(null, accessCredential, signature);
        } else {
            BaseURIInfo uriInfo = new BaseURIInfo(baseUri);
            // String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeaders, formParams, queryParams);
            String signatureBaseString = buildSignatureBaseString(httpMethod, uriInfo, authHeader, requestParams);
//            log.warning(">>>>>>>>>>> signature = " + signature);
//            log.warning(">>>>>>>>>>> signatureBaseString = " + signatureBaseString);
            verified = oauthSignatureAlgorithm.verify(signatureBaseString, accessCredential, signature);
        }

        if(log.isLoggable(Level.FINE)) log.fine("verified = " + verified);
        // return verified;
        
        if(verified == false) {
            throw new UnauthorizedException("Signaure is incorrect. Request signature = " + signature);
        }
        return true;
    }
    
    
}
