package org.miniauth.web;

import javax.servlet.ServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessIdentity;


// Client-side auth handler.
public interface ClientAuthHandler extends AuthHandler
{
    // request is an "in-out" param.
    boolean prepareRequest(AccessIdentity accessIdentity, ServletRequest request) throws MiniAuthException;
}
