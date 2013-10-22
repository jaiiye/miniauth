package org.miniauth.gae.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.gae.credential.mapper.GAECredentialMapper;
import org.miniauth.gae.credential.mapper.GAETokenCredentialMapper;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.oauth.service.OAuthEndorserService;
import org.miniauth.oauth.service.OAuthRequestEndorser;
import org.miniauth.service.EndorserService;


public class OAuthGAEEndorserService extends OAuthEndorserService implements EndorserService
{
    private static final Logger log = Logger.getLogger(OAuthGAEEndorserService.class.getName());

    // private OAuthRequestEndorser requestEndorser = null;
    
    public OAuthGAEEndorserService(GAETokenCredentialMapper credentialMapper)
    {
        super(credentialMapper);
        // requestEndorser = OAuthRequestEndorser.getInstance();
    }

    public GAECredentialMapper getGAECredentialMapper()
    {
        return (GAECredentialMapper) getOAuthCredentialMapper();
    }

    public GAETokenCredentialMapper getGAETokenCredentialMapper()
    {
        return (GAETokenCredentialMapper) getGAECredentialMapper();
    }

    @Override
    public boolean endorse(OutgoingRequest request) throws MiniAuthException
    {
        AccessIdentity accessIdentity = ((OAuthOutgoingRequest) request).getAccessIdentity();
        // log.warning(">>>>>>>>>>>>>>>>>>>>>>>>>>> accessIdentity = " + accessIdentity);
        AccessCredential accessCredential = getOAuthCredentialMapper().getAccesssCredential(accessIdentity);
        // log.warning(">>>>>>>>>>>>>>>>>>>>>>>>>>> accessCredential = " + accessCredential);
        if(accessCredential == null || accessCredential.getTokenSecret() == null) {
            throw new InvalidCredentialException("AccessCredential not found.");
        }
        return OAuthRequestEndorser.getInstance().endorse(accessCredential, request);
    }
    
    
}
