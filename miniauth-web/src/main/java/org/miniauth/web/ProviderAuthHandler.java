package org.miniauth.web;

import javax.servlet.ServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.CredentialPair;


// Server-side auth handler.
public interface ProviderAuthHandler extends AuthHandler
{
    boolean verifyRequest(CredentialPair credentialPair, ServletRequest request) throws MiniAuthException;
}
