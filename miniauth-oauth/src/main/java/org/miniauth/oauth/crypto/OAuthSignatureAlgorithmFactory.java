package org.miniauth.oauth.crypto;

import java.util.logging.Logger;

import org.miniauth.oauth.core.SignatureMethod;


/**
 * Factory for creating OAuthSignatureAlgorithm classes.
 */
public class OAuthSignatureAlgorithmFactory
{
    private static final Logger log = Logger.getLogger(OAuthSignatureAlgorithmFactory.class.getName());
    // We can reuse the same objects...
    private static final HmacSHA1OAuthSignatureAlgorithm sHmacSHA1OAuthSignatureAlgorithm = new HmacSHA1OAuthSignatureAlgorithm(); 
    private static final RsaSHA1OAuthSignatureAlgorithm sRsaSHA1OAuthSignatureAlgorithm = new RsaSHA1OAuthSignatureAlgorithm(); 
    private static final PlainTextOAuthSignatureAlgorithm sPlainTextOAuthSignatureAlgorithm = new PlainTextOAuthSignatureAlgorithm(); 
    // ....

    private OAuthSignatureAlgorithmFactory() {}

    // Initialization-on-demand holder.
    private static final class OAuthSignatureAlgorithmFactoryHolder
    {
        private static final OAuthSignatureAlgorithmFactory INSTANCE = new OAuthSignatureAlgorithmFactory();
    }
    // Singleton method
    public static OAuthSignatureAlgorithmFactory getInstance()
    {
        return OAuthSignatureAlgorithmFactoryHolder.INSTANCE;
    }

    /**
     * Returns a signatureAlgorithm implementation of the given signature method.
     * @param signatureMethod 
     * @return A signature algorithm.
     */
    public OAuthSignatureAlgorithm getOAuthSignatureAlgorithm(String signatureMethod)
    {
        if(! SignatureMethod.isValid(signatureMethod)) {
            return null;
        }
        // switch(signatureMethod) {
        // case SignatureMethod.HMAC_SHA1:
        if(signatureMethod.equals(SignatureMethod.HMAC_SHA1)) {
            return sHmacSHA1OAuthSignatureAlgorithm;
        // case SignatureMethod.RSA_SHA1:
        } else if(signatureMethod.equals(SignatureMethod.RSA_SHA1)) {
            return sRsaSHA1OAuthSignatureAlgorithm;
        // case SignatureMethod.PLAINTEXT:
        } else if(signatureMethod.equals(SignatureMethod.PLAINTEXT)) {
            return sPlainTextOAuthSignatureAlgorithm;
        }
        return null;
    }
    
}
