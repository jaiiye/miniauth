package org.miniauth.oauth.nonce;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NonceGeneratorTest
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
    public void testGenerateRandomNonce()
    {
        for(int i=0; i<20; i++) {
            String nonce = NonceGenerator.generateRandomNonce();
            System.out.println("nonce = " + nonce);
        }
    }

}
