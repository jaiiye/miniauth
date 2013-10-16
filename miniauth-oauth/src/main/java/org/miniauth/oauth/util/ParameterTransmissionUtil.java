package org.miniauth.oauth.util;

import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.core.AuthScheme;
import org.miniauth.core.HttpHeader;
import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.exception.BadRequestException;
import org.miniauth.exception.UnauthorizedException;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.util.AuthHeaderUtil;


public final class ParameterTransmissionUtil
{
    private static final Logger log = Logger.getLogger(ParameterTransmissionUtil.class.getName());

    private ParameterTransmissionUtil() {}

    
    public static boolean isTransmissionTypeValid(String type)
    {
        if(ParameterTransmissionType.HEADER.equals(type) 
                || ParameterTransmissionType.FORM.equals(type)
                || ParameterTransmissionType.QUERY.equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    // temporary
    public static String getDefaultTransmissionType()
    {
        return ParameterTransmissionType.HEADER;
    }
    

    // Note:
    // Oauth params should be in one and only one of the param set {oauth headers, form params, query params}.
    // ....
    
    public static String getOAuthParameterTransmissionType(Map<String,String> header, Map<String,String[]> form, Map<String,String[]> query) throws MiniAuthException 
    {
        if(header != null && header.containsKey(HttpHeader.AUTHORIZATION) ) {  // TBD: what if the auth header is not oauth????
            String authScheme = AuthHeaderUtil.getAuthScheme(header);
            if(AuthScheme.OAUTH.equals(authScheme)) {
                return ParameterTransmissionType.HEADER;
            } else {
                // What does this mean???
                throw new BadRequestException("Invalid authorization header: authScheme = " + authScheme);
            }
        } else if(form != null && form.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD) ) {  // using an arbitrary oauth param.   // form-urlencoded ??? The caller should have checked this??? if it's not single-part & url-encoded form, the input "form" should be null????
            // temporary
            return ParameterTransmissionType.FORM;
        } else if(query != null && query.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD) ) {  // using an arbitrary oauth param.
            return ParameterTransmissionType.QUERY;
        }
        
        // ????
        // return null;
        // badrequestexception or unauthorizedexception ????
        throw new UnauthorizedException("Auth credential not found.");
    }

    public static String getTransmissionType(Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException 
    {
        if(authHeader != null && !authHeader.isEmpty()) {
            return ParameterTransmissionType.HEADER;
        } else if(formParams != null && formParams.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD) ) {  // using an arbitrary oauth param.   // form-urlencoded ??? The caller should have checked this??? if it's not single-part & url-encoded form, the input "form" should be null????
            // temporary
            return ParameterTransmissionType.FORM;
        } else if(queryParams != null && queryParams.containsKey(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD) ) {  // using an arbitrary oauth param.
            return ParameterTransmissionType.QUERY;
        }
        
        // ????
        // return null;
        // badrequestexception or unauthorizedexception ????
        throw new UnauthorizedException("Auth credential not found.");
    }

}
