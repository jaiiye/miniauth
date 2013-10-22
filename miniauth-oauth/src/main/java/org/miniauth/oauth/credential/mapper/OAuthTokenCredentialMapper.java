package org.miniauth.oauth.credential.mapper;

import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.credential.ConsumerCredential;


/**
 * Defines an OAuth-specific "credential mapper" for a specific consumer key/secret pair.
 */
public interface OAuthTokenCredentialMapper extends OAuthCredentialMapper
{
    /**
     * Returns a (unique) consumerKey associated with this mapper.
     * @return Consumer key.
     */
    String getConsumerKey();

    /**
     * Returns a (unique) consumerSecret associated with this mapper.
     * @return Consumer secret.
     */
    String getConsumerSecret();
    
    /**
     * Returns a (unique) consumer key/secret pair associated with this mapper.
     * @return ConsumerCredential comprising consumer key/secret pair.
     */
    ConsumerCredential getConsumerCredential();
    
    /**
     * Returns a pair of consumer key and access token.
     * @param accessToken
     * @return AccessIdentity comprising a pair of consumer key and access token.
     */
    AccessIdentity getAccessIdentity(String accessToken);
    
    /**
     * Returns a pair of consumer secret and token secret for the given access token.
     * @param accessToken
     * @return AccessCredential comprising a pair of consumer secret and token secret.
     */
    AccessCredential getAccesssCredential(String accessToken);
}
