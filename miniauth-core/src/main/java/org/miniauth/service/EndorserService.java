package org.miniauth.service;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;


/**
 * Request "signer" in the case of OAuth.
 * (Using a generic/broader term "endorse" instead of "sign" for future generalization.)
 */
public interface EndorserService extends CredentialService
{
    /**
     * Returns true if the request has been successfully "endorsed".
     * @param request Partial outgoing request wrapper object. "In-out" param.
     * @return true if the operation was successful.
     * @throws MiniAuthException
     */
    boolean endorse(OutgoingRequest request) throws MiniAuthException;
}
