package org.miniauth.credential;


// Resource owner credential
public interface TokenCredential
{
    String getAccessToken();   // User identifier
    String getTokenSecret();   // User shared secret.
}
