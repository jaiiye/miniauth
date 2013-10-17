package org.miniauth.credential;


/**
 * Access credential/password stored on the server ("shared secrets").
 *   (not auth credential presented by the client).
 */
public interface AccessCredential extends AuthCredential
{
    String getConsumerSecret();
    String getTokenSecret();
}
