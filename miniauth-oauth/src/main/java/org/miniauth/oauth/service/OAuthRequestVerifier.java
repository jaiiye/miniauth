package org.miniauth.oauth.service;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.IncomingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.exception.InvalidStateException;
import org.miniauth.exception.UnauthorizedException;
import org.miniauth.oauth.common.OAuthIncomingRequest;
import org.miniauth.oauth.signature.OAuthSignatureVerifier;
import org.miniauth.service.RequestVerifier;
import org.miniauth.signature.SignatureVerifier;


/**
 * OAuth Request verifier.
 * verify() verifies the OAuth request signature.
 */
public class OAuthRequestVerifier implements RequestVerifier
{
    private static final Logger log = Logger.getLogger(OAuthRequestVerifier.class.getName());

    // TBD: 
    private SignatureVerifier signatureVerifier = null;
    // private OAuthSignatureVerifier signatureVerifier = null;

    // Singleton.
    private OAuthRequestVerifier()
    {
        signatureVerifier = new OAuthSignatureVerifier();
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
     * (Note: it does not return false. If error occurs, it throws exceptions.)
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
        Map<String,String> authCredential = null;
        if(credential != null) {
            authCredential = credential.toReadOnlyMap();
        }

        boolean verified = signatureVerifier.verify(authCredential, oauthRequest.getHttpMethod(), oauthRequest.getBaseURI(), oauthRequest.getAuthHeader(), oauthRequest.getFormParams(), oauthRequest.getQueryParams());
        if(log.isLoggable(Level.FINE)) log.fine("verified = " + verified);
        return verified;
    }

}
