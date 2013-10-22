package org.miniauth.oauth.credential.mapper;

import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.credential.ConsumerCredential;
import org.miniauth.credential.TokenCredential;
import org.miniauth.credential.mapper.CredentialMapper;
import org.miniauth.exception.CredentialStoreException;


/**
 * Defines an OAuth-specific "credential mapper".
 */
public interface OAuthCredentialMapper extends CredentialMapper
{
    /**
     * Returns a consumer secret for the given consumerKey.
     * @param consumerKey
     * @return Consumer secret.
     * @throws CredentialStoreException TODO
     */
    String getConsumerSecret(String consumerKey) throws CredentialStoreException;
    /**
     * Returns a token secret for the givne accessToken.
     * @param accessToken
     * @return Token secret.
     * @throws CredentialStoreException TODO
     */
    String getTokenSecret(String accessToken) throws CredentialStoreException;
    
    // ConsumerCredential getConsumerCredential(String consumerKey);
    // TokenCredential getTokenCredential(String accessToken);
    
    /**
     * Returns a pair of consumer secret and token secret for the given consumer key/access token.
     * @param accessIdentity
     * @return AccessCredential comprising a pair of consumer secret and token secret.
     * @throws CredentialStoreException TODO
     */
    AccessCredential getAccesssCredential(AccessIdentity accessIdentity) throws CredentialStoreException;

    // AccessCredential getAccesssCredential(String consumerKey, String accessToken);

}
