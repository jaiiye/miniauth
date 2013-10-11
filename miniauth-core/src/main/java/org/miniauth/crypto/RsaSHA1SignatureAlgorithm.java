package org.miniauth.crypto;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.core.CryptoAlgorithm;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.util.Base64Util;


public class RsaSHA1SignatureAlgorithm extends AbstractSignatureAlgorithm implements SignatureAlgorithm
{
    private static final Logger log = Logger.getLogger(RsaSHA1SignatureAlgorithm.class.getName());
    private static final String KEY_TYPE = "RSA";
    private static final String BEGIN_CERT = "-----BEGIN CERTIFICATE";

    public RsaSHA1SignatureAlgorithm()
    {
    }

    @Override
    public String generate(String text, String key) throws AuthSignatureException
    {
        String signature = null;
        try {
            byte[] decodedPrivKey = Base64Util.decode(key);
            KeyFactory keyFact = KeyFactory.getInstance(KEY_TYPE);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivKey);
            RSAPrivateKey rsaPrivKey = (RSAPrivateKey) keyFact.generatePrivate(keySpec);
            Signature signer = Signature.getInstance(CryptoAlgorithm.RSA_SHA1_ALGORITHM);
            signer.initSign(rsaPrivKey);
            signer.update(text.getBytes());
            byte[] rsasha1 = signer.sign();
            signature = Base64Util.encodeBase64(rsasha1);
        } catch (NoSuchAlgorithmException ex) {
            throw new AuthSignatureException("Unspported algorithm: " + CryptoAlgorithm.RSA_SHA1_ALGORITHM, ex);
        } catch (InvalidKeySpecException ex) {
            throw new AuthSignatureException("Invalid RSA keyspec", ex);
        } catch (InvalidKeyException ex) {
            throw new AuthSignatureException("Invalid key", ex);
        } catch (SignatureException ex) {
            throw new AuthSignatureException("Failed to generate signature.", ex);
        }

        if(log.isLoggable(Level.FINER)) log.finer("signature = " + signature);
        return signature;
    }

    @Override
    public boolean verify(String text, String key, String signature) throws AuthSignatureException
    {
        boolean verified = false;
        try {
            // String decodedKey = Base64Util.decodeBase64(key);   // ???
            Signature signer = Signature.getInstance(CryptoAlgorithm.RSA_SHA1_ALGORITHM);
            RSAPublicKey rsaPubKey = null;
            // if (decodedKey.startsWith(BEGIN_CERT)) {
            if (key.startsWith(BEGIN_CERT)) {
                Certificate cert = null;
                // ByteArrayInputStream bais = new ByteArrayInputStream(decodedKey.getBytes());
                ByteArrayInputStream bais = new ByteArrayInputStream(key.getBytes());
                BufferedInputStream bis = new BufferedInputStream(bais);
                CertificateFactory certfac = CertificateFactory.getInstance("X.509");
                while (bis.available() > 0) {
                    cert = certfac.generateCertificate(bis);
                }
                rsaPubKey = (RSAPublicKey) cert.getPublicKey();
            }            
            byte[] decodedSignature = Base64Util.decode(signature);
            signer.initVerify(rsaPubKey);
            signer.update(text.getBytes());
            verified = signer.verify(decodedSignature);
        } catch (NoSuchAlgorithmException ex) {
            throw new AuthSignatureException("Unspported algorithm: " + CryptoAlgorithm.RSA_SHA1_ALGORITHM, ex);
        } catch (IOException ex) {
            throw new AuthSignatureException(ex);
        } catch (CertificateException ex) {
            throw new AuthSignatureException(ex);
        } catch (InvalidKeyException ex) {
            throw new AuthSignatureException("Invalid key", ex);
        } catch (SignatureException ex) {
            throw new AuthSignatureException("Failed to verify signature.", ex);
        }
        
        if(log.isLoggable(Level.FINER)) log.finer("signature verified = " + verified);
        return verified;
    }

    
}
