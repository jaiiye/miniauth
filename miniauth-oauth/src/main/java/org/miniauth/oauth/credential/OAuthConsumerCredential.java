package org.miniauth.oauth.credential;

import java.io.Serializable;

import org.miniauth.credential.ConsumerCredential;


public final class OAuthConsumerCredential implements ConsumerCredential, Serializable
{
    private static final long serialVersionUID = 1L;

    private final String consumerKey;
    private final String consumerSecret;

    public OAuthConsumerCredential(String consumerKey, String consumerSecret)
    {
        super();
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
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
