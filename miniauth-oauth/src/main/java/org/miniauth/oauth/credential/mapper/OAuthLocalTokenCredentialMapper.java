package org.miniauth.oauth.credential.mapper;

import java.util.HashMap;
import java.util.Map;

import org.miniauth.credential.mapper.CredentialMapper;


/**
 * Service that returns a "token secret" given an access token.
 * The token credential pairs are stored in a local "registry" (HashMap).
 * (This is just an exemplary implementation. Not for production use, except for a very simple use case.) 
 */
public class OAuthLocalTokenCredentialMapper extends OAuthCredentialMapper implements CredentialMapper
{
    // TBD:
    // this is just a temporary implementation.
    // the credential should come from persistent storage such as config/DB, etc.
    private final Map<String,String> tokenRegistry = new HashMap<>();
    
    // Consumer credential is hard-coded.
    // private String consumerKey = null;
    private String consumerSecret = null;

    private OAuthLocalTokenCredentialMapper()
    {
        super();
    }

    @Override
    protected void init()
    {
        // TBD:
        // set consumerSecret, and
        // populate tokenRegistry here.
        // ...
    }

    // Initialization-on-demand holder.
    private static final class OAuthLocalCredentialMapperHolder
    {
        private static final OAuthLocalTokenCredentialMapper INSTANCE = new OAuthLocalTokenCredentialMapper();
    }
    // Singleton method
    public static OAuthLocalTokenCredentialMapper getInstance()
    {
        return OAuthLocalCredentialMapperHolder.INSTANCE;
    }

    public String getConsumerSecret()
    {
        return consumerSecret;
    }
    public void setConsumerSecret(String consumerSecret)
    {
        this.consumerSecret = consumerSecret;
    }

    @Override
    public String getCredentialSecret(String credentialType, String credentialKey)
    {
        if(CONSUMER_CREDENTIAL.equals(credentialType)) {
            return consumerSecret;
        } else if(TOKEN_CREDENTIAL.equals(credentialType)) {
            return tokenRegistry.get(credentialKey);
        } else {
            return null;
        }
    }
    @Override
    public String putCredentialSecret(String credentialType, String credentialKey, String credentialSecret)
    {
        if(CONSUMER_CREDENTIAL.equals(credentialType)) {
            String oldConsumerSecret = this.consumerSecret;
            this.consumerSecret = credentialSecret;
            return oldConsumerSecret;
        } else if(TOKEN_CREDENTIAL.equals(credentialType)) {
            return tokenRegistry.put(credentialKey, credentialSecret);
        } else {
            return null;
        }
    }

    @Override
    public String getTokenSecret(String accessToken)
    {
        return tokenRegistry.get(accessToken);
    }

    @Override
    public String putTokenSecret(String accessToken, String tokenSecret)
    {
        return tokenRegistry.put(accessToken, tokenSecret);
    }


}
