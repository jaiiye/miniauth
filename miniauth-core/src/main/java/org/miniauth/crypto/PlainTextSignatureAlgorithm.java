package org.miniauth.crypto;

import org.miniauth.exception.AuthSignatureException;


/**
 * "Plain text" signature algorithm per OAuth standard.
 */
public class PlainTextSignatureAlgorithm extends AbstractSignatureAlgorithm implements SignatureAlgorithm
{

    public PlainTextSignatureAlgorithm()
    {
    }

    @Override
    public String generate(String text, String key) throws AuthSignatureException
    {
        return key;
    }
    
    
}
