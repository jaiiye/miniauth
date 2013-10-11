package org.miniauth.crypto;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.exception.AuthSignatureException;

public class HmacSHA1SignatureAlgorithmTest
{
    private SignatureAlgorithm signatureAlgorithm = null;

    @Before
    public void setUp() throws Exception
    {
        signatureAlgorithm = new HmacSHA1SignatureAlgorithm();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testGenerateAndVerify()
    {
        String text = "I am happy.";
        String key = "abc";
        try {
            String signature = signatureAlgorithm.generate(text, key);
            System.out.println("signature = " + signature);
            
            boolean verified = signatureAlgorithm.verify(text, key, signature);
            System.out.println("verified = " + verified);
            
            assertTrue(verified);
        } catch (AuthSignatureException e) {
            e.printStackTrace();
        }
    }

}
