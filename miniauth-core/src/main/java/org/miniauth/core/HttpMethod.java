package org.miniauth.core;


/**
 * Defines the HTTP method constants.
 * Note that we use all-caps for the names (following the general convention).
 */
public final class HttpMethod
{
    // Note the upper cases.
    public static final String HEAD = "HEAD";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String DELETE = "DELETE";
    // etc. ...
   
    private HttpMethod() {}

    /**
     * Converts the given method name to Upper case.
     * @param method  Method name (but, not necessarily a method name constant, which should be all caps).
     * @return the Method name constant.
     */
    public static String sanitizeName(String method)
    {
        // TBD: Validate the name???
        if(method == null) {
            return null;
        }
        return method.toUpperCase();
    }

}
