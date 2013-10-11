package org.miniauth.crypto;

import org.miniauth.exception.AuthSignatureException;


public interface SignatureAlgorithm
{
    String generate(String text, String key) throws AuthSignatureException;
    boolean verify(String text, String key, String signature) throws AuthSignatureException;
    
}
