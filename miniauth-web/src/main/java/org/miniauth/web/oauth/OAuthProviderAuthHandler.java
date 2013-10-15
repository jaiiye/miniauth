package org.miniauth.web.oauth;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.credential.CredentialPair;
import org.miniauth.web.ProviderAuthHandler;


public class OAuthProviderAuthHandler extends OAuthAuthHandler implements ProviderAuthHandler
{
    public OAuthProviderAuthHandler()
    {
        
    }

    
    @Override
    public HttpServletRequest verifyRequest(CredentialPair credentialPair,
            HttpServletRequest request)
    {
        // TODO Auto-generated method stub
        return null;
    }

    
    
    
    
}
