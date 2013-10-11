package org.miniauth.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.miniauth.core.CryptoAlgorithm;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.util.Base64Util;


public class HmacSHA1SignatureAlgorithm extends AbstractSignatureAlgorithm implements SignatureAlgorithm
{

    public HmacSHA1SignatureAlgorithm()
    {
    }

    @Override
    public String generate(String text, String key) throws AuthSignatureException
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

    
    
}
