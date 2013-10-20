package org.miniauth.web.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.core.AuthScheme;
import org.miniauth.oauth.credential.mapper.OAuthCredentialMapper;
import org.miniauth.web.ProviderAuthHandler;
import org.miniauth.web.oauth.OAuthProviderAuthHandler;


// TBD: Not full implemented....
// To be used on the "server side" implementation.
// This filter is included here as sort of a test case.
// It calls AuthHandler.verifyRequest() method.
public class OAuthProviderAuthFilter extends ProviderAuthFilter implements Filter
{
    private static final Logger log = Logger.getLogger(OAuthProviderAuthFilter.class.getName());

    OAuthCredentialMapper credentialMapper = null;
    
    // This should be multi-thread safe... ???
    private ProviderAuthHandler providerAuthHandler = null;
    // Lazy initialized...
    private ProviderAuthHandler getProviderAuthHandler()
    {
        if(providerAuthHandler == null) {
            // TBD:
            // Initialize credentialMapper ????
            // credentialMapper = null;
            providerAuthHandler = new OAuthProviderAuthHandler(credentialMapper);
        }
        return providerAuthHandler;
    }
    

    @Override
    public void init(FilterConfig config) throws ServletException
    {
        super.init(config);;
        super.setAuthScheme(AuthScheme.OAUTH);
        // TBD:
        // Initialize OAuthCredentialMapper
        // how ???
        credentialMapper = null;
        // ....
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException
    {

        // TBD:
        // Verify request oauth signature, etc...
        // ...
        
        
        boolean verified = false;
        try {
            verified = getProviderAuthHandler().verifyRequest((HttpServletRequest) req);
        } catch (MiniAuthException e) {
            // temporary
            throw new ServletException("Invalid auth.", e);
        } catch (Exception e) {
            // temporary
            throw new ServletException("Unknown error.", e);
        }
        
        // TBD:
        if(verified == false) {
            // bail out...
            // redirect/forward to error page ???
            // throw exeption ????
        }
        
        
        chain.doFilter(req, res);
    }


}
