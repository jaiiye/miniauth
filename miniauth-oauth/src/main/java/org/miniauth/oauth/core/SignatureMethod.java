package org.miniauth.oauth.core;

import java.util.HashMap;
import java.util.Map;

import org.miniauth.core.CryptoAlgorithm;


// http://tools.ietf.org/html/rfc5849
// TBD: Move this to the core module???
public final class SignatureMethod
{
    public static final String HMAC_SHA1 = "HMAC-SHA1";
    public static final String RSA_SHA1 = "RSA-SHA1";
    public static final String PLAINTEXT = "PLAINTEXT";
    // etc...

   
    private SignatureMethod() {}

    // TBD:
    // java.security.Signature algo name.
    // ????
    private static Map<String,String> algorithmName;
    static {
        algorithmName = new HashMap<>();
        algorithmName.put(HMAC_SHA1, CryptoAlgorithm.HMAC_SHA1_ALGORITHM);
        algorithmName.put(RSA_SHA1, CryptoAlgorithm.RSA_SHA1_ALGORITHM);
        algorithmName.put(PLAINTEXT, null);  // ????
        // ...
    }
    
    public static String getAlgorithmName(String sigMethod)
    {
        return algorithmName.get(sigMethod);
    }
    
    public static boolean isValid(String sigMethod)
    {
        return algorithmName.containsKey(sigMethod);
    }
    
    public static boolean requiresNonceAndTimestamp(String sigMethod)
    {
        if(algorithmName.containsKey(sigMethod)) {
            return ! sigMethod.equals(PLAINTEXT);
        } else {
            return false; // ???
        }
    }


}
