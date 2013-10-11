package org.miniauth.oauth.core;


// http://tools.ietf.org/html/rfc5849#section-3.5
public final class ParameterTransmissionType
{
    // "Authorization" header.
    public static final String HEADER = "header";
    // Form-encoded in Entity-body
    public static final String FORM = "form";
    // Query string in URI.
    public static final String QUERY = "query";
    // ...
   
    private ParameterTransmissionType() {}

}
