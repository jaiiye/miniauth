package org.miniauth.oauth.credential.mapper;

import org.miniauth.credential.mapper.CredentialMapper;


public interface OAuthTokenCredentialMapper extends CredentialMapper
{
    String getConsumerKey();
    String getConsumerSecret();
    String getTokenSecret(String accessToken);
    String putTokenSecret(String accessToken, String tokenSecret);
}
