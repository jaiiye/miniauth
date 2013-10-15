package org.miniauth.web;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.credential.AccessIdentity;


// Client-side auth handler.
public interface ClientAuthHandler extends AuthHandler
{
    HttpServletRequest prepareRequest(AccessIdentity accessIdentity, HttpServletRequest request);
}
