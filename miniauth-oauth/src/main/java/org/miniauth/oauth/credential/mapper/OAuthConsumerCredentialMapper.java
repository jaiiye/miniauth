package org.miniauth.oauth.credential.mapper;

import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.CredentialStoreException;



/**
 * Defines a "credential mapper" for two-legged OAuth (2LO).
 */
public interface OAuthConsumerCredentialMapper extends OAuthCredentialMapper
{
    // ConsumerCredential getConsumerCredential(String consumerKey);
    AccessCredential getAccesssCredential(String consumerKey) throws CredentialStoreException;
}
