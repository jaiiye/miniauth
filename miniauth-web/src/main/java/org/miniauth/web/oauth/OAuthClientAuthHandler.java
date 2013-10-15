package org.miniauth.web.oauth;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.credential.AccessIdentity;
import org.miniauth.web.ClientAuthHandler;


public class OAuthClientAuthHandler extends OAuthAuthHandler implements ClientAuthHandler
{
    public OAuthClientAuthHandler()
    {
        
    }

    @Override
    public HttpServletRequest prepareRequest(AccessIdentity accessIdentity,
            HttpServletRequest request)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    
    
}
