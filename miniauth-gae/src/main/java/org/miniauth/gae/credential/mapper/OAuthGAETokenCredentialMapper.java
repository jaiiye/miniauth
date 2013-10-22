package org.miniauth.gae.credential.mapper;

import java.util.Map;

import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.credential.ConsumerCredential;
import org.miniauth.exception.CredentialStoreException;
import org.miniauth.oauth.credential.OAuthAccessCredential;
import org.miniauth.oauth.credential.OAuthAccessIdentity;
import org.miniauth.oauth.credential.OAuthConsumerCredential;
import org.miniauth.oauth.credential.mapper.AbstractOAuthCredentialMapper;


/**
 * Service that returns a "token secret" given an access token.
 * TBD ...
 */
public class OAuthGAETokenCredentialMapper extends AbstractOAuthCredentialMapper implements DynamicGAETokenCredentialMapper
{
//    // TBD:
//    // this is just a temporary implementation.
//    // the credential should come from persistent storage such as config/DB, etc.
//    private final Map<String,String> tokenRegistry = new HashMap<>();
    
    // Consumer credential is hard-coded.
    private String consumerKey = null;
    private String consumerSecret = null;

    public OAuthGAETokenCredentialMapper()
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

    @Override
    public String getConsumerKey()
    {
        return this.consumerKey;
    }
    public OAuthGAETokenCredentialMapper setConsumerKey(String consumerKey)
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
    public OAuthGAETokenCredentialMapper setConsumerSecret(String consumerSecret)
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
//        if(CONSUMER_CREDENTIAL.equals(credentialType)) {
//            return consumerSecret;
//        } else if(TOKEN_CREDENTIAL.equals(credentialType)) {
//            return tokenRegistry.get(credentialKey);
//        } else {
//            return null;
//        }
        throw new CredentialStoreException("Not implemented.");
    }
    @Override
    public String putCredentialSecret(String credentialType, String credentialKey, String credentialSecret) throws CredentialStoreException
    {
//        if(CONSUMER_CREDENTIAL.equals(credentialType)) {
//            String oldConsumerSecret = this.consumerSecret;
//            this.consumerSecret = credentialSecret;
//            return oldConsumerSecret;
//        } else if(TOKEN_CREDENTIAL.equals(credentialType)) {
//            return tokenRegistry.put(credentialKey, credentialSecret);
//        } else {
//            return null;
//        }
        throw new CredentialStoreException("Not implemented.");
    }

    @Override
    public String getTokenSecret(String accessToken) throws CredentialStoreException
    {
//        return tokenRegistry.get(accessToken);
        throw new CredentialStoreException("Not implemented.");
    }

    @Override
    public String putTokenSecret(String accessToken, String tokenSecret) throws CredentialStoreException
    {
//        return tokenRegistry.put(accessToken, tokenSecret);
        throw new CredentialStoreException("Not implemented.");
    }

    @Override
    public void putTokenSecrets(Map<String, String> tokenCredentials)
            throws CredentialStoreException
    {
        // TBD: Validation ??
//        tokenRegistry.putAll(tokenCredentials);
        throw new CredentialStoreException("Not implemented.");
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
//        String oldSecret = tokenRegistry.put(accessToken, tokenSecret);
//        return new OAuthAccessCredential(getConsumerSecret(), oldSecret);
        throw new CredentialStoreException("Not implemented.");
    }


}
