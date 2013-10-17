package org.miniauth.crypto;

import org.miniauth.exception.AuthSignatureException;


/**
 * Interface to define a "signature algorithm".
 * It includes a pair of methods, generate() and verify(). 
 */
public interface SignatureAlgorithm
{
    /**
     * Generates a signature for the given text with the given key.
     * @param text Text to be signed.
     * @param key Cryto Key to sign the text.
     * @return The generated signature.
     * @throws AuthSignatureException
     */

    String generate(String text, String key) throws AuthSignatureException;
    /**
     * Verifies the signature.
     * @param text Text to be signed.
     * @param key Cryto Key to sign the text.
     * @param signature Input signature to be verified.
     * @return true if the input signature matches the signature generated from text/key pair.
     * @throws AuthSignatureException
     */
    boolean verify(String text, String key, String signature) throws AuthSignatureException;

}
