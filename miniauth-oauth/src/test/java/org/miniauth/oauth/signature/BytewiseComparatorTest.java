package org.miniauth.oauth.signature;

import java.util.Comparator;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BytewiseComparatorTest
{
    private Comparator<String> byteComaparator;

    @Before
    public void setUp() throws Exception
    {
        byteComaparator = new BytewiseComparator();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testCompare()
    {
        String str1a = "abc";
        String str1b = "xyz";
        int cmp1 = byteComaparator.compare(str1a, str1b);
        System.out.println("cmp1 = " + cmp1);
        assertEquals(-1, cmp1);
        
        String str2a = "";
        String str2b = "xyz";
        int cmp2 = byteComaparator.compare(str2a, str2b);
        System.out.println("cmp2 = " + cmp2);
        assertEquals(-1, cmp2);
        
        String str3a = "abc";
        String str3b = "abcd";
        int cmp3 = byteComaparator.compare(str3a, str3b);
        System.out.println("cmp3 = " + cmp3);
        assertEquals(-1, cmp3);

        String str4a = "xyzw";
        String str4b = "xyz";
        int cmp4 = byteComaparator.compare(str4a, str4b);
        System.out.println("cmp4 = " + cmp4);
        assertEquals(1, cmp4);

        String str5a = "xyz";
        String str5b = "";
        int cmp5 = byteComaparator.compare(str5a, str5b);
        System.out.println("cmp5 = " + cmp5);
        assertEquals(1, cmp5);

    }

}
