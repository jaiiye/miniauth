package org.miniauth.core;


// http://tools.ietf.org/html/rfc2617
public final class AuthScheme
{
    public static final String BASIC = "Basic";
    public static final String DIGEST = "Digest";
    public static final String OAUTH = "OAuth";
    public static final String OAUTH2 = "OAuth2";
    // etc. ...
    
    public static final String TOKEN_TYPE_BEARER = "Beaer";
    // etc. ...
   
    private AuthScheme() {}
    
    
    // temporary
    // Returns the auth name that is used right after "Authorization: ".
    // The reason for this function is that OAuth2 uses "Bearer" scheme (currently),
    //      which is different from the auth scheme name.
    // TBD: Is there a better name for this function?
    public static String getAuthorizationHeaderAuthScheme(String authScheme)
    {
        switch(authScheme) {
        case OAUTH2:
            return TOKEN_TYPE_BEARER;
        default:
            return authScheme;
        }
    }
    // reverse
    // TBD: Is there a better name for this function?
    public static String getAuthSchemeFromAuthorizationHeaderAuthScheme(String authorizationHeaderAuthScheme)
    {
        switch(authorizationHeaderAuthScheme) {
        case TOKEN_TYPE_BEARER:
            return OAUTH2;
        default:
            return authorizationHeaderAuthScheme;
        }
    }

    
}
