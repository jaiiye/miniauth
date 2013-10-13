package org.miniauth.oauth.crypto;

import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.InvalidCredentialException;


public interface OAuthSignatureAlgorithm
{
    String generate(String text, AccessCredential credential) throws AuthSignatureException, InvalidCredentialException;
    boolean verify(String text, AccessCredential credential, String signature) throws AuthSignatureException, InvalidCredentialException;
    
}
