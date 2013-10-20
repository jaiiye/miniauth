package org.miniauth.service;

import org.miniauth.credential.mapper.CredentialMapper;


/**
 * Base interface for "services with credential mappers".
 */
public interface CredentialService
{
    CredentialMapper getCredentialMapper();
}
