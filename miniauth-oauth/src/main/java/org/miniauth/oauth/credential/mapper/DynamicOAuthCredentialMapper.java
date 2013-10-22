package org.miniauth.oauth.credential.mapper;

import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.credential.ConsumerCredential;
import org.miniauth.credential.TokenCredential;
import org.miniauth.credential.mapper.DynamicCredentialMapper;


/**
 * "Writable" version of OAuthCredentialMapper.
 */
public interface DynamicOAuthCredentialMapper extends OAuthCredentialMapper, DynamicCredentialMapper
{
    String putConsumerSecret(String consumerKey, String consumerSecret);
    String putTokenSecret(String accessToken, String tokenSecret);
    // ConsumerCredential putConsumerCredential(ConsumerCredential consumerCredential);
    // TokenCredential putTokenCredential(TokenCredential tokenCredential);
    AccessCredential putAccesssCredential(AccessIdentity accessIdentity, AccessCredential accessCredential);
}
