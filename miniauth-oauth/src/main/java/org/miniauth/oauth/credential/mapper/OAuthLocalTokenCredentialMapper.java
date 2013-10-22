package org.miniauth.oauth.credential.mapper;

import java.util.HashMap;
import java.util.Map;

import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.credential.ConsumerCredential;
import org.miniauth.oauth.credential.OAuthAccessCredential;
import org.miniauth.oauth.credential.OAuthAccessIdentity;
import org.miniauth.oauth.credential.OAuthConsumerCredential;


/**
 * Service that returns a "token secret" given an access token.
 * The token credential pairs are stored in a local "registry" (HashMap).
 * (This is just an exemplary implementation. Not for production use, except for a very simple use case.) 
 */
public final class OAuthLocalTokenCredentialMapper extends AbstractOAuthCredentialMapper implements DynamicOAuthTokenCredentialMapper
{
    // TBD:
    // this is just a temporary implementation.
    // the credential should come from persistent storage such as config/DB, etc.
    private final Map<String,String> tokenRegistry = new HashMap<>();
    
    // Consumer credential is hard-coded.
    private String consumerKey = null;
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

    // TBD:
    // This is a weird singleton.
    // Note that we are assuming we are using a single consumerKey/Secret within a single app.
    // ....
    // Make it a "multiton" (with a consumerKey as a key) ???
    // ....
    
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

    @Override
    public String getConsumerKey()
    {
        return this.consumerKey;
    }
    public OAuthLocalTokenCredentialMapper setConsumerKey(String consumerKey)
    {
        this.consumerKey = consumerKey;
        return this;
    }

    @Override
    public String getConsumerSecret()
    {
        return consumerSecret;
    }
    // Note: we can do the following:
    // OAuthLocalTokenCredentialMapper credentialMapper = OAuthLocalTokenCredentialMapper.getInstance().setConsumerSecret(consumerSecret);
    public OAuthLocalTokenCredentialMapper setConsumerSecret(String consumerSecret)
    {
        this.consumerSecret = consumerSecret;
        return this;
    }

    @Override
    public ConsumerCredential getConsumerCredential()
    {
        return new OAuthConsumerCredential(getConsumerKey(), getConsumerSecret());
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

    @Override
    public AccessIdentity getAccessIdentity(String accessToken)
    {
        return new OAuthAccessIdentity(getConsumerKey(), accessToken);
    }

    @Override
    public AccessCredential getAccesssCredential(String accessToken)
    {
        return new OAuthAccessCredential(getConsumerSecret(), getTokenSecret(accessToken));
    }

    @Override
    public AccessCredential putAccesssCredential(String accessToken, String tokenSecret)
    {
        String oldSecret = tokenRegistry.put(accessToken, tokenSecret);
        return new OAuthAccessCredential(getConsumerSecret(), oldSecret);
    }

}
