package org.miniauth.oauth.nonce;


public interface NonceStore
{
    // TBD:
    // accessIdentity ???
    
    // timestamp: unix epoch time in seconds.
    void store(String nonce, int timestamp);
    // Returns true if nonce does not exist in the store, or existing value is older than maxAge.
    boolean isNew(String nonce, int timestamp);
    // Returns isNew() after adding the nonce to the store.
    boolean check(String nonce, int timestamp);
    // Removes all nonces older than masAge.
    void clean();
    // etc...
}
