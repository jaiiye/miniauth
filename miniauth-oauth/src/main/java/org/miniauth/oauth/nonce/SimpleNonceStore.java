package org.miniauth.oauth.nonce;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Simple "nonce store" implementation.
 * Note:
 * Nonce should be really stored in a persistent storage.
 *   (This "simple" implementation will not generally work in a distributed system.)
 * NonceStore is per AuthIdentity (client+token).
 * TBD: If we use a persistent storage, 
 *    then a single nonce table for all AuthIdentity might be more convenient.
 */
public class SimpleNonceStore implements NonceStore, Serializable
{
    private static final Logger log = Logger.getLogger(SimpleNonceStore.class.getName());
    private static final long serialVersionUID = 1L;

    // temporary.
    private static final int DEFAULT_MAX_AGE = 10 * 24 * 3600;   // 10 days. arbitrary. 
    
    // NonceStore should be shared across the app.
    // Use singleton/multiton ???
    // ....

    // nonce stored more tha maxAge will not be considered as valid.
    private final int maxAge;
    
    // Note:
    // the current implementation works well if nonce is generally a random string.
    // If nonce is designed to be different for the same timestamp onlyu
    //   (and if there can be potentially many same nonces across different timestamps),
    // then the current implmentation may not be optimal....
    // ....

    // nonce -> timestamp (unix epoch time in seconds).
    private final Map<String, Integer> nonceMap;

    public SimpleNonceStore()
    {
        this(DEFAULT_MAX_AGE);
    }
    public SimpleNonceStore(int maxAge)
    {
        this.maxAge = maxAge;
        nonceMap = new HashMap<>();
    }

    public int getMaxAge()
    {
        return maxAge;
    }
//    public void setMaxAge(int maxAge)
//    {
//        this.maxAge = maxAge;
//    }

    @Override
    public void store(String nonce, int timestamp)
    {
        // Overwrites the existing entry, if any.
        nonceMap.put(nonce, timestamp);
    }

    @Override
    public boolean isNew(String nonce, int timestamp)
    {
        Integer existingTimestamp = nonceMap.get(nonce);
        if(existingTimestamp == null) {
            return true;
        } else {
            int nowSecs = (int) (System.currentTimeMillis() / 1000);
            if(existingTimestamp < nowSecs - maxAge) {
                // too old. ignore.
                nonceMap.remove(nonce);
                return true;
            } else {
                if(timestamp == existingTimestamp) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
    
    // Note that this method is not idempotent.
    // In general, it is better to call isNew() and then call store(),
    //     each of which is idempotent...
    @Override
    public boolean check(String nonce, int timestamp)
    {
        boolean isNew;
        Integer existingTimestamp = nonceMap.get(nonce);
        if(existingTimestamp == null) {
            isNew = true;
        } else {
            int nowSecs = (int) (System.currentTimeMillis() / 1000);
            if(existingTimestamp < nowSecs - maxAge) {
                // too old. ignore and add the new one.
                isNew = true;   // true or false???
            } else {
                if(timestamp == existingTimestamp) {
                    isNew = false;
                } else {
                    isNew = true;
                }
            }
        }
        nonceMap.put(nonce, timestamp);
        return isNew;
    }

    @Override
    public void clean()
    {
        int nowSecs = (int) (System.currentTimeMillis() / 1000);
        for(String n : nonceMap.keySet()) {
            Integer t = nonceMap.get(n);
            if(t == null || t < nowSecs - maxAge) {
                // ???
                nonceMap.remove(n);
            }
        }
    }


}
