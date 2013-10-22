package org.miniauth.oauth.credential.mapper;

import java.util.logging.Logger;

import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.CredentialStoreException;
import org.miniauth.oauth.credential.OAuthAccessCredential;


/**
 * Service that returns a "secret" given a credential key.
 * Specific implementation (based on the app requirement) should be inherited from this class.
 */
public abstract class AbstractOAuthCredentialMapper implements DynamicOAuthCredentialMapper   //, DynamicOAuthTokenCredentialMapper
{
    private static final Logger log = Logger.getLogger(AbstractOAuthCredentialMapper.class.getName());

    // temporary
    protected static final String CONSUMER_CREDENTIAL = "consumerCredential";
    protected static final String TOKEN_CREDENTIAL = "tokenCredential";
    // etc...

    public AbstractOAuthCredentialMapper()
    {
        // To be overridden in subclasses.
        init();
    }

    protected void init()
    {
        // empty
    }


//    @Override
//    public abstract String getCredentialSecret(String credentialType, String credentialKey);
//    @Override
//    public abstract String putCredentialSecret(String credentialName, String credentialKey, String credentialSecret);


    @Override
    public String getConsumerSecret(String consumerKey) throws CredentialStoreException
    {
        return getCredentialSecret(CONSUMER_CREDENTIAL, consumerKey);
    }
    @Override
    public String putConsumerSecret(String consumerKey, String consumerSecret) throws CredentialStoreException
    {
        return putCredentialSecret(CONSUMER_CREDENTIAL, consumerKey, consumerSecret);
    }

    @Override
    public String getTokenSecret(String accessToken) throws CredentialStoreException
    {
        return getCredentialSecret(TOKEN_CREDENTIAL, accessToken);
    }
    @Override
    public String putTokenSecret(String accessToken, String tokenSecret) throws CredentialStoreException
    {
        return putCredentialSecret(TOKEN_CREDENTIAL, accessToken, tokenSecret);
    }

//    @Override
//    public String getConsumerKey()
//    {
//        return null;
//    }
//    @Override
//    public String getConsumerSecret()
//    {
//        return null;
//    }
//    public ConsumerCredential getConsumerCredential()
//    {
//        return null;
//    }
//  @Override
//    public AccessIdentity getAccessIdentity(String accessToken)
//    {
//        return null;
//    }
//    @Override
//    public AccessCredential getAccesssCredential(String accessToken)
//    {
//        return null;
//    }


    @Override
    public AccessCredential getAccesssCredential(AccessIdentity accessIdentity) throws CredentialStoreException
    {
        if(accessIdentity == null) {
            return null;
        }
        String consumerKey = accessIdentity.getConsumerKey();
        String accessToken = accessIdentity.getAccessToken();
        String consumerSecret = getConsumerSecret(consumerKey);
        String tokenSecret = getTokenSecret(accessToken);
        return new OAuthAccessCredential(consumerSecret, tokenSecret);
    }

    @Override
    public AccessCredential putAccesssCredential(AccessIdentity accessIdentity, AccessCredential accessCredential) throws CredentialStoreException
    {
        if(accessIdentity == null || accessCredential == null) {   // ???
            return null;
        }
        String consumerKey = accessIdentity.getConsumerKey();
        String accessToken = accessIdentity.getAccessToken();
        String consumerSecret = accessCredential.getConsumerSecret();
        String tokenSecret = accessCredential.getTokenSecret();
        String oldConsumerSecret = putConsumerSecret(consumerKey, consumerSecret);
        String oldTokenSecret = putTokenSecret(accessToken, tokenSecret);
        return new OAuthAccessCredential(oldConsumerSecret, oldTokenSecret);
    }

}
