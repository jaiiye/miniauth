package org.miniauth.oauth.credential;


// Access credential/password stored on the server.
//   (not auth credential presented by the client).
public interface AccessCredential
{
    String getConsumerSecret();
    String getTokenSecret();
}
