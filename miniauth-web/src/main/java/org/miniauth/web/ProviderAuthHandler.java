package org.miniauth.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;


/**
 * Server-side auth handler.
 */
public interface ProviderAuthHandler extends AuthHandler
{
    /**
     * Verifies the request for auth.
     * @param request the request object.
     * @return true if the request has valid authorization token.
     * @throws MiniAuthException
     * @throws IOException
     */
    boolean verifyRequest(HttpServletRequest request) throws MiniAuthException, IOException;
}
