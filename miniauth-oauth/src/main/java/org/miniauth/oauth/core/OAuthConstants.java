package org.miniauth.oauth.core;

import java.util.HashSet;
import java.util.Set;


public final class OAuthConstants
{
    // OAuth? OAuth2?
    public static final String PARAM_REALM = "realm";
    public static final String PARAM_OAUTH_CALLBACK = "oauth_callback";

    public static final String PARAM_OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    public static final String PARAM_OAUTH_TOKEN = "oauth_token";
    public static final String PARAM_OAUTH_SIGNATURE = "oauth_signature";
    public static final String PARAM_OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    public static final String PARAM_OAUTH_NONCE = "oauth_nonce";
    public static final String PARAM_OAUTH_TIMESTAMP = "oauth_timestamp";
    public static final String PARAM_OAUTH_VERSION = "oauth_version";     // always "1.0"

    public static final String PARAM_OAUTH_TOKEN_VERIFIER = "oauth_verifier";
    // ...
    
    private static final Set<String> sOAuthParams;
    static {
        sOAuthParams = new HashSet<>();
        sOAuthParams.add(PARAM_OAUTH_CONSUMER_KEY);
        sOAuthParams.add(PARAM_OAUTH_TOKEN);
        sOAuthParams.add(PARAM_OAUTH_SIGNATURE);
        sOAuthParams.add(PARAM_OAUTH_SIGNATURE_METHOD);
        sOAuthParams.add(PARAM_OAUTH_NONCE);
        sOAuthParams.add(PARAM_OAUTH_TIMESTAMP);
        sOAuthParams.add(PARAM_OAUTH_VERSION);
        // callback ?? verifier ???
        // etc. ??
    }
 
    
    private OAuthConstants() {}

    
    // temporary
    public static boolean isOAuthParam(String param)
    {
        // TBD: which is better???
        // return param.startsWith("oauth_");
        return sOAuthParams.contains(param);
    }

    
}
