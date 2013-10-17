package org.miniauth.core;


/**
 * Auth information for OAuth (and for other auth protocols as well)
 * can be transmitted in the header, in the URL query, or in the url-encoded form.
 * This class defines the static constants for these three types.
 * Cf. http://tools.ietf.org/html/rfc5849#section-3.5
 */
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
