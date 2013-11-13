package org.miniauth.oauth.credential;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.miniauth.credential.AuthCredentialConstants;
import org.miniauth.credential.TokenCredential;


/**
 * OAuth specific implementation of TokenCredential.
 */
public final class OAuthTokenCredential implements TokenCredential, Serializable
{
    private static final long serialVersionUID = 1L;

    private final String accessToken;
    private final String tokenSecret;

    public OAuthTokenCredential(Map<String,String> authCredential)
    {
        this((authCredential!=null ? authCredential.get(AuthCredentialConstants.ACCESS_TOKEN) : null),
                (authCredential!= null ? authCredential.get(AuthCredentialConstants.TOKEN_SECRET) : null));
    }
    public OAuthTokenCredential(String accessToken, String tokenSecret)
    {
        super();
        this.accessToken = accessToken;
        this.tokenSecret = tokenSecret;
    }

    // Returns a "read-only" map of the bean content.
    @Override
    public Map<String,String> toReadOnlyMap()
    {
        Map<String,String> map = new HashMap<String,String>();
        map.put(AuthCredentialConstants.ACCESS_TOKEN, this.accessToken);
        map.put(AuthCredentialConstants.TOKEN_SECRET, this.tokenSecret);
        return map;
    }

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
                + ((accessToken == null) ? 0 : accessToken.hashCode());
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
        OAuthTokenCredential other = (OAuthTokenCredential) obj;
        if (accessToken == null) {
            if (other.accessToken != null)
                return false;
        } else if (!accessToken.equals(other.accessToken))
            return false;
        if (tokenSecret == null) {
            if (other.tokenSecret != null)
                return false;
        } else if (!tokenSecret.equals(other.tokenSecret))
            return false;
        return true;
    }
 
    
    // For debugging...
    @Override
    public String toString()
    {
        return "OAuthTokenCredential [accessToken=" + accessToken
                + ", tokenSecret=" + tokenSecret + "]";
    }

    

}
