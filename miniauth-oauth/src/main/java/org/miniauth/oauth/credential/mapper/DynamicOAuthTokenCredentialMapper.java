package org.miniauth.oauth.credential.mapper;

import org.miniauth.credential.AccessCredential;


/**
 * "Writable" version of OAuthTokenCredentialMapper.
 */
public interface DynamicOAuthTokenCredentialMapper extends OAuthTokenCredentialMapper, DynamicOAuthCredentialMapper
{
    // String putTokenSecret(String accessToken, String tokenSecret);
    AccessCredential putAccesssCredential(String accessToken, String tokenSecret);
}
