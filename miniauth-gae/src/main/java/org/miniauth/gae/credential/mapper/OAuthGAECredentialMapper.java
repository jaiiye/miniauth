package org.miniauth.gae.credential.mapper;

import java.util.HashMap;
import java.util.Map;

import org.miniauth.exception.CredentialStoreException;
import org.miniauth.oauth.credential.mapper.AbstractOAuthCredentialMapper;


/**
 * Service that returns a "secret" given a credential key.
 * TBD... 
 */
public class OAuthGAECredentialMapper extends AbstractOAuthCredentialMapper implements DynamicGAECredentialMapper
{
//    // TBD:
//    // this is just a temporary implementation.
//    // the credential should come from persistent storage such as config/DB, etc.
//    private final Map<String,Map<String,String>> superRegistry = new HashMap<>();

    public OAuthGAECredentialMapper()
    {
        super();
    }

    @Override
    protected void init()
    {
        // TBD:
        // populate superRegistry here.
        // ...
    }


    @Override
    public String getCredentialSecret(String credentialType, String credentialKey) throws CredentialStoreException
    {
//        if(! superRegistry.containsKey(credentialType)) {
//            return null;
//        }
//        Map<String,String> credentialRegistry = superRegistry.get(credentialType);
//        return credentialRegistry.get(credentialKey);
        throw new CredentialStoreException("Not implemented.");
    }

    @Override
    public String putCredentialSecret(String credentialType, String credentialKey, String credentialSecret) throws CredentialStoreException
    {
//        Map<String,String> credentialRegistry = null;
//        if(! superRegistry.containsKey(credentialType)) {
//            credentialRegistry = new HashMap<>();
//            superRegistry.put(credentialType, credentialRegistry);
//        } else {
//            credentialRegistry = superRegistry.get(credentialType);
//        }
//        return credentialRegistry.put(credentialKey, credentialSecret);
        throw new CredentialStoreException("Not implemented.");
    }



}
