package org.miniauth.service;

import org.miniauth.MiniAuthException;
import org.miniauth.common.IncomingRequest;


/**
 * Request "verifier".
 * It verifies the signature in the case of OAuth.
 */
public interface VerifierService extends CredentialService
{
    /**
     * Returns true if the request is valid and verified.
     * @param request Partial incoming request wrapper object. It should be considered read-only. 
     * @return true if the request was successfully verified.
     * @throws MiniAuthException
     */
    boolean verify(IncomingRequest request) throws MiniAuthException;
}
