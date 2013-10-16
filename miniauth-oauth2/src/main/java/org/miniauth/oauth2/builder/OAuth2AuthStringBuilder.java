package org.miniauth.oauth2.builder;


import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.builder.AuthStringBuilder;
import org.miniauth.core.AuthScheme;
import org.miniauth.credential.AuthCredentialConstants;
import org.miniauth.exception.InvalidCredentialException;


public class OAuth2AuthStringBuilder implements AuthStringBuilder
{
    private static final Logger log = Logger.getLogger(OAuth2AuthStringBuilder.class.getName());

    public OAuth2AuthStringBuilder()
    {
    }

    @Override
    public String generateAuthorizationString(
            String transmissionType, Map<String, String> authCredential, String httpMethod,
            URI baseURI, Map<String,String> authHeader, Map<String, String[]> formParams, Map<String, String[]> queryParams) throws MiniAuthException
    {
        // Note: transmissionType is ignored for OAuth2.
        //       It is always "header".
        String accessToken = null;
        if(authCredential != null && authCredential.containsKey(AuthCredentialConstants.ACCESS_TOKEN)) {
            accessToken = authCredential.get(AuthCredentialConstants.ACCESS_TOKEN);
        } else {
            // ???
            // TBD: check requestParams ???
            throw new InvalidCredentialException("Access token is not provided.");
        }
        String authString = AuthScheme.getAuthorizationHeaderAuthScheme(AuthScheme.OAUTH2) + " " + accessToken;
        if(log.isLoggable(Level.FINER)) log.finer("authString = " + authString);
        return authString;
    }

}
