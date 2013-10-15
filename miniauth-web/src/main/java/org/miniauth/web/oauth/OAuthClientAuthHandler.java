package org.miniauth.web.oauth;

import javax.servlet.ServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.web.ClientAuthHandler;


public class OAuthClientAuthHandler extends OAuthAuthHandler implements ClientAuthHandler
{
    public OAuthClientAuthHandler()
    {
        
    }

    @Override
    public boolean prepareRequest(AccessIdentity accessIdentity,
            ServletRequest request) throws MiniAuthException
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    
    
    
}
