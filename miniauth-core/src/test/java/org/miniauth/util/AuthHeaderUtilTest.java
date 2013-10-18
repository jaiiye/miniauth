package org.miniauth.util;

import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.miniauth.MiniAuthException;

public class AuthHeaderUtilTest
{
    @Test
    public void testGetAuthParamsStringString()
    {
        try {
            String paramString = "a=\"b\",c=\"d%72f\"";
            String authHeader = "OAuth " + paramString;
            String expectedAuthScheme = null;
            Map<String,String> authParams = AuthHeaderUtil.getAuthParams(authHeader, expectedAuthScheme);
            System.out.println("authParams = " + authParams);
            assertEquals("b", authParams.get("a"));
            assertEquals("drf", authParams.get("c"));
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
    }

}
