package org.miniauth.web.oauth;

import javax.servlet.ServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.CredentialPair;
import org.miniauth.web.ProviderAuthHandler;


public class OAuthProviderAuthHandler extends OAuthAuthHandler implements ProviderAuthHandler
{
    public OAuthProviderAuthHandler()
    {
        
    }

    
    @Override
    public boolean verifyRequest(CredentialPair credentialPair, ServletRequest request) throws MiniAuthException
    {
        // TODO Auto-generated method stub
        return false;
    }


    
    
}
