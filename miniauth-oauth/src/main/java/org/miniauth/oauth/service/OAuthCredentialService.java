package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.callback.CredentialMapper;
import org.miniauth.service.CredentialService;


public abstract class OAuthCredentialService implements CredentialService
{
    private static final Logger log = Logger.getLogger(OAuthCredentialService.class.getName());

    private CredentialMapper credentialMapper = null;
    

    public OAuthCredentialService(CredentialMapper credentialMapper)
    {
        this.credentialMapper = credentialMapper;
    }

    @Override
    public CredentialMapper getCredentialMapper()
    {
        return this.credentialMapper;
    }

    
    
    
}
