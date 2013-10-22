package org.miniauth.gae.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.IncomingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.gae.credential.mapper.GAECredentialMapper;
import org.miniauth.oauth.common.OAuthIncomingRequest;
import org.miniauth.oauth.service.OAuthRequestVerifier;
import org.miniauth.oauth.service.OAuthVerifierService;
import org.miniauth.service.VerifierService;


public class OAuthGAEVerifierService extends OAuthVerifierService implements VerifierService
{
    private static final Logger log = Logger.getLogger(OAuthGAEVerifierService.class.getName());

    // private OAuthRequestVerifier requestVerifier = null;

    public OAuthGAEVerifierService(GAECredentialMapper credentialMapper)
    {
        super(credentialMapper);
        // requestVerifier = OAuthRequestVerifier.getInstance();
    }

    public GAECredentialMapper getGAECredentialMapper()
    {
        return (GAECredentialMapper) getOAuthCredentialMapper();
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
