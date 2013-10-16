package org.miniauth.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;


// Server-side auth handler.
public interface ProviderAuthHandler extends AuthHandler
{
    // boolean verifyRequest(CredentialPair credentialPair, ServletRequest request) throws MiniAuthException;
    boolean verifyRequest(Map<String, String> authCredential, HttpServletRequest request) throws MiniAuthException;
}
