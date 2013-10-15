package org.miniauth.credential;


// Consumer credential.
public interface ConsumerCredential extends AuthCredential
{
    String getConsumerKey();      // Client Identifier
    String getConsumerSecret();   // Client shared secret
}
