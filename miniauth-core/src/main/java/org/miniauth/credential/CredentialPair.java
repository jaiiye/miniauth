package org.miniauth.credential;


/**
 * Consumer + Token.
 */
public interface CredentialPair extends ConsumerCredential, TokenCredential
{
    ConsumerCredential getConsumerCredential();
    TokenCredential getTokenCredential();
    AccessIdentity getAccessIdentity();
    AccessCredential getAccessCredential();
}
