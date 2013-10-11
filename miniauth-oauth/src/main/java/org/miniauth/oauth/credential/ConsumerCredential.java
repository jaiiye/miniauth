package org.miniauth.oauth.credential;


// Consumer credential.
public interface ConsumerCredential
{
    String getConsumerKey();      // Client Identifier
    String getConsumerSecret();   // Client shared secret
}
