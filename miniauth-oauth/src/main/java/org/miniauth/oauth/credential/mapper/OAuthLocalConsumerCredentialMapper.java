package org.miniauth.oauth.credential.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.CredentialStoreException;
import org.miniauth.oauth.credential.OAuthAccessCredential;


/**
 * Default implementation of OAuthConsumerCredentialMapper,
 *    which is a "credential mapper" for two-legged OAuth (2LO).
 */
public final class OAuthLocalConsumerCredentialMapper extends AbstractOAuthCredentialMapper implements DynamicOAuthConsumerCredentialMapper
{
    private static final Logger log = Logger.getLogger(OAuthLocalConsumerCredentialMapper.class.getName());

    // TBD:
    // this is just a temporary implementation.
    // the credential should come from persistent storage such as config/DB, etc.
    private final Map<String,String> consumerRegistry = new HashMap<>();


    private OAuthLocalConsumerCredentialMapper()
    {
        super();
    }

    
    @Override
    protected void init()
    {
    }

    
    // Initialization-on-demand holder.
    private static final class OAuthLocalCredentialMapperHolder
    {
        private static final OAuthLocalConsumerCredentialMapper INSTANCE = new OAuthLocalConsumerCredentialMapper();
    }
    // Singleton method
    public static OAuthLocalConsumerCredentialMapper getInstance()
    {
        return OAuthLocalCredentialMapperHolder.INSTANCE;
    }


    @Override
    public String getConsumerSecret(String consumerKey)
            throws CredentialStoreException
    {
        return consumerRegistry.get(consumerKey);
    }


    @Override
    public String putConsumerSecret(String consumerKey, String consumerSecret)
            throws CredentialStoreException
    {
        return consumerRegistry.put(consumerKey, consumerSecret);
    }

    @Override
    public void putConsumerSecrets(Map<String, String> consumerCredentials)
            throws CredentialStoreException
    {
        // TBD: Validation ??
        consumerRegistry.putAll(consumerCredentials);
    }


    @Override
    public String getCredentialSecret(String credentialType,
            String credentialKey) throws CredentialStoreException
    {
        if(CONSUMER_CREDENTIAL.equals(credentialType)) {
            return consumerRegistry.get(credentialKey);
        } else {
            return null;
        }
    }

    @Override
    public String putCredentialSecret(String credentialType,
            String credentialKey, String credentialSecret)
            throws CredentialStoreException
    {
        if(CONSUMER_CREDENTIAL.equals(credentialType)) {
            return consumerRegistry.put(credentialKey, credentialSecret);
        } else {
            return null;
        }
    }

    @Override
    public AccessCredential getAccesssCredential(String consumerKey) throws CredentialStoreException
    {
        return new OAuthAccessCredential(getConsumerSecret(consumerKey), null);
    }

    @Override
    public AccessCredential putAccesssCredential(String consumerKey, String consumerSecret) throws CredentialStoreException
    {
        String oldSecret = consumerRegistry.put(consumerKey, consumerSecret);
        return new OAuthAccessCredential(oldSecret, null);
    }


}
