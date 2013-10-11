package org.miniauth.oauth.crypto;

import org.miniauth.crypto.PlainTextSignatureAlgorithm;
import org.miniauth.crypto.SignatureAlgorithm;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.oauth.credential.AccessCredential;


public class PlainTextOAuthSignatureAlgorithm extends AbstractOAuthSignatureAlgorithm implements OAuthSignatureAlgorithm
{

    public PlainTextOAuthSignatureAlgorithm()
    {
        SignatureAlgorithm algorithm = new PlainTextSignatureAlgorithm();
        setSignatureAlgorithm(algorithm);
    }

    @Override
    public String generate(String text, AccessCredential credential) throws AuthSignatureException, InvalidCredentialException
    {
        String key = buildKeyString(credential);
        return getSignatureAlgorithm().generate(text, key);
    }
    
    
}
