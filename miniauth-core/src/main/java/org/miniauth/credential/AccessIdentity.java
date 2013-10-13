package org.miniauth.credential;


// Client and User "identity".
public interface AccessIdentity
{
    String getConsumerKey();
    String getAccessToken();
}
