package org.miniauth.oauth.credential;

import java.io.Serializable;

import org.miniauth.credential.AccessCredential;


public final class OAuthAccessCredential implements AccessCredential, Serializable
{
    private static final long serialVersionUID = 1L;

    private final  String consumerSecret;
    private final String tokenSecret;
    
    public OAuthAccessCredential(String consumerSecret, String tokenSecret)
    {
        super();
        this.consumerSecret = consumerSecret;
        this.tokenSecret = tokenSecret;
    }

    @Override
    public String getConsumerSecret()
    {
        return consumerSecret;
    }
//    public void setConsumerSecret(String consumerSecret)
//    {
//        this.consumerSecret = consumerSecret;
//    }

    @Override
    public String getTokenSecret()
    {
        return tokenSecret;
    }
//    public void setTokenSecret(String tokenSecret)
//    {
//        this.tokenSecret = tokenSecret;
//    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((consumerSecret == null) ? 0 : consumerSecret.hashCode());
        result = prime * result
                + ((tokenSecret == null) ? 0 : tokenSecret.hashCode());
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
        OAuthAccessCredential other = (OAuthAccessCredential) obj;
        if (consumerSecret == null) {
            if (other.consumerSecret != null)
                return false;
        } else if (!consumerSecret.equals(other.consumerSecret))
            return false;
        if (tokenSecret == null) {
            if (other.tokenSecret != null)
                return false;
        } else if (!tokenSecret.equals(other.tokenSecret))
            return false;
        return true;
    }


}
