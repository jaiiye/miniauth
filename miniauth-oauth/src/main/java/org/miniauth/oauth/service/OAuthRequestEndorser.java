package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.exception.InvalidStateException;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.service.RequestEndorser;


/**
 * OAuth Request signer implementation.
 * (Using a generic/broader term "endorse" instead of "sign" for future generalization.)
 */
public class OAuthRequestEndorser implements RequestEndorser
{
    private static final Logger log = Logger.getLogger(OAuthRequestEndorser.class.getName());

    // Singleton.
    private OAuthRequestEndorser()
    {
    }
    // Initialization-on-demand holder.
    private static final class OAuthRequestEndorserHolder
    {
        private static final OAuthRequestEndorser INSTANCE = new OAuthRequestEndorser();
    }
    // Singleton method
    public static OAuthRequestEndorser getInstance()
    {
        return OAuthRequestEndorserHolder.INSTANCE;
    }

    
    /**
     * Returns true if the request has been successfully "endorsed".
     * @param credential Access credential needed to endorse/sign the request.
     * @param request Partial outgoing request wrapper object. "In-out" param.
     * @return true if the operation was successful.
     * @throws MiniAuthException
     */
    @Override
    public boolean endorse(AccessCredential credential, OutgoingRequest request) throws MiniAuthException
    {
        if(request == null) {
            throw new InvalidInputException("OutgoingRequest is null.");
        }
        if(request.isEndorsed()) {
            throw new InvalidStateException("OutgoingRequest is already endorsed.");
        }
        if(! request.isReady()) {
            throw new InvalidStateException("OutgoingRequest is not ready for endorsement.");
        }

        OAuthOutgoingRequest oauthRequest = (OAuthOutgoingRequest) request;

        
        


        
        return false;
    }

}
