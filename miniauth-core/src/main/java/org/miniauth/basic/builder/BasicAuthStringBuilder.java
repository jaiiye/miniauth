package org.miniauth.basic.builder;

import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.basic.util.BasicAuthorizationValueUtil;
import org.miniauth.builder.AuthStringBuilder;
import org.miniauth.core.AuthScheme;
import org.miniauth.credential.AuthCredentialConstants;


public class BasicAuthStringBuilder implements AuthStringBuilder
{
    private static final Logger log = Logger.getLogger(BasicAuthStringBuilder.class.getName());
    
    public BasicAuthStringBuilder()
    {
    }
    

    @Override
    public String generateAuthorizationString(
            String transmissionType, Map<String, String> authCredential, String httpMethod,
            URI baseURI, Map<String,String> authHeader, Map<String, String[]> formParams, Map<String, String[]> queryParams) throws MiniAuthException
    {
        return generateAuthorizationString(transmissionType, authCredential, httpMethod, baseURI, authHeader, null);
    }

    @Override
    public String generateAuthorizationString(String transmissionType,
            Map<String, String> authCredential, String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> requestParams)
            throws MiniAuthException
    {
        // Note: transmissionType is ignored for Basic.
        //       It is always "header".
        String username = null;
        String password = null;
        if(authCredential != null) {
            if(authCredential.containsKey(AuthCredentialConstants.USERNAME)) {
                username = authCredential.get(AuthCredentialConstants.USERNAME);
            }
            if(authCredential.containsKey(AuthCredentialConstants.PASSWORD)) {
                password = authCredential.get(AuthCredentialConstants.PASSWORD);
            }
        }

        String paramString = BasicAuthorizationValueUtil.buildBasicAuthorizationValueString(username, password);
        
        String authString = AuthScheme.getAuthorizationHeaderAuthScheme(AuthScheme.BASIC) + " " + paramString;
        if(log.isLoggable(Level.FINER)) log.finer("authString = " + authString);
        return authString;
    }

}
