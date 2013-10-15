package org.miniauth.web.oauth;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.CredentialPair;
import org.miniauth.web.ProviderAuthHandler;
import org.miniauth.web.util.ServletRequestUtil;


public class OAuthProviderAuthHandler extends OAuthAuthHandler implements ProviderAuthHandler
{
    private static final Logger log = Logger.getLogger(OAuthProviderAuthHandler.class.getName());

    public OAuthProviderAuthHandler()
    {
        
    }

    
    @Override
    public boolean verifyRequest(CredentialPair credentialPair, ServletRequest request) throws MiniAuthException
    {
        // TBD:
        Map<String,String[]> params = ServletRequestUtil.parseServletRequest(request);
        
        
        
        
        return false;
    }


}
