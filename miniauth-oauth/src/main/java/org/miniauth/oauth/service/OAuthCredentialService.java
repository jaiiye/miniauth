package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.credential.mapper.CredentialMapper;
import org.miniauth.oauth.credential.mapper.OAuthCredentialMapper;
import org.miniauth.service.CredentialService;


public abstract class OAuthCredentialService implements CredentialService
{
    private static final Logger log = Logger.getLogger(OAuthCredentialService.class.getName());

    private OAuthCredentialMapper credentialMapper = null;
    

    public OAuthCredentialService(OAuthCredentialMapper credentialMapper)
    {
        this.credentialMapper = credentialMapper;
    }

    @Override
    public CredentialMapper getCredentialMapper()
    {
        return this.credentialMapper;
    }
    
    
}