package org.miniauth.oauth.service;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.exception.InvalidStateException;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.signature.OAuthSignatureGenerator;
import org.miniauth.service.RequestEndorser;
import org.miniauth.signature.SignatureGenerator;


/**
 * OAuth Request signer implementation.
 * (Using a generic/broader term "endorse" instead of "sign" for future generalization.)
 */
public class OAuthRequestEndorser implements RequestEndorser
{
    private static final Logger log = Logger.getLogger(OAuthRequestEndorser.class.getName());

    // TBD: 
    private SignatureGenerator signatureGenerator = null;
    // private OAuthSignatureGenerator signatureGenerator = null;
    
    // Singleton.
    private OAuthRequestEndorser()
    {
        signatureGenerator = new OAuthSignatureGenerator();
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
     * Returns true if the oauthRequest has been successfully "endorsed".
     * (Note: it never returns false. If error occurs, it throws exceptions.)
     * @param credential Access credential needed to endorse/sign the request.
     * @param request Outgoing request wrapper object. "In-out" param. It should be in a "ready" state.
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
        Map<String,String> authCredential = null;
        if(credential != null) {
            authCredential = credential.toReadOnlyMap();
        }
        
        // validate request ??


//        OAuthParamMap newOAuthParamMap = signatureGenerator.generateOAuthParamMap(authCredential, oauthRequest.getHttpMethod(), oauthRequest.getBaseURI(), oauthRequest.getAuthHeader(), oauthRequest.getFormParams(), oauthRequest.getQueryParams());
//        oauthRequest.endorse(newOAuthParamMap);

        String signature = signatureGenerator.generate(authCredential, oauthRequest.getHttpMethod(), oauthRequest.getBaseURI(), oauthRequest.getAuthHeader(), oauthRequest.getFormParams(), oauthRequest.getQueryParams());
        oauthRequest.endorse(signature);
        
        if(log.isLoggable(Level.FINE)) log.fine("oauthRequest = " + oauthRequest);
        return true;
    }

}
