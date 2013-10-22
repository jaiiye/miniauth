package org.miniauth.oauth.credential.mapper;

import java.util.Map;

import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.CredentialStoreException;


/**
 * "Writable" version of OAuthTokenCredentialMapper.
 */
public interface DynamicOAuthTokenCredentialMapper extends OAuthTokenCredentialMapper, DynamicOAuthCredentialMapper
{
    // String putTokenSecret(String accessToken, String tokenSecret);
    void putTokenSecrets(Map<String,String> tokenCredentials) throws CredentialStoreException;

    AccessCredential putAccesssCredential(String accessToken, String tokenSecret) throws CredentialStoreException;
}
