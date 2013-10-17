package org.miniauth.credential;


/**
 * Client and User "identity".
 */
public interface AccessIdentity extends AuthCredential
{
    String getConsumerKey();
    String getAccessToken();
}
