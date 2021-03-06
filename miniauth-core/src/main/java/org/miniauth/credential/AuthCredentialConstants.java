package org.miniauth.credential;


/**
 * This is used to allow using a Map in place of a specific credential type.
 * Note that we use Maps with these constants as keys in the public APIs,
 * rather than using AuthCredential variety of bean classes. 
 */
public final class AuthCredentialConstants
{
    public static final String CONSUMER_KEY = "consumerKey";
    public static final String CONSUMER_SECRET = "consumerSecret";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String TOKEN_SECRET = "tokenSecret";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    // ...

}
