package org.miniauth.request;

import org.miniauth.MiniAuthException;
import org.miniauth.common.IncomingRequest;
import org.miniauth.credential.AccessCredential;


/**
 * Request "verifier".
 * It verifies the signature in the case of OAuth.
 */
public interface RequestVerifier
{
    /**
     * Returns true if the request is valid and verified.
     * @param credential Access credential needed to validate/verify the request.
     * @param request Partial incoming request wrapper object 
     * @return true if the request was successfully verified.
     * @throws MiniAuthException
     */
    boolean verify(AccessCredential credential, IncomingRequest request) throws MiniAuthException;
}
