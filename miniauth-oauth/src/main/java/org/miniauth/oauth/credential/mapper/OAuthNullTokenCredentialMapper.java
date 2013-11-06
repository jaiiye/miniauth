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
 * "Dummy" implementation of OAuthTokenCredentialMapper,
 *    which defines a service that returns a "token secret" given an access token.
 * Although it implements OAuthTokenCredentialMapper, it does not actually return real token secrets, however.
 * This class can be used where OAuthTokenCredentialMapper is needed,
 *    but the actual implementation is not important.
 * OAuthNullTokenCredentialMapper can be used, in particular, in Two-Legged OAuth (2LO).
 */
public final class OAuthNullTokenCredentialMapper extends AbstractOAuthCredentialMapper implements DynamicOAuthTokenCredentialMapper
{
    private static final Logger log = Logger.getLogger(OAuthNullTokenCredentialMapper.class.getName());
    
    // Consumer credential is hard-coded.
    private final String consumerKey;
    private final String consumerSecret;

//    public OAuthNullTokenCredentialMapper()
//    {
//        this(null, null);
//    }
    public OAuthNullTokenCredentialMapper(String consumerKey, String consumerSecret)
    {
        super();
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    @Override
    protected void init()
    {
        // TBD:
        // set consumerSecret, etc...
        // ...
    }


    @Override
    public String getConsumerKey()
    {
        return this.consumerKey;
    }
//    public OAuthNullTokenCredentialMapper setConsumerKey(String consumerKey)
//    {
//        this.consumerKey = consumerKey;
//        return this;
//    }

    @Override
    public String getConsumerSecret()
    {
        return consumerSecret;
    }
//    public OAuthNullTokenCredentialMapper setConsumerSecret(String consumerSecret)
//    {
//        this.consumerSecret = consumerSecret;
//        return this;
//    }

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
            return null;
        } else {
            return null;
        }
    }
    @Override
    public String putCredentialSecret(String credentialType, String credentialKey, String credentialSecret) throws CredentialStoreException
    {
        if(CONSUMER_CREDENTIAL.equals(credentialType)) {
//            String oldConsumerSecret = this.consumerSecret;
//            this.consumerSecret = credentialSecret;
//            return oldConsumerSecret;
            return this.consumerSecret;
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

    @Override
    public void putTokenSecrets(Map<String, String> tokenCredentials)
            throws CredentialStoreException
    {
        // ???
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
        String oldSecret = null;
        return new OAuthAccessCredential(getConsumerSecret(), oldSecret);
    }


}
