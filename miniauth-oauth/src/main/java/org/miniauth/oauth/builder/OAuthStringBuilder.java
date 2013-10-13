package org.miniauth.oauth.builder;

import java.net.URI;
import java.util.Map;

import org.miniauth.builder.AuthStringBuilder;


public class OAuthStringBuilder implements AuthStringBuilder
{

    @Override
    public String generateAuthorizationString(
            Map<String, String> authCredential, String httpMethod, URI baseURI,
            Map<String, String[]> requestParams)
    {
        // TBD
        return null;
    }

}
