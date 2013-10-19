package org.miniauth.oauth.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;
import org.miniauth.core.HttpMethod;
import org.miniauth.credential.AccessCredential;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.credential.OAuthAccessCredential;
import org.miniauth.oauth.service.OAuthRequestEndorser;

public class OAuthOutgoingRequestTest
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
    public void testOpenEndorsedURLConnection()
    {
        try {
            String httpMethod = HttpMethod.GET;
            String baseUri = "http://www.google.com/";
    
            Map<String,String> authHeader = new HashMap<>();
            authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.HMAC_SHA1);
            // authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.PLAINTEXT);
            authHeader.put(OAuthConstants.PARAM_OAUTH_TOKEN, "_token_1_");
            authHeader.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, "_consumer_key_1_");
            // authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE, "_signature_1_");
//            authHeader.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, Integer.toString((int) (System.currentTimeMillis() / 1000L)));
//            authHeader.put(OAuthConstants.PARAM_OAUTH_NONCE, NonceGenerator.generateRandomNonce());
            authHeader.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, "123456789");
            authHeader.put(OAuthConstants.PARAM_OAUTH_NONCE, "123456");
    
            String queryString = "aa=b&c=d%33e&f%65g=xxx";
            
            OAuthOutgoingRequestBuilder oauthOutgoingRequestBuilder = new OAuthOutgoingRequestBuilder();
            OAuthOutgoingRequest outgoingRequest = oauthOutgoingRequestBuilder.init().setHttpMethod(httpMethod).setBaseURI(baseUri).setAuthHeader(authHeader).setQueryParams(queryString).build();
            System.out.println("Input outgoingRequest = " + outgoingRequest);

            AccessCredential accessCredential = new OAuthAccessCredential("_consumer_secret_3_", "_token_secret_3_"); 

            OAuthRequestEndorser.getInstance().endorse(accessCredential, outgoingRequest);
            System.out.println("Endorsed outgoingRequest = " + outgoingRequest);
            
//            OAuthParamMap oauthParams = outgoingRequest.getOauthParamMap();
//            String signature = oauthParams.getSignature();
//            System.out.println("signature = " + signature);
//            assertEquals("axSB2dUOB+yEHFnktb2ZN8fW+qo=", signature);
            
            URL url = outgoingRequest.getURL();
            System.out.println("url = " + url);
            
            
            HttpURLConnection httpConn = outgoingRequest.openEndorsedURLConnection();
            System.out.println("httpConn = " + httpConn);
//            Map<String,List<String>> props1 = httpConn.getRequestProperties();
//            System.out.println("props1 = " + props1);

            httpConn.connect();

//            Map<String,List<String>> props2 = httpConn.getRequestProperties();
//            System.out.println("props2 = " + props2);
            
            Scanner s = null;
            int code = httpConn.getResponseCode();
            if (code != 200) {
                System.out.println("error: " + code);
                s = new Scanner(httpConn.getErrorStream());
            } else {
                System.out.println("code: " + code);
                s = new Scanner(httpConn.getInputStream());
            }
            s.useDelimiter("\\Z");
            String response = s.next();
            System.out.println("response = " + response);
            if(s != null) {
                s.close();
            }
            
            
        } catch (MiniAuthException | IOException e) {
            e.printStackTrace();
        }

        
    }

}
