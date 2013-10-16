package org.miniauth.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;


// Client-side auth handler.
public interface ClientAuthHandler extends AuthHandler
{
    // request is an "in-out" param.
    // boolean prepareRequest(AccessIdentity accessIdentity, ServletRequest request) throws MiniAuthException;
    boolean prepareRequest(Map<String, String> authCredential, HttpServletRequest request) throws MiniAuthException, IOException;
}
