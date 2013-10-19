package org.miniauth.oauth.callback;

import org.miniauth.callback.CredentialMapper;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.oauth.credential.OAuthAccessCredential;


/**
 * Service that returns a "secret" given a credential key.
 * Specific implementation (based on the app requirement) should be inherited from this class.
 */
public abstract class OAuthCredentialMapper implements CredentialMapper
{
    // temporary
    protected static final String CONSUMER_CREDENTIAL = "consumerCredential";
    protected static final String TOKEN_CREDENTIAL = "tokenCredential";
    // etc...

    public OAuthCredentialMapper()
    {
        // To be overridden in subclasses.
        init();
    }

    protected void init()
    {
        // empty
    }


    @Override
    public abstract String getCredentialSecret(String credentialType, String credentialKey);
    public abstract String putCredentialSecret(String credentialName, String credentialKey, String credentialSecret);


    public String getConsumerSecret(String consumerKey)
    {
        return getCredentialSecret(CONSUMER_CREDENTIAL, consumerKey);
    }
    public String putConsumerSecret(String consumerKey, String consumerSecret)
    {
        return putCredentialSecret(CONSUMER_CREDENTIAL, consumerKey, consumerSecret);
    }

    public String getTokenSecret(String accessToken)
    {
        return getCredentialSecret(TOKEN_CREDENTIAL, accessToken);
    }
    public String putTokenSecret(String accessToken, String tokenSecret)
    {
        return putCredentialSecret(TOKEN_CREDENTIAL, accessToken, tokenSecret);
    }

    public AccessCredential getAccesssCredential(AccessIdentity accessIdentity)
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

    public AccessCredential putAccesssCredential(AccessIdentity accessIdentity, AccessCredential accessCredential)
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
