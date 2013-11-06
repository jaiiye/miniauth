package org.miniauth.web.oauth;

import org.miniauth.oauth.credential.mapper.OAuthCredentialMapper;
import org.miniauth.web.AbstractAuthHandler;
import org.miniauth.web.AuthHandler;


/**
 * Base class for OAuthProviderAuthHandler and client side AuthHandler implmentations.
 */
public abstract class OAuthAuthHandler extends AbstractAuthHandler implements AuthHandler
{
    private static final long serialVersionUID = 1L;

    public OAuthAuthHandler(OAuthCredentialMapper credentialMapper)
    {
        super(credentialMapper);
    }

    public OAuthCredentialMapper getOAuthCredentialMapper()
    {
        return (OAuthCredentialMapper) getCredentialMapper();
    }

    
    
}
