package org.miniauth.oauth.crypto;

import org.miniauth.crypto.RsaSHA1SignatureAlgorithm;
import org.miniauth.crypto.SignatureAlgorithm;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.oauth.credential.AccessCredential;


public class RsaSHA1OAuthSignatureAlgorithm extends AbstractOAuthSignatureAlgorithm implements OAuthSignatureAlgorithm
{

    public RsaSHA1OAuthSignatureAlgorithm()
    {
        SignatureAlgorithm algorithm = new RsaSHA1SignatureAlgorithm();
        setSignatureAlgorithm(algorithm);
    }

    @Override
    protected String buildKeyString(AccessCredential credential) throws InvalidCredentialException
    {
        String consumerSecret = credential.getConsumerSecret();
        // String tokenSecret = credential.getTokenSecret();
        
        return consumerSecret;
    }

    @Override
    public String generate(String text, AccessCredential credential) throws AuthSignatureException, InvalidCredentialException
    {
        String key = buildKeyString(credential);
        return getSignatureAlgorithm().generate(text, key);
    }

    @Override
    public boolean verify(String text, AccessCredential credential, String signature) throws AuthSignatureException, InvalidCredentialException
    {
        String key = buildKeyString(credential);
        return getSignatureAlgorithm().verify(text, key, signature);
    }
    
    
}
