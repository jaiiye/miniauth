package org.miniauth.oauth.nonce;

import java.math.BigInteger;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Simple nonce generaor.
 * Cf. http://tools.ietf.org/html/rfc5849#section-3.3
 */
public final class NonceGenerator
{
    private static final Logger log = Logger.getLogger(NonceGenerator.class.getName());
    private static Random sRrandom = new Random(System.currentTimeMillis());

    private NonceGenerator() {};

    /**
     * returns a 32-bit random nonce, by default.
     */
    public static String generateRandomNonce()
    {
        return generateRandomNonce(32);
    }

    /**
     * Returns a random nonce with the given number of bits.
     * @param numBits Maximum number of bits to be used in generating a nonce.
     * @return A nonce.
     */
    public static String generateRandomNonce(int numBits)
    {
        // temporary
        BigInteger big = new BigInteger(numBits, sRrandom);
        String nonce = big.toString();
        if(log.isLoggable(Level.FINER)) log.finer("Nonce generated = " + nonce);;
        return nonce;
    }

}
