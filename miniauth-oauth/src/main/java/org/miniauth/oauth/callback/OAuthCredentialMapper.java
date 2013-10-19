package org.miniauth.oauth.callback;

import java.util.HashMap;
import java.util.Map;

import org.miniauth.callback.CredentialMapper;


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
    public abstract String getCredentialSecret(String credentialName, String credentialKey);
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


}
