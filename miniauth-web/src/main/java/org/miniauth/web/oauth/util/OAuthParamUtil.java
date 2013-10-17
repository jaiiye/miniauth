package org.miniauth.web.oauth.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.core.AuthScheme;
import org.miniauth.credential.AuthCredentialConstants;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.signature.OAuthSignatureUtil;
import org.miniauth.oauth.util.ParameterTransmissionUtil;
import org.miniauth.util.AuthHeaderUtil;
import org.miniauth.web.util.ServletRequestUtil;


public final class OAuthParamUtil
{
    private static final Logger log = Logger.getLogger(OAuthParamUtil.class.getName());

    private OAuthParamUtil() {}

    
    public static Map<String,String> buildNewOAuthHeaderMap(Map<String, String> authCredential)
    {
        Map<String,String> authHeader = new HashMap<>();
        
        authHeader.put(OAuthConstants.PARAM_OAUTH_VERSION, OAuthConstants.OAUTH_VERSION_STRING);
        authHeader.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, SignatureMethod.HMAC_SHA1);
        if(authCredential != null) {
            if(authCredential.containsKey(AuthCredentialConstants.CONSUMER_KEY)) {
                authHeader.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, authCredential.get(AuthCredentialConstants.CONSUMER_KEY));
            }
            if(authCredential.containsKey(AuthCredentialConstants.ACCESS_TOKEN)) {
                authHeader.put(OAuthConstants.PARAM_OAUTH_TOKEN, authCredential.get(AuthCredentialConstants.ACCESS_TOKEN));
            }
        }
        // etc..

        return authHeader;
    }

}
