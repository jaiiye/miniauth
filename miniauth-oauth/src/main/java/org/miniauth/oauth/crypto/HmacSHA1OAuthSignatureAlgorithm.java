package org.miniauth.oauth.crypto;

import org.miniauth.credential.AccessCredential;
import org.miniauth.crypto.HmacSHA1SignatureAlgorithm;
import org.miniauth.crypto.SignatureAlgorithm;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.InvalidCredentialException;


public class HmacSHA1OAuthSignatureAlgorithm extends AbstractOAuthSignatureAlgorithm implements OAuthSignatureAlgorithm
{

    public HmacSHA1OAuthSignatureAlgorithm()
    {
        SignatureAlgorithm algorithm = new HmacSHA1SignatureAlgorithm();
        setSignatureAlgorithm(algorithm);
    }

    @Override
    public String generate(String text, AccessCredential credential) throws AuthSignatureException, InvalidCredentialException
    {
        String key = buildKeyString(credential);
        return getSignatureAlgorithm().generate(text, key);
    }
    
    
}
