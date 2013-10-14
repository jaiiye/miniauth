package org.miniauth.oauth.signature;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;
import org.miniauth.core.BaseURIInfo;
import org.miniauth.core.HttpMethod;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.ConsumerCredential;
import org.miniauth.credential.CredentialPair;
import org.miniauth.credential.TokenCredential;
import org.miniauth.oauth.credential.OAuthConsumerCredential;
import org.miniauth.oauth.credential.OAuthCredentialPair;
import org.miniauth.oauth.credential.OAuthTokenCredential;


public class OAuthSignatureVerifierTest
{
    private OAuthSignatureVerifier oAuthSignatureVerifier = null;

    @Before
    public void setUp() throws Exception
    {
        oAuthSignatureVerifier = new OAuthSignatureVerifier(); 
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testVerify()
    {
        String httpMethod = HttpMethod.GET;

        String uriScheme = "http";
        String userInfo = null;
        String host = "example.com";
        int port = 80;
        String path = "/a/b";
        BaseURIInfo uriInfo = new BaseURIInfo(uriScheme, userInfo, host, port, path);

        String consumerKey = "9djdj82h48djs9d2";
        String accessToken = "kkk9d7dh3k39sjv7";
        
        Map<String,String[]> authHeaders = new HashMap<>();
        authHeaders.put("oauth_consumer_key", new String[]{consumerKey});
        authHeaders.put("oauth_token", new String[]{accessToken});
        authHeaders.put("oauth_signature_method", new String[]{"HMAC-SHA1"});
        authHeaders.put("oauth_timestamp", new String[]{"137131201"});
        authHeaders.put("oauth_nonce", new String[]{"7d8f3e4a"});
        authHeaders.put("realm", new String[]{"Example"});
        authHeaders.put("oauth_signature", new String[]{"should not be included in the signature base string"});
        
        Map<String,String[]> formParams = new HashMap<>();
        formParams.put("c2", new String[]{});
        formParams.put("a3", new String[]{"2 q"});

        Map<String,String[]> queryParams = new HashMap<>();
        queryParams.put("b5", new String[]{"=%3D"});
        // queryParams.put("a3", new String[]{"a"});
        queryParams.put("c@", new String[]{});
        queryParams.put("a2", new String[]{"r b"});
        
        
        
        ConsumerCredential consumerCredential = new OAuthConsumerCredential(consumerKey, "_consumer_secret_");
        TokenCredential tokenCredential = new OAuthTokenCredential(accessToken, "_token_secret_");
        CredentialPair credential = new OAuthCredentialPair(consumerCredential, tokenCredential);
        AccessCredential accessCredential = credential.getAccessCredential();

        String signature = null;
        try {
            OAuthSignatureGenerator oAuthSignatureGenerator = new OAuthSignatureGenerator();
            signature = oAuthSignatureGenerator.generate(accessCredential, httpMethod, uriInfo, authHeaders, formParams, queryParams);
            System.out.println("signature = " + signature);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }

        authHeaders.put("oauth_signature", new String[]{signature});

        try {
            boolean verfied = oAuthSignatureVerifier.verify(credential, httpMethod, uriInfo, authHeaders, formParams, queryParams);
            System.out.println("verfied = " + verfied);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
    }

}
