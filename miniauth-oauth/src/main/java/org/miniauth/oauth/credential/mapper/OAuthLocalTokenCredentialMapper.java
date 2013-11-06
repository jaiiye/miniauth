package org.miniauth.oauth.credential.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.credential.ConsumerCredential;
import org.miniauth.exception.CredentialStoreException;
import org.miniauth.oauth.credential.OAuthAccessCredential;
import org.miniauth.oauth.credential.OAuthAccessIdentity;
import org.miniauth.oauth.credential.OAuthConsumerCredential;


/**
 * Service that returns a "token secret" given an access token.
 * The token credential pairs are stored in a local "registry" (HashMap).
 * (This is just an exemplary implementation. Not for production use, except for a very simple use case.) 
 */
public class OAuthLocalTokenCredentialMapper extends AbstractOAuthCredentialMapper implements DynamicOAuthTokenCredentialMapper
{
    private static final Logger log = Logger.getLogger(OAuthLocalTokenCredentialMapper.class.getName());

    // TBD:
    // this is just a temporary implementation.
    // the credential should come from persistent storage such as config/DB, etc.
    private final Map<String,String> tokenRegistry = new HashMap<>();
    
    // Consumer credential is hard-coded.
    private String consumerKey = null;
    private String consumerSecret = null;

    public OAuthLocalTokenCredentialMapper()
    {
        this(null, null);
    }
    public OAuthLocalTokenCredentialMapper(String consumerKey, String consumerSecret)
    {
        super();
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }


    @Override
    protected void init()
    {
        // TBD:
        // set consumerSecret, and
        // populate tokenRegistry here.
        // ...
    }


    // 11/05/13
    // This does not really make sense.
    // Singleton can be useful only in apps that require a single consumer key/secret pair...
    // --> Made the constructor public, for now...
    // but, in general, being able to "share", or reuse, tokenRegistry is, can be, important....
    // ....
    
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
    @Deprecated
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
    public String getCredentialSecret(String credentialType, String credentialKey) throws CredentialStoreException
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
    public String putCredentialSecret(String credentialType, String credentialKey, String credentialSecret) throws CredentialStoreException
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
    public String getTokenSecret(String accessToken) throws CredentialStoreException
    {
        return tokenRegistry.get(accessToken);
    }

    @Override
    public String putTokenSecret(String accessToken, String tokenSecret) throws CredentialStoreException
    {
        return tokenRegistry.put(accessToken, tokenSecret);
    }

    @Override
    public void putTokenSecrets(Map<String, String> tokenCredentials)
            throws CredentialStoreException
    {
        // TBD: Validation ??
        tokenRegistry.putAll(tokenCredentials);
    }

    @Override
    public AccessIdentity getAccessIdentity(String accessToken) throws CredentialStoreException
    {
        return new OAuthAccessIdentity(getConsumerKey(), accessToken);
    }

    @Override
    public AccessCredential getAccesssCredential(String accessToken) throws CredentialStoreException
    {
        return new OAuthAccessCredential(getConsumerSecret(), getTokenSecret(accessToken));
    }

    @Override
    public AccessCredential putAccesssCredential(String accessToken, String tokenSecret) throws CredentialStoreException
    {
        String oldSecret = tokenRegistry.put(accessToken, tokenSecret);
        return new OAuthAccessCredential(getConsumerSecret(), oldSecret);
    }


}
