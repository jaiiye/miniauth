package org.miniauth.web.oauth;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.web.ClientAuthHandler;
import org.miniauth.web.oauth.util.OAuthServletRequestUtil;


public class OAuthClientAuthHandler extends OAuthAuthHandler implements ClientAuthHandler
{
    public OAuthClientAuthHandler()
    {
        
    }

    @Override
    public boolean prepareRequest(Map<String, String> authCredential, HttpServletRequest request) throws MiniAuthException, IOException
    {
        // TBD:
        // The "preparation" algorithm changes
        //    based on whether the input request already contains at least one oatuh_x params. 
        // ...
        // boolean oauthParamIncluded = OAuthServletRequestUtil.isOAuthParamPresent(request);
        // For now, getOAuthParamTransmissionType() always returns null.
        String transmissionType = OAuthServletRequestUtil.getOAuthParamTransmissionType(request);
        if(transmissionType == null) {
            // We always use the header if it's not already determined by the input request...
            transmissionType = ParameterTransmissionType.HEADER;
        }
        
        
        
        // ...
        
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

        // ???
        Map<String,String> authHeader = OAuthServletRequestUtil.getAuthParams(request);
        Map<String,String[]> requestParams = request.getParameterMap();
        
        // ...
        
        
        
        return false;
    }
    
    
    
    
}
