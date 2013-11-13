package org.miniauth.oauth.service;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;
import org.miniauth.core.AuthScheme;
import org.miniauth.core.HttpMethod;
import org.miniauth.credential.AccessCredential;
import org.miniauth.oauth.common.OAuthIncomingRequest;
import org.miniauth.oauth.common.OAuthIncomingRequestBuilder;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.oauth.common.OAuthOutgoingRequestBuilder;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.credential.OAuthAccessCredential;
import org.miniauth.oauth.credential.mapper.OAuthLocalCredentialMapper;

public class OAuthVerifierServiceTest
{
    private static final String consumerKey = "_consumer_key_1_";
    private static final String consumerSecret = "_consumer_secret_3_";
    private OAuthLocalCredentialMapper credentialMapper = null;
    private OAuthVerifierService verifierService = null;

    @Before
    public void setUp() throws Exception
    {
        credentialMapper = OAuthLocalCredentialMapper.getInstance();
        credentialMapper.putConsumerSecret(consumerKey, consumerSecret);
        credentialMapper.putTokenSecret("token1", "secret1");
        credentialMapper.putTokenSecret("token2", "secret2");
        credentialMapper.putTokenSecret("_token_1_", "_token_secret_3_");
        verifierService = new OAuthVerifierService(credentialMapper);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testVerify()
    {
        try {
            String httpMethod = HttpMethod.GET;
            // String baseUri = "http://www.example.com:80/a/b";
            String baseUri = "http://www.example.com/a/b";
    
            Map<String,String> authHeader = new HashMap<String,String>();
            authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.HMAC_SHA1);
            // authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.PLAINTEXT);
            authHeader.put(OAuthConstants.PARAM_OAUTH_TOKEN, "_token_1_");
            authHeader.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, consumerKey);
            // authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE, "_signature_1_");
            // authHeader.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, Integer.toString((int) (System.currentTimeMillis() / 1000L)));
            // authHeader.put(OAuthConstants.PARAM_OAUTH_NONCE, NonceGenerator.generateRandomNonce());
            // authHeader.put(OAuthConstants.PARAM_OAUTH_VERSION, OAuthConstants.OAUTH_VERSION_STRING);
    
            String queryString = "a=b&c=d%33e&f%65g=xxx";
            
            OAuthOutgoingRequestBuilder oauthOutgoingRequestBuilder = new OAuthOutgoingRequestBuilder();
            OAuthOutgoingRequest outgoingRequest = oauthOutgoingRequestBuilder.init().setHttpMethod(httpMethod).setBaseURI(baseUri).setAuthHeader(authHeader).setQueryParams(queryString).build();
            System.out.println("Input outgoingRequest = " + outgoingRequest);

            AccessCredential accessCredential = new OAuthAccessCredential(consumerSecret, "_token_secret_3_"); 

            OAuthRequestEndorser.getInstance().endorse(accessCredential, outgoingRequest);
            System.out.println("Endorsed outgoingRequest = " + outgoingRequest);
            
            OAuthParamMap oauthParams = outgoingRequest.getOauthParamMap();
            String signature = oauthParams.getSignature();
            System.out.println("signature = " + signature);
            
            // authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE, signature);
            String newAuthString = oauthParams.buildUrlEncodedParamString(outgoingRequest.getAuthParamTransmissionType());
            String newAuthHeaderStr = AuthScheme.OAUTH + " " + newAuthString;
            System.out.println("newAuthHeaderStr = " + newAuthHeaderStr);
            

            OAuthIncomingRequestBuilder oauthIncomingRequestBuilder = new OAuthIncomingRequestBuilder();
            OAuthIncomingRequest incomingRequest = oauthIncomingRequestBuilder.init().setHttpMethod(httpMethod).setBaseURI(baseUri).setAuthHeaderAuthorizationString(newAuthHeaderStr).setQueryParams(queryString).build();
            System.out.println("incomingRequest = " + incomingRequest);

            
            boolean verified =  verifierService.verify(incomingRequest);
            System.out.println("verified = " + verified);
            assertTrue(verified);
            
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
    }

}
