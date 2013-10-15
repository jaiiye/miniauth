package org.miniauth.web.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


// To be used on the "server side" implementation.
// Place holder...
public abstract class ProviderAuthFilter implements Filter
{
    private static final Logger log = Logger.getLogger(ProviderAuthFilter.class.getName());

    // TBD
    private FilterConfig config = null;
    private String authScheme = null;

    protected final FilterConfig getConfig()
    {
        return this.config;
    }

    protected final String getAuthScheme()
    {
        return this.authScheme;
    }
    protected final void setAuthScheme(String authScheme)
    {
        this.authScheme = authScheme;
    }

    @Override
    public void destroy()
    {
        this.config = null;
    }

    @Override
    public void init(FilterConfig config) throws ServletException
    {
        this.config = config;
        // TBD:
        // set authScheme here... (e.g., from config, etc....)
        // ...
        
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException
    {
        if(authScheme == null) {
            chain.doFilter(req, res);
            return;
        }

        // TBD:
        // ...
        
    }


}
