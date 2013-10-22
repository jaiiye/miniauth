package org.miniauth.gae.credential.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.CredentialStoreException;
import org.miniauth.oauth.credential.OAuthAccessCredential;
import org.miniauth.oauth.credential.mapper.AbstractOAuthCredentialMapper;


public final class OAuthGAEConsumerCredentialMapper extends AbstractOAuthCredentialMapper implements DynamicGAEConsumerCredentialMapper
{
    private static final Logger log = Logger.getLogger(OAuthGAEConsumerCredentialMapper.class.getName());

//    // TBD:
//    // this is just a temporary implementation.
//    // the credential should come from persistent storage such as config/DB, etc.
//    private final Map<String,String> consumerRegistry = new HashMap<>();
//

    private OAuthGAEConsumerCredentialMapper()
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
        private static final OAuthGAEConsumerCredentialMapper INSTANCE = new OAuthGAEConsumerCredentialMapper();
    }
    // Singleton method
    public static OAuthGAEConsumerCredentialMapper getInstance()
    {
        return OAuthLocalCredentialMapperHolder.INSTANCE;
    }


    @Override
    public String getConsumerSecret(String consumerKey)
            throws CredentialStoreException
    {
//        return consumerRegistry.get(consumerKey);
        throw new CredentialStoreException("Not implemented.");
    }


    @Override
    public String putConsumerSecret(String consumerKey, String consumerSecret)
            throws CredentialStoreException
    {
//        return consumerRegistry.put(consumerKey, consumerSecret);
        throw new CredentialStoreException("Not implemented.");
    }

    @Override
    public void putConsumerSecrets(Map<String, String> consumerCredentials)
            throws CredentialStoreException
    {
//        // TBD: Validation ??
//        consumerRegistry.putAll(consumerCredentials);
        throw new CredentialStoreException("Not implemented.");
    }


    @Override
    public String getCredentialSecret(String credentialType,
            String credentialKey) throws CredentialStoreException
    {
//        if(CONSUMER_CREDENTIAL.equals(credentialType)) {
//            return consumerRegistry.get(credentialKey);
//        } else {
//            return null;
//        }
        throw new CredentialStoreException("Not implemented.");
    }

    @Override
    public String putCredentialSecret(String credentialType,
            String credentialKey, String credentialSecret)
            throws CredentialStoreException
    {
//        if(CONSUMER_CREDENTIAL.equals(credentialType)) {
//            return consumerRegistry.put(credentialKey, credentialSecret);
//        } else {
//            return null;
//        }
        throw new CredentialStoreException("Not implemented.");
    }

    @Override
    public AccessCredential getAccesssCredential(String consumerKey)
            throws CredentialStoreException
    {
        return new OAuthAccessCredential(getConsumerSecret(consumerKey), null);
    }

    @Override
    public AccessCredential putAccesssCredential(String consumerKey,
            String consumerSecret) throws CredentialStoreException
    {
//        String oldSecret = consumerRegistry.put(consumerKey, consumerSecret);
//        return new OAuthAccessCredential(oldSecret, null);
        throw new CredentialStoreException("Not implemented.");
    }


}
