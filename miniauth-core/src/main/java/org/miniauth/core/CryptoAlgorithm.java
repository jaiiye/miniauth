package org.miniauth.core;



/**
 * Defines crypto algorithm names.
 * (These two are those required by OAuth standard.)
 * Cf. http://tools.ietf.org/html/rfc5849
 */
public final class CryptoAlgorithm
{
    // TBD:
    public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    public static final String RSA_SHA1_ALGORITHM = "SHA1withRSA";
    // etc...

    private CryptoAlgorithm() {}


}
