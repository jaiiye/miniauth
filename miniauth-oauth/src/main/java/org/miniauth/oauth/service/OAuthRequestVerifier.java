package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.IncomingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.exception.InvalidStateException;
import org.miniauth.exception.UnauthorizedException;
import org.miniauth.oauth.common.OAuthIncomingRequest;
import org.miniauth.service.RequestVerifier;


/**
 * OAuth Request verifier.
 * verify() verifies the OAuth request signature.
 */
public class OAuthRequestVerifier implements RequestVerifier
{
    private static final Logger log = Logger.getLogger(OAuthRequestVerifier.class.getName());

    // Singleton.
    private OAuthRequestVerifier()
    {
    }
    // Initialization-on-demand holder.
    private static final class OAuthRequestVerifierHolder
    {
        private static final OAuthRequestVerifier INSTANCE = new OAuthRequestVerifier();
    }
    // Singleton method
    public static OAuthRequestVerifier getInstance()
    {
        return OAuthRequestVerifierHolder.INSTANCE;
    }

    
    
    /**
     * Returns true if the request is valid and verified.
     * @param credential Access credential needed to validate/verify the request.
     * @param request Partial incoming request wrapper object. It should be considered read-only. 
     * @return true if the request was successfully verified.
     * @throws MiniAuthException
     */
    @Override
    public boolean verify(AccessCredential credential, IncomingRequest request) throws MiniAuthException
    {
        if(request == null) {
            throw new InvalidInputException("IncomingRequest is null.");
        }
        if(! request.isReady()) {
            throw new InvalidStateException("IncomingRequest is not ready for verification.");
        }
        if(! request.isEndorsed()) {
            throw new UnauthorizedException("IncomingRequest is not endorsed.");
        }

        OAuthIncomingRequest oauthRequest = (OAuthIncomingRequest) request;

        
        
        
        
        return false;
    }

}
