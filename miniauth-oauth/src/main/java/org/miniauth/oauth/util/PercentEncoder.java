package org.miniauth.oauth.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.logging.Logger;

import org.miniauth.exception.InternalErrorException;

/**
 * Percent decoder.
 * http://tools.ietf.org/html/rfc5849#section-3.6
 * http://tools.ietf.org/html/rfc3986
 * This is not a generic percent encoder.
 *   it is specific to OAuth.
 */
public final class PercentEncoder
{
    private static final Logger log = Logger.getLogger(PercentEncoder.class.getName());
    private static char[] HEXCHAR = "0123456789ABCDEF".toCharArray();

    private PercentEncoder() {}

    public static char getHexChar(int halfByte)
    {
        return HEXCHAR[halfByte];
    }

    public static String encode(String text) throws InternalErrorException
    {
        if(text == null) {
            return null;
        }
        return encode(text.toCharArray());
    }
    public static String encode(char[] c) throws InternalErrorException
    {
        if(c == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int len = c.length;
        try {
            for(int i=0; i<len; i++) {
                char ch = c[i];
                if((ch >= 'A' && ch <= 'Z') 
                        || (ch >= 'a' && ch <= 'z') 
                        || (ch >= '0' && ch <= '9')
                        || (ch == '-') ||  (ch == '.')  || (ch == '_') || (ch == '~')
                        ) {
                    sb.append(ch);
                } else {
                    byte[] bytes = ("" + ch).getBytes("UTF-8");
                    for(byte b : bytes) {
                        int upper = (((int) b) & 0xF0) >> 4;
                        int lower = ((int) b) & 0x0F;
                        sb.append('%');
                        sb.append(HEXCHAR[upper]);
                        sb.append(HEXCHAR[lower]);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new InternalErrorException("Failed to encode String: " + Arrays.toString(c), e);
        }
        return sb.toString();
    }


}
