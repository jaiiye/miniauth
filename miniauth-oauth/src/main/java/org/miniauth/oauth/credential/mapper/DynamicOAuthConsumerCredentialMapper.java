package org.miniauth.oauth.credential.mapper;

import org.miniauth.credential.AccessCredential;


/**
 * "Writable" version of OAuthConsumerCredentialMapper.
 */
public interface DynamicOAuthConsumerCredentialMapper extends OAuthConsumerCredentialMapper, DynamicOAuthCredentialMapper
{
    // String putConsumerSecret(String consumerKey, String consumerSecret);
    // ConsumerCredential putConsumerCredential(String consumerKey, String consumerSecret);
    AccessCredential putAccesssCredential(String consumerKey, String consumerSecret);
}
