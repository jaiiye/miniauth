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
public class ProviderAuthFilter implements Filter
{
    private static final Logger log = Logger.getLogger(ProviderAuthFilter.class.getName());

    // TBD
    private FilterConfig config = null;
    private String authMethod = null;


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
        // set authMethod here... (e.g., from config, etc....)
        // ...
        
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException
    {
        if(authMethod == null) {
            chain.doFilter(req, res);
            return;
        }

        // TBD:
        // Verify request oauth signature, etc...
        // ...
        
    }


}
