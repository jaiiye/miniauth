package org.miniauth.oauth.common;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;


public class OAuthRequestUtilTest
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
    public void testUpdateOAuthHeaderWithOAuthParamMap()
    {
        Map<String,String> authHeader = new HashMap<>();
        authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.HMAC_SHA1);
        authHeader.put(OAuthConstants.PARAM_OAUTH_TOKEN, "_token_");
        int oldCnt = authHeader.size();
        OAuthParamMap oauthParamMap = new OAuthParamMap();
        oauthParamMap.setToken("_new_token_");
        oauthParamMap.setSignature("_signature_");
        try {
            Map<String,String> newAuthHeader = OAuthRequestUtil.updateOAuthHeaderWithOAuthParamMap(authHeader, oauthParamMap);
            System.out.println("newAuthHeader = " + newAuthHeader);
            String newToken = newAuthHeader.get(OAuthConstants.PARAM_OAUTH_TOKEN);
            assertEquals("_new_token_", newToken);
            int newCnt = newAuthHeader.size();
            assertEquals(3, newCnt);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateParamsWithOAuthParamMap()
    {
        Map<String,String[]> queryParams = new HashMap<>();
        queryParams.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, new String[]{SignatureMethod.HMAC_SHA1});
        queryParams.put(OAuthConstants.PARAM_OAUTH_TOKEN, new String[]{"_token_"});
        int oldCnt = queryParams.size();
        OAuthParamMap oauthParamMap = new OAuthParamMap();
        oauthParamMap.setToken("_new_token_");
        oauthParamMap.setSignature("_signature_");
        try {
            Map<String,String[]> newQueryParams = OAuthRequestUtil.updateParamsWithOAuthParamMap(queryParams, oauthParamMap);
            System.out.println("newQueryParams = " + newQueryParams);
            String newToken = newQueryParams.get(OAuthConstants.PARAM_OAUTH_TOKEN)[0];
            assertEquals("_new_token_", newToken);
            int newCnt = newQueryParams.size();
            assertEquals(3, newCnt);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }

    }

}
