package org.miniauth.credential.mapper;

import org.miniauth.exception.CredentialStoreException;


/**
 * "Writable" version of CredentialMapper.
 */
public interface DynamicCredentialMapper extends CredentialMapper
{
    String putCredentialSecret(String credentialType, String credentialKey, String credentialSecret) throws CredentialStoreException;
}
