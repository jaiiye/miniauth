package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.IncomingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.oauth.common.OAuthIncomingRequest;
import org.miniauth.oauth.credential.mapper.AbstractOAuthCredentialMapper;
import org.miniauth.oauth.credential.mapper.OAuthCredentialMapper;
import org.miniauth.service.VerifierService;


public class OAuthVerifierService extends OAuthCredentialService implements VerifierService
{
    private static final Logger log = Logger.getLogger(OAuthVerifierService.class.getName());

    // private OAuthRequestVerifier requestVerifier = null;

    public OAuthVerifierService(OAuthCredentialMapper credentialMapper)
    {
        super(credentialMapper);
        // requestVerifier = OAuthRequestVerifier.getInstance();
    }

    @Override
    public boolean verify(IncomingRequest request) throws MiniAuthException
    {
        AccessIdentity accessIdentity = ((OAuthIncomingRequest) request).getAccessIdentity();
        AccessCredential credential = getOAuthCredentialMapper().getAccesssCredential(accessIdentity);
        if(credential == null || credential.getTokenSecret() == null) {
            throw new InvalidCredentialException("AccessCredential not found.");
        }
        return OAuthRequestVerifier.getInstance().verify(credential, request);
    }

    
    
}
