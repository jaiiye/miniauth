package org.miniauth.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
import org.miniauth.web.oauth.OAuthURLConnectionAuthHandler;
import org.miniauth.web.oauth.util.OAuthURLConnectionUtil;

public class URLConnectionAuthHandlerTest
{

    private static final String consumerKey = "_consumer_key_1_";
    private static final String consumerSecret = "_consumer_secret_3_";
    private static final String accessToken = "_token_1_";
    private static final String tokenSecret = "_token_secret_3_";
    private OAuthLocalTokenCredentialMapper credentialMapper = null;
    private URLConnectionAuthHandler authHandler = null;

    @Before
    public void setUp() throws Exception
    {
        credentialMapper = OAuthLocalTokenCredentialMapper.getInstance().setConsumerKey(consumerKey).setConsumerSecret(consumerSecret);
        credentialMapper.putTokenSecret("token1", "secret1");
        credentialMapper.putTokenSecret("token2", "secret2");
        credentialMapper.putTokenSecret(accessToken, tokenSecret);
        credentialMapper.setConsumerKey(consumerKey);
        credentialMapper.setConsumerSecret(consumerSecret);
        authHandler = new OAuthURLConnectionAuthHandler(credentialMapper);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testEndorseRequest()
    {
        try {
            String httpMethod = HttpMethod.GET;
            String baseUri = "http://www.miniauth.org/";
    
            Map<String,String> authHeader = new HashMap<>();
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
            
            
            URL url = outgoingRequest.getURL();
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            
            boolean endorsed = authHandler.endorseRequest(accessToken, httpConn);
            System.out.println("Endorsed HttpURLConnection = " + httpConn);
            System.out.println("endorsed = " + endorsed);
            assertTrue(endorsed);

            // This does not work since Java URLConnection does not return auth related params (e.g., those in Authorization header...)
            String signature = OAuthURLConnectionUtil.getOAuthSignature(httpConn);
            System.out.println("signature = " + signature);
            // assertEquals("axSB2dUOB+yEHFnktb2ZN8fW+qo=", signature);
            
        } catch (MiniAuthException | IOException e) {
            e.printStackTrace();
        }
    }

}
