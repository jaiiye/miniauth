package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.oauth.callback.OAuthCredentialMapper;
import org.miniauth.service.EndorserService;


public class OAuthEndorserService extends OAuthCredentialService implements EndorserService
{
    private static final Logger log = Logger.getLogger(OAuthEndorserService.class.getName());

    public OAuthEndorserService(OAuthCredentialMapper credentialMapper)
    {
        super(credentialMapper);
    }

    @Override
    public boolean endorse(OutgoingRequest request) throws MiniAuthException
    {
        // TBD
        return false;
    }

    
    
}
