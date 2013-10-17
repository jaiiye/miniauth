package org.miniauth.util;

import java.util.logging.Logger;


/**
 * Base64 encoder/decoder methods.
 */
public final class Base64Util
{
    private static final Logger log = Logger.getLogger(Base64Util.class.getName());

    private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final int[]  BYTES = new int[128];
    static {
        for(int i=0; i<BYTES.length; i++){
            BYTES[i] = -1;   // ????
        }
        for(int i=0; i<CHARS.length; i++){
            BYTES[CHARS[i]] = i;
        }
    }

    private Base64Util() {}

    
    public static String encodeBase64(String plainText)
    {
        char[] chars = encode(plainText);
        return new String(chars);
    }
    public static String encodeBase64(byte[] bytes)
    {
        char[] chars = encode(bytes);
        return new String(chars);
    }

    public static char[] encode(String plainText)
    {
        char[] chars = encode(plainText.getBytes());
        return chars;
    }
    public static char[] encode(byte[] bytes)
    {
        int size = bytes.length;
        char[] ar = new char[((size + 2) / 3) * 4];
        int a = 0;
        int i=0;
        while(i < size) {
            byte b0 = bytes[i++];
            byte b1 = (i < size) ? bytes[i++] : 0;
            byte b2 = (i < size) ? bytes[i++] : 0;

            int mask = 0x3F;
            ar[a++] = CHARS[(b0 >> 2) & mask];
            ar[a++] = CHARS[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
            ar[a++] = CHARS[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
            ar[a++] = CHARS[b2 & mask];
        }
        switch(size % 3) {
            case 1: ar[--a]  = '=';
            case 2: ar[--a]  = '=';
        }
        return ar;
    }

    public static String decodeBase64(String encodedText)
    {
        byte[] bytes = decode(encodedText.toCharArray());
        return new String(bytes);
    }
    public static String decodeBase64(char[] encodedChars)
    {
        byte[] bytes = decode(encodedChars);
        return new String(bytes);
    }

    public static byte[] decode(String encodedText)
    {
        return decode(encodedText.toCharArray());
    }
    public static byte[] decode(char[] encodedChars)
    {
        int len = encodedChars.length;
        int delta = (len >= 2 && encodedChars[len-1]=='=' && encodedChars[len-2]=='=') ? 2 : (len >= 1 && encodedChars[len-1]=='=') ? 1 : 0;
        byte[] bytes = new byte[len*3/4 - delta];
        int mask = 0xFF;
        int index = 0;
        for(int i=0; i<len; i+=4) {
            int c0 = BYTES[encodedChars[i]];
            int c1 = BYTES[encodedChars[i + 1]];
            bytes[index++]= (byte) (((c0 << 2) | (c1 >> 4)) & mask);
            if(index >= bytes.length){
                return bytes;
            }
            int c2 = BYTES[encodedChars[i + 2]];
            bytes[index++]= (byte) (((c1 << 4) | (c2 >> 2)) & mask);
            if(index >= bytes.length){
                return bytes;
            }
            int c3 = BYTES[encodedChars[i + 3]];
            bytes[index++]= (byte) (((c2 << 6) | c3) & mask);
        }
        return bytes;
    } 
    
    
    // TBD:
    // Need to compare these two versions.
    // ....
    
    public static char[] encode(byte[] bytes, int iOff, int iLen) 
    {
        int oDataLen = (iLen*4+2)/3;       // without padding
        int oLen = ((iLen+2)/3)*4;         // including padding
        char[] out = new char[oLen];
        int ip = iOff;
        int iEnd = iOff + iLen;
        int op = 0;
        while (ip < iEnd) {
            int i0 = bytes[ip++] & 0xff;
            int i1 = ip < iEnd ? bytes[ip++] & 0xff : 0;
            int i2 = ip < iEnd ? bytes[ip++] & 0xff : 0;
            int o0 = i0 >>> 2;
            int o1 = ((i0 &   3) << 4) | (i1 >>> 4);
            int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
            int o3 = i2 & 0x3F;
            out[op++] = CHARS[o0];
            out[op++] = CHARS[o1];
            out[op] = op < oDataLen ? CHARS[o2] : '='; op++;
            out[op] = op < oDataLen ? CHARS[o3] : '='; op++; 
        }
        return out; 
    }
    public static byte[] decode (char[] chars, int iOff, int iLen) 
    {
        // if (iLen%4 != 0) {
        //     throw new IllegalArgumentException ("Length of Base64 encoded input string is not a multiple of 4.");
        // }
        while (iLen > 0 && chars[iOff+iLen-1] == '=') {
            iLen--;
        }
        int oLen = (iLen*3) / 4;
        byte[] out = new byte[oLen];
        int ip = iOff;
        int iEnd = iOff + iLen;
        int op = 0;
        while (ip < iEnd) {
           int i0 = chars[ip++];
           int i1 = chars[ip++];
           int i2 = ip < iEnd ? chars[ip++] : 'A';
           int i3 = ip < iEnd ? chars[ip++] : 'A';
           // if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127) {
           //    throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
           // }
           int b0 = BYTES[i0];
           int b1 = BYTES[i1];
           int b2 = BYTES[i2];
           int b3 = BYTES[i3];
           // if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0) {
           //    throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
           // }
           int o0 = ( b0       <<2) | (b1>>>4);
           int o1 = ((b1 & 0xf)<<4) | (b2>>>2);
           int o2 = ((b2 &   3)<<6) | b3;
           out[op++] = (byte)o0;
           if (op<oLen) out[op++] = (byte) o1;
           if (op<oLen) out[op++] = (byte) o2; 
        }
        return out; 
    }

}
