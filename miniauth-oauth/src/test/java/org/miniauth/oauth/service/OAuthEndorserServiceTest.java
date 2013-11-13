package org.miniauth.oauth.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;
import org.miniauth.core.HttpMethod;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.oauth.common.OAuthOutgoingRequestBuilder;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.credential.mapper.OAuthLocalTokenCredentialMapper;

public class OAuthEndorserServiceTest
{
    private static final String consumerKey = "_consumer_key_1_";
    private static final String consumerSecret = "_consumer_secret_3_";
    private OAuthLocalTokenCredentialMapper credentialMapper = null;
    private OAuthEndorserService OAuthEndorserService = null;

    @Before
    public void setUp() throws Exception
    {
        credentialMapper = OAuthLocalTokenCredentialMapper.getInstance().setConsumerSecret(consumerSecret);
        credentialMapper.putTokenSecret("token1", "secret1");
        credentialMapper.putTokenSecret("token2", "secret2");
        credentialMapper.putTokenSecret("_token_1_", "_token_secret_3_");
        OAuthEndorserService = new OAuthEndorserService(credentialMapper);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testEndorse()
    {
        try {
            String httpMethod = HttpMethod.GET;
            String baseUri = "http://www.example.com:80/a/b";
    
            Map<String,String> authHeader = new HashMap<String,String>();
            authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.HMAC_SHA1);
            // authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.PLAINTEXT);
            authHeader.put(OAuthConstants.PARAM_OAUTH_TOKEN, "_token_1_");
            authHeader.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, consumerKey);
            // authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE, "_signature_1_");
//            authHeader.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, Integer.toString((int) (System.currentTimeMillis() / 1000L)));
//            authHeader.put(OAuthConstants.PARAM_OAUTH_NONCE, NonceGenerator.generateRandomNonce());
            authHeader.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, "123456789");
            authHeader.put(OAuthConstants.PARAM_OAUTH_NONCE, "123456");
    
            String queryString = "a=b&c=d%33e&f%65g=xxx";
            
            OAuthOutgoingRequestBuilder oauthOutgoingRequestBuilder = new OAuthOutgoingRequestBuilder();
            OAuthOutgoingRequest outgoingRequest = oauthOutgoingRequestBuilder.init().setHttpMethod(httpMethod).setBaseURI(baseUri).setAuthHeader(authHeader).setQueryParams(queryString).build();
            System.out.println("Input outgoingRequest = " + outgoingRequest);
            // boolean ready = outgoingRequest.isReady();
            // System.out.println("ready = " + ready);
            
            boolean endorsed = OAuthEndorserService.endorse(outgoingRequest);
            System.out.println("Endorsed outgoingRequest = " + outgoingRequest);
            System.out.println("endorsed = " + endorsed);
            assertTrue(endorsed);

            OAuthParamMap oauthParams = outgoingRequest.getOauthParamMap();
            String signature = oauthParams.getSignature();
            System.out.println("signature = " + signature);
            assertEquals("axSB2dUOB+yEHFnktb2ZN8fW+qo=", signature);
            
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }

        
    }

}
