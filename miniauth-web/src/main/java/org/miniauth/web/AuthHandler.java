package org.miniauth.web;

import org.miniauth.credential.mapper.CredentialMapper;


/**
 * Top-level Interface for the "auth handler" classes.
 */
public interface AuthHandler
{
    CredentialMapper getCredentialMapper();
}
