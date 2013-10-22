package org.miniauth.credential.mapper;


/**
 * "Writable" version of CredentialMapper.
 */
public interface DynamicCredentialMapper extends CredentialMapper
{
    String putCredentialSecret(String credentialType, String credentialKey, String credentialSecret);
}
