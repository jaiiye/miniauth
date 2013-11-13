package org.miniauth.util;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;

public class FormParamUtilTest
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
    public void testBuildUrlEncodedFormParamString()
    {
        Map<String,String[]> params = new LinkedHashMap<String,String[]>();  // for tracing, params are ordered...
        params.put("a?b", new String[]{"x#y"});
        params.put("c-", null);
        params.put("d=", new String[]{});
        params.put("e&", new String[]{null});
        params.put("f%g", new String[]{"", "z@!~w"});

        String str = null;
        try {
            str = FormParamUtil.buildUrlEncodedFormParamString(params);
            System.out.println("Param str = " + str);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
        assertEquals("a%3Fb=x%23y&c-&d%3D&e%26&f%25g=f%25g=z%40%21%7Ew", str);
    }

}
