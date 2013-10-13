package org.miniauth.oauth.signature;

import static org.junit.Assert.assertEquals;

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
import org.miniauth.oauth.credential.OAuthAccessCredential;
import org.miniauth.oauth.credential.OAuthConsumerCredential;
import org.miniauth.oauth.credential.OAuthCredentialPair;
import org.miniauth.oauth.credential.OAuthTokenCredential;

public class OAuthSignatureGeneratorTest
{
    private OAuthSignatureGenerator oAuthSignatureGenerator = null;

    @Before
    public void setUp() throws Exception
    {
        oAuthSignatureGenerator = new OAuthSignatureGenerator(); 
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testGenerate()
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
        queryParams.put("a3", new String[]{"a"});
        queryParams.put("c@", new String[]{});
        queryParams.put("a2", new String[]{"r b"});
        
        AccessCredential credential = new OAuthAccessCredential("_consumer_secret_", "_token_secret_");
        try {
            String signature = oAuthSignatureGenerator.generate(credential, httpMethod, uriInfo, authHeaders, formParams, queryParams);
            System.out.println("signature = " + signature);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testBuildSignatureBaseString()
    {
        String httpMethod = HttpMethod.GET;

        String uriScheme = "http";
        String userInfo = null;
        String host = "example.com";
        int port = 80;
        String path = "/a/b";
        BaseURIInfo uriInfo = new BaseURIInfo(uriScheme, userInfo, host, port, path);

        Map<String,String[]> authHeaders = new HashMap<>();
        authHeaders.put("oauth_consumer_key", new String[]{"9djdj82h48djs9d2"});
        authHeaders.put("oauth_token", new String[]{"kkk9d7dh3k39sjv7"});
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
        queryParams.put("a3", new String[]{"a"});
        queryParams.put("c@", new String[]{});
        queryParams.put("a2", new String[]{"r b"});
        
        
        try {
            String baseSignatureString = oAuthSignatureGenerator.buildSignatureBaseString(httpMethod, uriInfo, authHeaders, formParams, queryParams);
            System.out.println("baseSignatureString = " + baseSignatureString);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
        
    }

//    @Test
//    public void testBuildBaseUriString()
//    {
//        String urlScheme = "http";
//        String host = "example.com";
//        int port = 80;
//        String path = "/a/b";
//        try {
//            String baseUri = oAuthSignatureGenerator.buildBaseUriString(urlScheme, host, port, path);
//            System.out.println("baseUri = " + baseUri);
//        } catch (BadRequestException e) {
//            e.printStackTrace();
//        }
//
//    }

    // http://tools.ietf.org/html/rfc5849#section-3.4.1.3.2
    @Test
    public void testNormalizeRequestParameters()
    {
        Map<String,String[]> authHeaders = new HashMap<>();
        authHeaders.put("oauth_consumer_key", new String[]{"9djdj82h48djs9d2"});
        authHeaders.put("oauth_token", new String[]{"kkk9d7dh3k39sjv7"});
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
        queryParams.put("a3", new String[]{"a"});
        queryParams.put("c@", new String[]{});
        queryParams.put("a2", new String[]{"r b"});
        
        String expected = "a2=r%20b&a3=2%20q&a3=a&b5=%3D%253D&c%40=&c2=&oauth_consumer_key=9djdj82h48djs9d2&oauth_nonce=7d8f3e4a&oauth_signature_method=HMAC-SHA1&oauth_timestamp=137131201&oauth_token=kkk9d7dh3k39sjv7";
        
        try {
            String normalized = oAuthSignatureGenerator.normalizeRequestParameters(authHeaders, formParams, queryParams);
            System.out.println("normalized = " + normalized);
            assertEquals(expected, normalized);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
    }

    
    
}
