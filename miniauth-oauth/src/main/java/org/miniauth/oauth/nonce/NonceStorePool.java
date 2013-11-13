package org.miniauth.oauth.nonce;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.credential.AccessIdentity;


/**
 * NonceStore pool.
 * Note:
 * This is not really a "pool"
 * More like a factory + cache.
 *   (Each NonceStore for a given AccessIdentity is conceptually a singleton.)
 * TBD: This should really be implemented using a distributed cache like memcache.
 *  Otherwise, this will not work in a distributed server system.
 */

public final class NonceStorePool
{
    private static final Logger log = Logger.getLogger(NonceStorePool.class.getName());

    // TBD:
    // {AccessIdentity -> nonceStore???
    private final Map<AccessIdentity, NonceStore> nonceStoreMap;

    // Map of catetory -> nonceStore.
    // identity: arbitrary identifier....
    private NonceStorePool()
    {
        nonceStoreMap = new HashMap<AccessIdentity, NonceStore>();
    }

    // Initialization-on-demand holder.
    private static final class NonceStorePoolHolder
    {
        private static final NonceStorePool INSTANCE = new NonceStorePool();
    }
    // Singleton method
    public static NonceStorePool getInstance()
    {
        return NonceStorePoolHolder.INSTANCE;
    }

    public NonceStore getNonceStore(AccessIdentity identity)
    {
        if(nonceStoreMap.containsKey(identity)) {
            return nonceStoreMap.get(identity);
        } else {
            // tbd
            NonceStore nonceStore = new SimpleNonceStore();
            nonceStoreMap.put(identity, nonceStore);
            return nonceStore;
        }
    }

    public NonceStore putNonceStore(NonceStore nonceStore)
    {
        return putNonceStore(null, nonceStore);
    }
    public NonceStore putNonceStore(AccessIdentity identity, NonceStore nonceStore)
    {
        // Overwrites the existing value, if any.
        nonceStore = nonceStoreMap.put(identity, nonceStore);
        return nonceStore;
    }

}
