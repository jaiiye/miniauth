package org.miniauth.oauth.credential;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.credential.AuthCredentialConstants;
import org.miniauth.credential.ConsumerCredential;
import org.miniauth.credential.CredentialPair;
import org.miniauth.credential.TokenCredential;


/**
 * OAuth specific implementation of CredentialPair.
 */
public class OAuthCredentialPair implements CredentialPair, Serializable
{
    private static final long serialVersionUID = 1L;

    private final ConsumerCredential consumerCredential;
    private final TokenCredential tokenCredential;
    // "cache" 
    private volatile AccessIdentity accessIdentity = null;
    private volatile AccessCredential accessCredential = null;
    // ...

    public OAuthCredentialPair(ConsumerCredential consumerCredential, TokenCredential tokenCredential)
    {
        super();
        this.consumerCredential = consumerCredential;
        this.tokenCredential = tokenCredential;
    }
    public OAuthCredentialPair(Map<String,String> authCredential)
    {
        this((authCredential!=null ? authCredential.get(AuthCredentialConstants.CONSUMER_KEY) : null),
                (authCredential!= null ? authCredential.get(AuthCredentialConstants.CONSUMER_SECRET) : null),
                (authCredential!=null ? authCredential.get(AuthCredentialConstants.ACCESS_TOKEN) : null),
                (authCredential!= null ? authCredential.get(AuthCredentialConstants.TOKEN_SECRET) : null));
    }
    public OAuthCredentialPair(String consumerKey, String consumerSecret, String accessToken, String tokenSecret)
    {
        this(new OAuthConsumerCredential(consumerKey, consumerSecret), new OAuthTokenCredential(accessToken, tokenSecret));
    }

    // Returns a "read-only" map of the bean content.
    @Override
    public Map<String,String> toReadOnlyMap()
    {
        Map<String,String> map = new HashMap<String,String>();
        if(this.consumerCredential != null) {
            map.put(AuthCredentialConstants.CONSUMER_KEY, this.consumerCredential.getConsumerKey());
            map.put(AuthCredentialConstants.CONSUMER_SECRET, this.consumerCredential.getConsumerSecret());
        }
        if(this.tokenCredential != null) {
            map.put(AuthCredentialConstants.ACCESS_TOKEN, this.tokenCredential.getAccessToken());
            map.put(AuthCredentialConstants.TOKEN_SECRET, this.tokenCredential.getTokenSecret());
        }
        return map;
    }


    @Override
    public String getConsumerKey()
    {
        if(consumerCredential != null) {
            return consumerCredential.getConsumerKey();
        } else {
            return null;
        }
    }

    @Override
    public String getConsumerSecret()
    {
        if(consumerCredential != null) {
            return consumerCredential.getConsumerSecret();
        } else {
            return null;
        }
    }

    @Override
    public String getAccessToken()
    {
        if(tokenCredential != null) {
            return tokenCredential.getAccessToken();
        } else {
            return null;
        }
    }

    @Override
    public String getTokenSecret()
    {
        if(tokenCredential != null) {
            return tokenCredential.getTokenSecret();
        } else {
            return null;
        }
    }

    @Override
    public ConsumerCredential getConsumerCredential()
    {
        return consumerCredential;
    }

    @Override
    public TokenCredential getTokenCredential()
    {
        return tokenCredential;
    }

    @Override
    public AccessIdentity getAccessIdentity()
    {
        if(accessIdentity == null) {
            accessIdentity = new OAuthAccessIdentity(getConsumerKey(), getAccessToken());
        }
        return accessIdentity;
    }

    @Override
    public AccessCredential getAccessCredential()
    {
        if(accessCredential == null) {
            accessCredential = new OAuthAccessCredential(getConsumerKey(), getAccessToken());
        }
        return accessCredential;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((consumerCredential == null) ? 0 : consumerCredential
                        .hashCode());
        result = prime * result
                + ((tokenCredential == null) ? 0 : tokenCredential.hashCode());
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
        OAuthCredentialPair other = (OAuthCredentialPair) obj;
        if (consumerCredential == null) {
            if (other.consumerCredential != null)
                return false;
        } else if (!consumerCredential.equals(other.consumerCredential))
            return false;
        if (tokenCredential == null) {
            if (other.tokenCredential != null)
                return false;
        } else if (!tokenCredential.equals(other.tokenCredential))
            return false;
        return true;
    }

    
    // For debugging...
    @Override
    public String toString()
    {
        return "OAuthCredentialPair [consumerCredential=" + consumerCredential
                + ", tokenCredential=" + tokenCredential + ", accessIdentity="
                + accessIdentity + ", accessCredential=" + accessCredential
                + "]";
    }

    
}
