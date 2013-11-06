package org.miniauth.oauth.credential.mapper;

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
 * A simple implementation of OAuthConsumerCredentialMapper,
 *    which is a "credential mapper" for two-legged OAuth (2LO).
 * OAuthSingleConsumerCredentialMapper is a "dummy" class which is associated with a single consumer (consumer key/secret).
 * This class can be used when OAuthConsumerCredentialMapper is needed, as a sort of "null" implementation.
 */
public class OAuthSingleConsumerCredentialMapper extends AbstractOAuthCredentialMapper implements DynamicOAuthConsumerCredentialMapper
{
    private static final Logger log = Logger.getLogger(OAuthSingleConsumerCredentialMapper.class.getName());
    private static final long serialVersionUID = 1L;

    // Consumer credential is hard-coded.
    private String consumerKey = null;
    private String consumerSecret = null;

    public OAuthSingleConsumerCredentialMapper()
    {
        this(null, null);
    }
    public OAuthSingleConsumerCredentialMapper(String consumerKey, String consumerSecret)
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


    public String getConsumerKey()
    {
        return this.consumerKey;
    }
    public OAuthSingleConsumerCredentialMapper setConsumerKey(String consumerKey)
    {
        this.consumerKey = consumerKey;
        return this;
    }
    public String getConsumerSecret()
    {
        return consumerSecret;
    }

    @Override
    public String putConsumerSecret(String consumerKey, String consumerSecret) throws CredentialStoreException
    {
        // Note: OAuthSingleConsumerCredentialMapper is associated with a single consumer key/secret pair.
        this.consumerKey = consumerKey;
        String oldSecret = this.consumerSecret;
        this.consumerSecret = consumerSecret;
        return oldSecret;
    }
    @Override
    public void putConsumerSecrets(Map<String, String> consumerCredentials)  throws CredentialStoreException
    {
        throw new CredentialStoreException("Not a valid method for OAuthSingleConsumerCredentialMapper."); 
    }

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
            return null;
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
            return null;
        } else {
            return null;
        }
    }

    @Override
    public String getTokenSecret(String accessToken) throws CredentialStoreException
    {
        return null;
    }

    @Override
    public String putTokenSecret(String accessToken, String tokenSecret) throws CredentialStoreException
    {
        return null;
    }


    public AccessIdentity getAccessIdentity() throws CredentialStoreException
    {
        return new OAuthAccessIdentity(getConsumerKey(), null);
    }

    public AccessCredential getAccesssCredential() throws CredentialStoreException
    {
        return new OAuthAccessCredential(getConsumerSecret(), null);
    }
    @Override
    public AccessCredential getAccesssCredential(String consumerKey) throws CredentialStoreException
    {
        // TBD:
        // assert consumerKey == this.getConsumerKey().
        // ...
        return getAccesssCredential();
    }

    @Override
    public AccessCredential putAccesssCredential(String accessToken, String tokenSecret) throws CredentialStoreException
    {
        throw new CredentialStoreException("Not a valid method for OAuthSingleConsumerCredentialMapper."); 
    }


}
