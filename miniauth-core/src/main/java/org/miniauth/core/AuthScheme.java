package org.miniauth.core;


// http://tools.ietf.org/html/rfc2617
public final class AuthScheme
{
    public static final String BASIC = "Basic";
    public static final String DIGEST = "Digest";
    public static final String OAuth = "OAuth";
    // etc. ...
   
    private AuthScheme() {}

}
