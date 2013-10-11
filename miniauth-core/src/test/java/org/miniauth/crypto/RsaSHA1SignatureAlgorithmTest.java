package org.miniauth.crypto;

import static org.junit.Assert.assertTrue;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.util.Base64Util;

public class RsaSHA1SignatureAlgorithmTest
{
    private SignatureAlgorithm signatureAlgorithm = null;

    @Before
    public void setUp() throws Exception
    {
        signatureAlgorithm = new RsaSHA1SignatureAlgorithm();
    }

    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void testDummy()
    {
    }
    
    // @Test
    public void testGenerateAndVerify()
    {
        String text = "I am happy.";
        // TBD:
        // How to generate RSA key ???
        try {
            KeyPairGenerator keyGen;
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair keyPair = keyGen.genKeyPair();
            // private or public key???
            byte[] privateEncoded =  keyPair.getPrivate().getEncoded();
        
            // ???
            // String key = new String(privateEncoded);
            String key = Base64Util.encodeBase64(privateEncoded);
            // ...

            String signature = signatureAlgorithm.generate(text, key);
            System.out.println("signature = " + signature);
            
            boolean verified = signatureAlgorithm.verify(text, key, signature);
            System.out.println("verified = " + verified);
            
            assertTrue(verified);
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (AuthSignatureException e) {
            e.printStackTrace();
        }
    }

}
