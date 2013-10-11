package org.miniauth.oauth.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;

public class PercentEncoderDecoderTest
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
        String text = "abcXYZ+/~!@#$%^&*()-=<>?XYZ{}|\\\"':;,.xy\u0007\u0008\u0032\u001bz_z";
        char[] c = text.toCharArray();
        String encoded = null;
        String decoded = null;
        try {
            // encoded = PercentEncoder.encode(c);
            encoded = PercentEncoder.encode(text);
            System.out.println("encoded = " + encoded);
            decoded = PercentDecoder.decode(encoded);
            System.out.println("decoded = " + decoded);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
        assertEquals(text, decoded);
    }

}
