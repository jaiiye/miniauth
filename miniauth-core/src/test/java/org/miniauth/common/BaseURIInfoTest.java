package org.miniauth.common;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.common.BaseURIInfo;

public class BaseURIInfoTest
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
    public void testBuildURI()
    {
        String uriScheme = "Http";
        String userInfo = null;
        String host = "Example.com";
        int port = 80;
        String path = "/abc";

        BaseURIInfo uriInfo = new BaseURIInfo(uriScheme, userInfo, host, port, path);
        String uriStr = uriInfo.toString();
        System.out.println("uriStr = " + uriStr);
        
    }

}
