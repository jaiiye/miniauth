package org.miniauth.web.oauth.signature;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.credential.AccessIdentity;
import org.miniauth.credential.CredentialPair;



public final class WebOAuthSignatureUtil
{

    private WebOAuthSignatureUtil() {}

    
    // TBD.
    public static String generateSignature(AccessIdentity accessIdentity, HttpServletRequest request)
    {
        
        
        return null;
    }

    // TBD.
    public static boolean verifySignature(CredentialPair credentialPair, HttpServletRequest request)
    {
        
        
        return false;
    }
    
    
}
