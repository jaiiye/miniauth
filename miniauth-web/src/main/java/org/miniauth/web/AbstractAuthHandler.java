package org.miniauth.web;

import java.io.Serializable;
import java.util.logging.Logger;

import org.miniauth.credential.mapper.CredentialMapper;


// Base class for AuthHandler implementations.
public abstract class AbstractAuthHandler implements AuthHandler, Serializable
{
    private static final Logger log = Logger.getLogger(AbstractAuthHandler.class.getName());
    private static final long serialVersionUID = 1L;

    private CredentialMapper credentialMapper = null;

    public AbstractAuthHandler(CredentialMapper credentialMapper)
    {
        this.credentialMapper = credentialMapper;
    }

    @Override
    public CredentialMapper getCredentialMapper()
    {
        return this.credentialMapper;
    }

    
}
