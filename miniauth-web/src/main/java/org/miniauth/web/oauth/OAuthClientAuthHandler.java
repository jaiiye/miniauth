package org.miniauth.web.oauth;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.builder.AuthStringBuilder;
import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.exception.InvalidStateException;
import org.miniauth.oauth.builder.OAuthAuthStringBuilder;
import org.miniauth.web.ClientAuthHandler;
import org.miniauth.web.oauth.util.OAuthServletRequestUtil;


public class OAuthClientAuthHandler extends OAuthAuthHandler implements ClientAuthHandler
{

    // TBD: Is it safe to reuse this???
    private final AuthStringBuilder authStringBuilder;

    public OAuthClientAuthHandler()
    {
        authStringBuilder = new OAuthAuthStringBuilder();
    }
    
    // Note that "signing" is the last step.
    // Once the request is signed, it cannot be modified.
    public boolean isEndorsed(HttpServletRequest request)
    {
        // TBD: ...
        // return OAuthServletRequestUtil.isOAuthParamPresent(request);
        return OAuthServletRequestUtil.isOAuthSignaturePresent(request);
    }

    @Override
    public boolean endorseRequest(Map<String, String> authCredential, HttpServletRequest request) throws MiniAuthException, IOException
    {
        return endorseRequest(authCredential, request, null);
    }
    public boolean endorseRequest(Map<String, String> authCredential, HttpServletRequest request, String transmissionType) throws MiniAuthException, IOException
    {
        // Note:
        // At this point, request should not contain any oauth_x parameters.
        if(isEndorsed(request)) {
            // return false;
            throw new InvalidStateException("Request already endorsed.");
        }
        // ...
        // 
        // TBD:
        // We can use any existing oauth_ parameters already included in request
        //   and fill in what's missing including signature.
        // For now, we do not support such scenario.
        // ...
        // boolean oauthParamIncluded = OAuthServletRequestUtil.isOAuthParamPresent(request);
        // if(transmissionType == null) {
        //     transmissionType = OAuthServletRequestUtil.getOAuthParamTransmissionType(request);
        // }
        if(transmissionType == null) {
            // We always use the header type if it's not already set...
            transmissionType = ParameterTransmissionType.HEADER;
        } 
        // else validate ????
       
        
        
        // ...
        
        String httpMethod = request.getMethod();
        String requestUrl = request.getRequestURL().toString();
        URI baseURI = null;
        try {
            baseURI = new URI(requestUrl);
        } catch (URISyntaxException e) {
            // ??? This cannot happen.
            throw new InvalidInputException("Invalid requestUrl = " + requestUrl, e);
        }

        // ???
        Map<String,String> authHeader = OAuthServletRequestUtil.getAuthParams(request);
        Map<String,String[]> requestParams = request.getParameterMap();
        
        // ...
        
        
        // Add oauth_X params...
        switch(transmissionType) {
        case ParameterTransmissionType.HEADER:
            // ...
            break;
            // ....
        }
        
        
        String authString = authStringBuilder.generateAuthorizationString(transmissionType, authCredential, httpMethod, baseURI, authHeader, requestParams);

        
        // add oauth_X params including the signature to request.
        //
        switch(transmissionType) {
        case ParameterTransmissionType.HEADER:
            // ...
            break;
            // ....
        }
        
        
        
        return false;
    }
    
    
    
    
}
