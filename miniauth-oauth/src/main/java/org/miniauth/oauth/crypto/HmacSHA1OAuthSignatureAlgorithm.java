package org.miniauth.oauth.crypto;

import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessCredential;
import org.miniauth.crypto.HmacSHA1SignatureAlgorithm;
import org.miniauth.crypto.SignatureAlgorithm;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.signature.OAuthSignatureUtil;


public class HmacSHA1OAuthSignatureAlgorithm extends AbstractOAuthSignatureAlgorithm implements OAuthSignatureAlgorithm
{

    public HmacSHA1OAuthSignatureAlgorithm()
    {
        SignatureAlgorithm algorithm = new HmacSHA1SignatureAlgorithm();
        setSignatureAlgorithm(algorithm);
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
        Map<String,String> oauthParams = OAuthSignatureUtil.getOAuthParams(authHeader, formParams, queryParams);
        OAuthParamMap oauthParamMap = new OAuthParamMap(oauthParams);
        
        String signature = generate(text, credential);
        oauthParamMap.setSignature(signature);

        return oauthParamMap;
    }

}
