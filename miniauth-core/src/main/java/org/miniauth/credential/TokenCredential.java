package org.miniauth.credential;


/**
 * Resource owner credential (e.g., for OAuth).
 * We also use this for username/password based credential.
 */
public interface TokenCredential extends AuthCredential
{
    String getAccessToken();   // User identifier
    String getTokenSecret();   // User shared secret.
}
