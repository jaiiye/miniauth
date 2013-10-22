package org.miniauth.credential.mapper;


/**
 * A "credential mapper" provides a method to retrieve a "credential secret" given a "credential key".
 * For example, if a username is given, the user's (hashed) password may be returned.
 */
public interface CredentialMapper
{
    // ?? which is better??
    // [1] 
    /**
     * Returns a "secret" corresponding to the given credentialKey for a given credential type.
     * @param credentialType  Consumer/client credential? Or, user access credential? etc.
     * @param credentialKey Consumer key, access token, username, etc.
     * @return the corresponding secret.
     */
    String getCredentialSecret(String credentialType, String credentialKey);

    // [2]
//    String getConsumerSecret(String consumerKey);
//    String getTokenSecret(String accessToken);
    
    // [3]
//    // AccessCredential getAccesssCredential(AccessIdentity accessIdentity);
    
    // [4]
//    // Map<String,String> getAccesssCredential(Map<String,String> accessIdentity);
}
