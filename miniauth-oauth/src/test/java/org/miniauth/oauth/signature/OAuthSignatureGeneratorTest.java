package org.miniauth.oauth.signature;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;
import org.miniauth.common.BaseURIInfo;
import org.miniauth.core.HttpMethod;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AuthCredentialConstants;
import org.miniauth.oauth.credential.OAuthAccessCredential;

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
        //BaseURIInfo uriInfo = new BaseURIInfo(uriScheme, userInfo, host, port, path);
        URI uriInfo = null;
        try {
            uriInfo = new URI(uriScheme, userInfo, host, port, path, null, null);
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }

        String consumerKey = "9djdj82h48djs9d2";
        String accessToken = "kkk9d7dh3k39sjv7";

        Map<String,String> authHeader = new HashMap<>();
        authHeader.put("oauth_consumer_key", consumerKey);
        authHeader.put("oauth_token", accessToken);
        authHeader.put("oauth_signature_method", "HMAC-SHA1");
        authHeader.put("oauth_timestamp", "137131201");
        authHeader.put("oauth_nonce", "7d8f3e4a");
        authHeader.put("realm", "Example");
        authHeader.put("oauth_signature", "should not be included in the signature base string");
        
        Map<String,String[]> formParams = new HashMap<>();
        formParams.put("c2", new String[]{});
        formParams.put("a3", new String[]{"2 q"});

        Map<String,String[]> queryParams = new HashMap<>();
        queryParams.put("b5", new String[]{"=%3D"});
        // queryParams.put("a3", new String[]{"a"});
        queryParams.put("c@", new String[]{});
        queryParams.put("a2", new String[]{"r b"});
        
        // AccessCredential credential = new OAuthAccessCredential("_consumer_secret_", "_token_secret_");
        Map<String,String> credential = new HashMap<>();
        credential.put(AuthCredentialConstants.CONSUMER_SECRET, "_consumer_secret_");
        credential.put(AuthCredentialConstants.TOKEN_SECRET, "_token_secret_");
        try {
            String signature = oAuthSignatureGenerator.generate(credential, httpMethod, uriInfo, authHeader, formParams, queryParams);
            System.out.println("signature = " + signature);
            assertEquals("DCTRdsyA5MrN50OB1IC2RJk38zs=", signature);
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

        Map<String,String> authHeader = new HashMap<>();
        authHeader.put("oauth_consumer_key", "9djdj82h48djs9d2");
        authHeader.put("oauth_token", "kkk9d7dh3k39sjv7");
        authHeader.put("oauth_signature_method", "HMAC-SHA1");
        authHeader.put("oauth_timestamp", "137131201");
        authHeader.put("oauth_nonce", "7d8f3e4a");
        authHeader.put("realm", "Example");
        authHeader.put("oauth_signature", "should not be included in the signature base string");
        
        Map<String,String[]> formParams = new HashMap<>();
        formParams.put("c2", new String[]{});
        formParams.put("a3", new String[]{"2 q"});

        Map<String,String[]> queryParams = new HashMap<>();
        queryParams.put("b5", new String[]{"=%3D"});
        // queryParams.put("a3", new String[]{"a"});
        queryParams.put("c@", new String[]{});
        queryParams.put("a2", new String[]{"r b"});
        
        
        try {
            String baseSignatureString = oAuthSignatureGenerator.buildSignatureBaseString(httpMethod, uriInfo, authHeader, formParams, queryParams);
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
        Map<String,String> authHeader = new HashMap<>();
        authHeader.put("oauth_consumer_key", "9djdj82h48djs9d2");
        authHeader.put("oauth_token", "kkk9d7dh3k39sjv7");
        authHeader.put("oauth_signature_method", "HMAC-SHA1");
        authHeader.put("oauth_timestamp", "137131201");
        authHeader.put("oauth_nonce", "7d8f3e4a");
        authHeader.put("realm", "Example");
        authHeader.put("oauth_signature", "should not be included in the signature base string");
        
        Map<String,String[]> formParams = new HashMap<>();
        formParams.put("c2", new String[]{});
        formParams.put("a3", new String[]{"2 q"});

        Map<String,String[]> queryParams = new HashMap<>();
        queryParams.put("b5", new String[]{"=%3D"});
        // queryParams.put("a3", new String[]{"a"});
        queryParams.put("c@", new String[]{});
        queryParams.put("a2", new String[]{"r b"});
        
        String expected = "a2=r%20b&a3=2%20q&b5=%3D%253D&c%40=&c2=&oauth_consumer_key=9djdj82h48djs9d2&oauth_nonce=7d8f3e4a&oauth_signature_method=HMAC-SHA1&oauth_timestamp=137131201&oauth_token=kkk9d7dh3k39sjv7";
        
        try {
            String normalized = oAuthSignatureGenerator.normalizeRequestParameters(authHeader, formParams, queryParams);
            System.out.println("normalized = " + normalized);
            assertEquals(expected, normalized);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
    }

    
    
}
