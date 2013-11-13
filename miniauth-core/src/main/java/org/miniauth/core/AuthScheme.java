package org.miniauth.core;


/**
 * Defaines constants for "auth scheme"
 * E.g., HTTP Basic, Digest, or OAuth and OAuth2, etc.
 * Cf. http://tools.ietf.org/html/rfc2617 
 */
public final class AuthScheme
{
    public static final String BASIC = "Basic";
    public static final String DIGEST = "Digest";
    public static final String OAUTH = "OAuth";
    public static final String OAUTH2 = "OAuth2";
    // etc. ...

    // Token type.
    public static final String TOKEN_TYPE_BEARER = "Beaer";
    // etc. ...
   
    private AuthScheme() {}
    

    // TBD: Better names for these functions?

    /**
     * Returns the auth name that is used right after "Authorization: ".
     * The reason for this function is that OAuth2 uses "Bearer" scheme/token type (currently),
     *       which is different from the auth scheme name.
     * @param authScheme The name of the auth scheme as defined in this class.
     * @return Returns the string that can be used as a "prefix" in the Authorization header.
     */
    public static String getAuthorizationHeaderAuthScheme(String authScheme)
    {
//        switch(authScheme) {
//        case OAUTH2:
//            return TOKEN_TYPE_BEARER;
//        default:
//            return authScheme;
//        }
        if(OAUTH2.equals(authScheme)) {
            return TOKEN_TYPE_BEARER;
        } else {
            return authScheme;
        }
    }
    
    /**
     * Reverse of getAuthorizationHeaderAuthScheme().
     * @param authorizationHeaderAuthScheme Given the "prefix" in the Authorization header, it returns the auth scheme.
     * @return
     */
    public static String getAuthSchemeFromAuthorizationHeaderAuthScheme(String authorizationHeaderAuthScheme)
    {
//        switch(authorizationHeaderAuthScheme) {
//        case TOKEN_TYPE_BEARER:
//            return OAUTH2;
//        default:
//            return authorizationHeaderAuthScheme;
//        }
        if(TOKEN_TYPE_BEARER.equals(authorizationHeaderAuthScheme)) {
            return OAUTH2;
        } else {
            return authorizationHeaderAuthScheme;
        }
    }

    
}
