package org.miniauth.basic.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;
import org.miniauth.credential.AuthCredentialConstants;


public class BasicAuthStringBuilderTest
{
    private BasicAuthStringBuilder basicAuthStringBuilder = null;

    @Before
    public void setUp() throws Exception
    {
        basicAuthStringBuilder = new BasicAuthStringBuilder();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testGenerateAuthorizationString()
    {
        String uname = "Aladdin";
        String pword = "open sesame";
        Map<String,String> authCredential = new HashMap<>();
        authCredential.put(AuthCredentialConstants.USERNAME, uname);
        authCredential.put(AuthCredentialConstants.PASSWORD, pword);
        
        String authString = null;
        try {
            authString = basicAuthStringBuilder.generateAuthorizationString(null, authCredential, null, null, null);
            System.out.println("authString = " + authString);
        } catch (MiniAuthException e) {
            e.printStackTrace();
        }
        assertEquals("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==", authString);        
    }

}
