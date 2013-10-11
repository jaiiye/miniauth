package org.miniauth.crypto;

import org.miniauth.exception.AuthSignatureException;


public abstract class AbstractSignatureAlgorithm implements SignatureAlgorithm
{

    public AbstractSignatureAlgorithm()
    {
    }

    @Override
    public boolean verify(String text, String key, String signature) throws AuthSignatureException
    {
        String expectedSignature = generate(text, key);
        return expectedSignature.equals(signature);
    }

}
