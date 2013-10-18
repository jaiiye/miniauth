package org.miniauth.oauth.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.util.OAuthSignatureUtil;


public final class OAuthParamMapUtil
{
    private static final Logger log = Logger.getLogger(OAuthParamMapUtil.class.getName());

    private OAuthParamMapUtil() {};

    public static OAuthParamMap buildOAuthParams(OAuthOutgoingRequest request) throws MiniAuthException
    {
        return buildOAuthParams(request, null);
    }
    // If any value is found in both request and accessIdenty, the value in accessIdenty overwrites those in request.
    public static OAuthParamMap buildOAuthParams(OAuthOutgoingRequest request, AccessIdentity accessIdentity) throws MiniAuthException
    {
        OAuthParamMap oauthParamMap = null;
        if(request == null) {
            oauthParamMap = new OAuthParamMap();
        } else {
            oauthParamMap = new OAuthParamMap(OAuthSignatureUtil.getOAuthParams(request.getAuthHeader(), request.getFormParams(), request.getQueryParams()));
        }
        if(accessIdentity != null) {
            oauthParamMap.setAccessIdentity(accessIdentity);
        }
        String version = oauthParamMap.getVersion();
        if(version == null || version.isEmpty()) {
            oauthParamMap.setVersion(OAuthConstants.OAUTH_VERSION_STRING);
        }
        String signatureMethod = oauthParamMap.getSignatureMethod();
        if(signatureMethod == null || signatureMethod.isEmpty()) {
            oauthParamMap.setSignatureMethod(SignatureMethod.HMAC_SHA1);  // ???
        }
        // timestamp and nonce ???

        if(log.isLoggable(Level.FINER)) log.finer("oauthParamMap = " + oauthParamMap);
        return oauthParamMap;
    }

    public static OAuthParamMap buildOAuthParams(OAuthIncomingRequest request) throws MiniAuthException
    {
//        OAuthParamMap oauthParamMap = null;
//        if(request == null) {
//            oauthParamMap = new OAuthParamMap();
//        } else {
//            oauthParamMap = new OAuthParamMap(OAuthSignatureUtil.getOAuthParams(request.getAuthHeader(), request.getFormParams(), request.getQueryParams()));
//        }
//        String signature = oauthParamMap.getSignature();
//        if(signature == null || signature.isEmpty()) {
//            throw new BadRequestException("OAuth signature is missing in the request.");
//        }
        
        OAuthParamMap oauthParamMap = OAuthSignatureUtil.validateOAuthParams(request.getAuthHeader(), request.getFormParams(), request.getQueryParams(), true);

        if(log.isLoggable(Level.FINER)) log.finer("oauthParamMap = " + oauthParamMap);
        return oauthParamMap;
    }

}
