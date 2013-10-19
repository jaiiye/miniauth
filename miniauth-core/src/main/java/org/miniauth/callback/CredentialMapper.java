package org.miniauth.callback;

import java.util.Map;

import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;


public interface CredentialMapper
{
    // ?? which is better??
    // [1] 
    // name: consumer, user token, etc.
    // credentialKey -> credentialSecret.
    String getCredentialSecret(String credentialType, String credentialKey);
    // [2]
//    String getConsumerSecret(String consumerKey);
//    String getTokenSecret(String accessToken);
    // [3]
//    // AccessCredential getAccesssCredential(AccessIdentity accessIdentity);
    // [4]
//    // Map<String,String> getAccesssCredential(Map<String,String> accessIdentity);
}
