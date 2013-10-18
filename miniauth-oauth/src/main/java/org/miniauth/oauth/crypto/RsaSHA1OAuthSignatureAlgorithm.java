package org.miniauth.oauth.crypto;

import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessCredential;
import org.miniauth.crypto.RsaSHA1SignatureAlgorithm;
import org.miniauth.crypto.SignatureAlgorithm;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.util.OAuthSignatureUtil;
import org.miniauth.util.ParamMapUtil;


/**
 * RSA-SHA1 signature implementation of OAuthSignatureAlgorithm.
 */
public class RsaSHA1OAuthSignatureAlgorithm extends AbstractOAuthSignatureAlgorithm implements OAuthSignatureAlgorithm
{

    public RsaSHA1OAuthSignatureAlgorithm()
    {
        SignatureAlgorithm algorithm = new RsaSHA1SignatureAlgorithm();
        setSignatureAlgorithm(algorithm);
    }

    @Override
    protected String buildKeyString(AccessCredential credential) throws InvalidCredentialException
    {
        String consumerSecret = credential.getConsumerSecret();
        // String tokenSecret = credential.getTokenSecret();
        
        return consumerSecret;
    }

    @Override
    public String generate(String text, AccessCredential credential) throws AuthSignatureException, InvalidCredentialException
    {
        String key = buildKeyString(credential);
        return getSignatureAlgorithm().generate(text, key);
    }

    @Override
    public OAuthParamMap generateOAuthParamMap(String text,
            AccessCredential credential, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams)
            throws MiniAuthException
    {
        Map<String,String[]> requestParams = OAuthSignatureUtil.mergeRequestParameters(formParams, queryParams);
        return generateOAuthParamMap(text, credential, authHeader, requestParams);
    }

    @Override
    public OAuthParamMap generateOAuthParamMap(String text,
            AccessCredential credential, Map<String, String> authHeader,
            Map<String, String[]> requestParams) throws MiniAuthException
    {
        Map<String,String> oauthParams = OAuthSignatureUtil.getOAuthParams(authHeader, requestParams);
        OAuthParamMap oauthParamMap = new OAuthParamMap(oauthParams);
        
        String signature = generate(text, credential);
        oauthParamMap.setSignature(signature);

        return oauthParamMap;
    }
 
    @Override
    public boolean verify(String text, AccessCredential credential, String signature) throws AuthSignatureException, InvalidCredentialException
    {
        String key = buildKeyString(credential);
        return getSignatureAlgorithm().verify(text, key, signature);
    }
   
    
}
