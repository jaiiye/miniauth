package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.IncomingRequest;
import org.miniauth.oauth.callback.OAuthCredentialMapper;
import org.miniauth.service.VerifierService;


public class OAuthVerifierService extends OAuthCredentialService implements VerifierService
{
    private static final Logger log = Logger.getLogger(OAuthVerifierService.class.getName());

    public OAuthVerifierService(OAuthCredentialMapper credentialMapper)
    {
        super(credentialMapper);
    }

    @Override
    public boolean verify(IncomingRequest request) throws MiniAuthException
    {
        // TBD
        return false;
    }

    
    
}
