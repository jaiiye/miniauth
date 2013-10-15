package org.miniauth.web;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.credential.CredentialPair;


// Server-side auth handler.
public interface ProviderAuthHandler extends AuthHandler
{
    HttpServletRequest verifyRequest(CredentialPair credentialPair, HttpServletRequest request);
}
