package org.miniauth.basic.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.exception.InternalErrorException;
import org.miniauth.util.Base64Util;



public final class BasicAuthorizationValueUtil
{
    private static final Logger log = Logger.getLogger(BasicAuthorizationValueUtil.class.getName());

    private BasicAuthorizationValueUtil() {}

    /**
     * It builds a string of (base64 encoded) username:password per HTTP Basic auth rule.
     * Cf. http://www.ietf.org/rfc/rfc2617.txt
     * Note that this does not build a full header string (e.g., including "Basic" etc.).
     * 
     * @param uname
     * @param pword
     * @return the auth string that can be used in a Basic auth header (after "Authorization: Basic ")
     * @throws InternalErrorException
     */
    public static String buildBasicAuthorizationValueString(String uname, String pword) throws InternalErrorException
    {
        if(uname == null) {
            return null;
        }
        String baseValue = uname + ":" + pword;
        String authValue = Base64Util.encodeBase64(baseValue);
        
        if(log.isLoggable(Level.FINER)) log.finer("authValue = " + authValue);
        return authValue;
    }
    

}
