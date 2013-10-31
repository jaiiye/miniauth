package org.miniauth.oauth.service;

import java.io.Serializable;
import java.util.logging.Logger;

import org.miniauth.credential.mapper.CredentialMapper;
import org.miniauth.oauth.credential.mapper.OAuthCredentialMapper;
import org.miniauth.service.CredentialService;


/**
 * Base class for OAuthEndorserService and OAuthVerifierService.
 */
public abstract class OAuthCredentialService implements CredentialService, Serializable
{
    private static final Logger log = Logger.getLogger(OAuthCredentialService.class.getName());
    private static final long serialVersionUID = 1L;

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

    public OAuthCredentialMapper getOAuthCredentialMapper()
    {
        return (OAuthCredentialMapper) getCredentialMapper();
    }

    
}
