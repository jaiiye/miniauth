package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.oauth.callback.OAuthCredentialMapper;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.service.EndorserService;


public class OAuthEndorserService extends OAuthCredentialService implements EndorserService
{
    private static final Logger log = Logger.getLogger(OAuthEndorserService.class.getName());

    // private OAuthRequestEndorser requestEndorser = null;
    
    public OAuthEndorserService(OAuthCredentialMapper credentialMapper)
    {
        super(credentialMapper);
        // requestEndorser = OAuthRequestEndorser.getInstance();
    }

    public OAuthCredentialMapper getOAuthCredentialMapper()
    {
        return (OAuthCredentialMapper) getCredentialMapper();
    }

    @Override
    public boolean endorse(OutgoingRequest request) throws MiniAuthException
    {
        AccessIdentity accessIdentity = ((OAuthOutgoingRequest) request).getAccessIdentity();
        AccessCredential credential = getOAuthCredentialMapper().getAccesssCredential(accessIdentity);
        return OAuthRequestEndorser.getInstance().endorse(credential, request);
    }
    
    
}
