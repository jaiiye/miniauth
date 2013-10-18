package org.miniauth.oauth.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.credential.OAuthAccessIdentity;
import org.miniauth.oauth.nonce.NonceGenerator;

public class OAuthParamMapUtilTest
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
    public void testBuildOAuthParamsOAuthOutgoingRequestAccessIdentity()
    {
        try {
            OAuthOutgoingRequest request = new OAuthOutgoingRequest();
           
            Map<String,String> authHeader = new HashMap<>();
            authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.HMAC_SHA1);
            authHeader.put(OAuthConstants.PARAM_OAUTH_TOKEN, "_token_1_");
            request.setAuthHeader(authHeader);
           
            Map<String,String[]> queryParams = new HashMap<>();
            queryParams.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, new String[]{SignatureMethod.PLAINTEXT});
            queryParams.put(OAuthConstants.PARAM_OAUTH_TOKEN, new String[]{"_token_2_"});
            queryParams.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, new String[]{"_consumer_key_2_"});
            request.setQueryParams(queryParams);

            AccessIdentity accessIdentity = new OAuthAccessIdentity("_consumer_key_3_", null); 
            OAuthParamMap oauthParamMap = OAuthParamMapUtil.buildOAuthParams(request, accessIdentity);
            System.out.println("oauthParamMap = " + oauthParamMap);
            
            String s1 = oauthParamMap.getSignatureMethod();
            String s2 = oauthParamMap.getConsumerKey();
            String s3 = oauthParamMap.getToken();
            assertEquals(SignatureMethod.HMAC_SHA1, s1);
            assertEquals("_consumer_key_3_", s2);
            assertNull(s3);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBuildOAuthParamsOAuthIncomingRequest()
    {
        try {
            OAuthIncomingRequest request = new OAuthIncomingRequest();
           
            Map<String,String> authHeader = new HashMap<>();
            authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.HMAC_SHA1);
            // authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.PLAINTEXT);
            authHeader.put(OAuthConstants.PARAM_OAUTH_TOKEN, "_token_1_");
            authHeader.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, "_consumer_key_1_");
            authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE, "_signature_1_");
            authHeader.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, Integer.toString((int) (System.currentTimeMillis() / 1000L)));
            authHeader.put(OAuthConstants.PARAM_OAUTH_NONCE, NonceGenerator.generateRandomNonce());
            request.setAuthHeader(authHeader);
           
            Map<String,String[]> queryParams = new HashMap<>();
            queryParams.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, new String[]{SignatureMethod.PLAINTEXT});
            queryParams.put(OAuthConstants.PARAM_OAUTH_TOKEN, new String[]{"_token_2_"});
            queryParams.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, new String[]{"_consumer_key_2_"});
            request.setQueryParams(queryParams);

            OAuthParamMap oauthParamMap = OAuthParamMapUtil.buildOAuthParams(request);
            System.out.println("oauthParamMap = " + oauthParamMap);
            
            String s1 = oauthParamMap.getSignatureMethod();
            String s2 = oauthParamMap.getConsumerKey();
            String s3 = oauthParamMap.getToken();
            assertEquals(SignatureMethod.HMAC_SHA1, s1);
            assertEquals("_consumer_key_1_", s2);
            assertEquals("_token_1_", s3);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
    }

}
