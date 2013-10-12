package org.miniauth.oauth.nonce;

import java.math.BigInteger;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


// TBD:
// http://tools.ietf.org/html/rfc5849#section-3.3
public final class NonceGenerator
{
    private static final Logger log = Logger.getLogger(NonceGenerator.class.getName());
    private static Random sRrandom = new Random(System.currentTimeMillis());

    private NonceGenerator() {};

    public static String generateRandomNonce()
    {
        return generateRandomNonce(32);
    }
    public static String generateRandomNonce(int numBits)
    {
        // temporary
        BigInteger big = new BigInteger(numBits, sRrandom);
        String nonce = big.toString();
        if(log.isLoggable(Level.FINER)) log.finer("Nonce generated = " + nonce);;
        return nonce;
    }

}
