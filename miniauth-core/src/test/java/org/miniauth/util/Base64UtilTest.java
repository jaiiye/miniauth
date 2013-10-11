package org.miniauth.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Base64UtilTest
{

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testEncodeDecode()
    {
        String plainText = "abcXYZ+/~!@#$%^&*()-=<>?XYZ{}|\\\"':;,.xy\u0007\u0008\u0032\u001bz";

        String econdedText = Base64Util.encodeBase64(plainText.getBytes());
        System.out.println("econdedText = " + econdedText);
        
        String decodedText = Base64Util.decodeBase64(econdedText);
        System.out.println("decodedText = " + decodedText);
        
        assertEquals(plainText, decodedText);
    }

    @Test
    public void testEncodeDecode2()
    {
        String plainText = "abcXYZ+/~!@#$%^&*()-=<>?XYZ{}|\\\"':;,.xy\u0007\u0008\u0032\u001bz";

        char[] chars = Base64Util.encode(plainText.getBytes(), 0, plainText.getBytes().length);
        // System.out.println("plainText.getBytes().length = " + plainText.getBytes().length);
        String econdedText = new String(chars);
        System.out.println("econdedText = " + econdedText);
        
        byte[] bytes = Base64Util.decode(econdedText.toCharArray(), 0, econdedText.toCharArray().length);
        // System.out.println("econdedText.toCharArray().length = " + econdedText.toCharArray().length);
        String decodedText = new String(bytes);
        System.out.println("decodedText = " + decodedText);
        
        assertEquals(plainText, decodedText);
    }


}
