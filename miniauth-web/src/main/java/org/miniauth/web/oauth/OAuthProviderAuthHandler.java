package org.miniauth.web.oauth;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.oauth.signature.OAuthSignatureVerifier;
import org.miniauth.signature.SignatureVerifier;
import org.miniauth.web.ProviderAuthHandler;
import org.miniauth.web.oauth.util.OAuthServletRequestUtil;


public class OAuthProviderAuthHandler extends OAuthAuthHandler implements ProviderAuthHandler
{
    private static final Logger log = Logger.getLogger(OAuthProviderAuthHandler.class.getName());

    public OAuthProviderAuthHandler()
    {
        
    }

    
    @Override
    public boolean verifyRequest(Map<String, String> authCredential, HttpServletRequest request) throws MiniAuthException, IOException
    {
        
        String httpMethod = request.getMethod();
        String host = request.getRemoteHost();
        String requestUri = request.getRequestURI();
        URI baseUri = null;
        try {
            baseUri = new URI(requestUri);
        } catch (URISyntaxException e) {
            // ??? This cannot happen.
            throw new InvalidInputException("Invalid requestUri = " + requestUri, e);
        }

        
        Map<String,String> authHeader = OAuthServletRequestUtil.getAuthParams(request);
        Map<String,String[]> requestParams = request.getParameterMap();

        
        SignatureVerifier signatureVerifier = new OAuthSignatureVerifier();
        boolean verified = signatureVerifier.verify(authCredential, httpMethod, baseUri, authHeader, requestParams);

        if(log.isLoggable(Level.FINE)) log.fine("verified = " + verified);;
        return verified;
    }


}
