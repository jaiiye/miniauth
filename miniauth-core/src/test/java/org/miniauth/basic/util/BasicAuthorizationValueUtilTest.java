package org.miniauth.basic.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.exception.InternalErrorException;

public class BasicAuthorizationValueUtilTest
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
    public void testBuildBasicAuthorizationValueString()
    {
        String uname = "Aladdin";
        String pword = "open sesame";
        String authValue = null;
        try {
            authValue = BasicAuthorizationValueUtil.buildBasicAuthorizationValueString(uname, pword);
            System.out.println("authValue = " + authValue);
        } catch (InternalErrorException e) {
            e.printStackTrace();
        }
        assertEquals("QWxhZGRpbjpvcGVuIHNlc2FtZQ==", authValue);        
    }

}
