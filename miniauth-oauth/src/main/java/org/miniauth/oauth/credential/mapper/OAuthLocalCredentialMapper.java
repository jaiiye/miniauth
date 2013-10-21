package org.miniauth.oauth.credential.mapper;

import java.util.HashMap;
import java.util.Map;

import org.miniauth.credential.mapper.CredentialMapper;


/**
 * Service that returns a "secret" given a credential key.
 * The credential key-secret pairs are stored in a local "registry" (HashMap).
 * (This is just an exemplary implementation. Not for production use, except for a very simple use case.) 
 */
public class OAuthLocalCredentialMapper extends OAuthCredentialMapper implements CredentialMapper
{
    // TBD:
    // this is just a temporary implementation.
    // the credential should come from persistent storage such as config/DB, etc.
    private final Map<String,Map<String,String>> superRegistry = new HashMap<>();

    private OAuthLocalCredentialMapper()
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

    // Initialization-on-demand holder.
    private static final class OAuthLocalCredentialMapperHolder
    {
        private static final OAuthLocalCredentialMapper INSTANCE = new OAuthLocalCredentialMapper();
    }
    // Singleton method
    public static OAuthLocalCredentialMapper getInstance()
    {
        return OAuthLocalCredentialMapperHolder.INSTANCE;
    }


    @Override
    public String getCredentialSecret(String credentialType, String credentialKey)
    {
        if(! superRegistry.containsKey(credentialType)) {
            return null;
        }
        Map<String,String> credentialRegistry = superRegistry.get(credentialType);
        return credentialRegistry.get(credentialKey);
    }
    @Override
    public String putCredentialSecret(String credentialType, String credentialKey, String credentialSecret)
    {
        Map<String,String> credentialRegistry = null;
        if(! superRegistry.containsKey(credentialType)) {
            credentialRegistry = new HashMap<>();
            superRegistry.put(credentialType, credentialRegistry);
        } else {
            credentialRegistry = superRegistry.get(credentialType);
        }
        return credentialRegistry.put(credentialKey, credentialSecret);
    }


}
