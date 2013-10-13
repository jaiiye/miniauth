package org.miniauth.oauth.credential;

import java.io.Serializable;

import org.miniauth.credential.AccessIdentity;


public final class OAuthAccessIdentity implements AccessIdentity, Serializable
{
    private static final long serialVersionUID = 1L;

    private final String consumerKey;
    private final String accessToken;
    
    public OAuthAccessIdentity(String consumerKey, String accessToken)
    {
        super();
        this.consumerKey = consumerKey;
        this.accessToken = accessToken;
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
    public String getAccessToken()
    {
        return accessToken;
    }
//    public void setAccessToken(String accessToken)
//    {
//        this.accessToken = accessToken;
//    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((accessToken == null) ? 0 : accessToken.hashCode());
        result = prime * result
                + ((consumerKey == null) ? 0 : consumerKey.hashCode());
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
        OAuthAccessIdentity other = (OAuthAccessIdentity) obj;
        if (accessToken == null) {
            if (other.accessToken != null)
                return false;
        } else if (!accessToken.equals(other.accessToken))
            return false;
        if (consumerKey == null) {
            if (other.consumerKey != null)
                return false;
        } else if (!consumerKey.equals(other.consumerKey))
            return false;
        return true;
    }


    // For debugging...
    @Override
    public String toString()
    {
        return "OAuthAccessIdentity [consumerKey=" + consumerKey
                + ", accessToken=" + accessToken + "]";
    }


}
