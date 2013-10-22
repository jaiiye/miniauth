package org.miniauth.oauth.credential.mapper;

import java.util.Map;

import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.CredentialStoreException;


/**
 * "Writable" version of OAuthConsumerCredentialMapper.
 */
public interface DynamicOAuthConsumerCredentialMapper extends OAuthConsumerCredentialMapper, DynamicOAuthCredentialMapper
{
    // String putConsumerSecret(String consumerKey, String consumerSecret);
    // ConsumerCredential putConsumerCredential(String consumerKey, String consumerSecret);
    void putConsumerSecrets(Map<String,String> consumerCredentials) throws CredentialStoreException;

    AccessCredential putAccesssCredential(String consumerKey, String consumerSecret) throws CredentialStoreException;

}
