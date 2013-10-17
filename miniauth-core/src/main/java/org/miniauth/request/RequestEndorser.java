package org.miniauth.request;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.credential.AccessCredential;


/**
 * Request "signer" in the case of OAuth.
 * (Using a generic/broader term "endorse" instead of "sign" for future generalization.)
 */
public interface RequestEndorser
{
    /**
     * Returns true if the request has been successfully "endorsed".
     * @param credential Access credential needed to endorse/sign the request.
     * @param request Partial outgoing request wrapper object. "In-out" param.
     * @return true if the operation was successful.
     * @throws MiniAuthException
     */
    boolean endorse(AccessCredential credential, OutgoingRequest request) throws MiniAuthException;
}
