package org.miniauth.oauth.credential;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.miniauth.credential.AuthCredentialConstants;
import org.miniauth.credential.ConsumerCredential;


/**
 * OAuth specific implementation of ConsumerCredential.
 */
public final class OAuthConsumerCredential implements ConsumerCredential, Serializable
{
    private static final long serialVersionUID = 1L;

    private final String consumerKey;
    private final String consumerSecret;

    public OAuthConsumerCredential(Map<String,String> authCredential)
    {
        this((authCredential!=null ? authCredential.get(AuthCredentialConstants.CONSUMER_KEY) : null),
                (authCredential!= null ? authCredential.get(AuthCredentialConstants.CONSUMER_SECRET) : null));
    }
    public OAuthConsumerCredential(String consumerKey, String consumerSecret)
    {
        super();
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    // Returns a "read-only" map of the bean content.
    @Override
    public Map<String,String> toReadOnlyMap()
    {
        Map<String,String> map = new HashMap<>();
        map.put(AuthCredentialConstants.CONSUMER_KEY, this.consumerKey);
        map.put(AuthCredentialConstants.CONSUMER_SECRET, this.consumerSecret);
        return map;
    }

    @Override
    public String getConsumerKey()
    {
        return consumerKey;
    }
//    public void setConsumerKey(String consumerKey)
//    {
//        this.consumerKey = consumerKey;
//    }

    @Override
    public String getConsumerSecret()
    {
        return consumerSecret;
    }
//    public void setConumerSecret(String consumerSecret)
//    {
//        this.consumerSecret = consumerSecret;
//    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((consumerKey == null) ? 0 : consumerKey.hashCode());
        result = prime * result
                + ((consumerSecret == null) ? 0 : consumerSecret.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OAuthConsumerCredential other = (OAuthConsumerCredential) obj;
        if (consumerKey == null) {
            if (other.consumerKey != null)
                return false;
        } else if (!consumerKey.equals(other.consumerKey))
            return false;
        if (consumerSecret == null) {
            if (other.consumerSecret != null)
                return false;
        } else if (!consumerSecret.equals(other.consumerSecret))
            return false;
        return true;
    }

}
