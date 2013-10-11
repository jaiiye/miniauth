package org.miniauth.oauth.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Logger;

import org.miniauth.exception.InternalErrorException;


// TBD:
// http://tools.ietf.org/html/rfc5849#section-3.6
// http://tools.ietf.org/html/rfc3986
public final class PercentDecoder
{
    private static final Logger log = Logger.getLogger(PercentDecoder.class.getName());

    private PercentDecoder() {}

    public static String decode(String text) throws InternalErrorException
    {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalErrorException("Failed to decode String: " + text, e);
        }
        return decoded;
    }
    
}
