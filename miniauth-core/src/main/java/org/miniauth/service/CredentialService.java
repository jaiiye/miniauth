package org.miniauth.service;

import org.miniauth.callback.CredentialMapper;


/**
 * Base interface for "services with credential mapppers".
 */
public interface CredentialService
{
    CredentialMapper getCredentialMapper();
}
