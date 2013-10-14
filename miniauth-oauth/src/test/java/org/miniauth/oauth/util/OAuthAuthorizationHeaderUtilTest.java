package org.miniauth.oauth.util;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.util.FormParamUtil;

public class OAuthAuthorizationHeaderUtilTest
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
    public void testBuildOAuthAuthorizationValueString()
    {
        Map<String,String> params = new LinkedHashMap<>();  // for tracing, params are ordered...
        params.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, null);
        params.put(OAuthConstants.PARAM_OAUTH_SIGNATURE, "");
        params.put(OAuthConstants.PARAM_OAUTH_TOKEN, "x#y?z");
        params.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, "123456");

        String str = null;
        try {
            str = OAuthAuthorizationValueUtil.buildOAuthAuthorizationValueString(params);
            System.out.println("Param str = " + str);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
        assertEquals("oauth_consumer_key=\"\",oauth_signature=\"\",oauth_token=\"x%23y%3Fz\",oauth_timestamp=\"123456\"", str);
    }

}
