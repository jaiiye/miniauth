package org.miniauth.oauth.credential.mapper;

import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.credential.ConsumerCredential;
import org.miniauth.credential.TokenCredential;
import org.miniauth.credential.mapper.DynamicCredentialMapper;
import org.miniauth.exception.CredentialStoreException;


/**
 * "Writable" version of OAuthCredentialMapper.
 */
public interface DynamicOAuthCredentialMapper extends OAuthCredentialMapper, DynamicCredentialMapper
{
    String putConsumerSecret(String consumerKey, String consumerSecret) throws CredentialStoreException;
    String putTokenSecret(String accessToken, String tokenSecret) throws CredentialStoreException;
    // ConsumerCredential putConsumerCredential(ConsumerCredential consumerCredential);
    // TokenCredential putTokenCredential(TokenCredential tokenCredential);
    AccessCredential putAccesssCredential(AccessIdentity accessIdentity, AccessCredential accessCredential) throws CredentialStoreException;
    
    // TBD:
    // remove, clear, etc. ???
    // ...
}
