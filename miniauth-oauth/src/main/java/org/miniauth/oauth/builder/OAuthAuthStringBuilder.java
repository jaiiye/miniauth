package org.miniauth.oauth.builder;

import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.builder.AuthStringBuilder;
import org.miniauth.core.AuthScheme;
import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.oauth.common.OAuthParamMap;
import org.miniauth.oauth.signature.OAuthSignatureGenerator;
import org.miniauth.oauth.util.ParameterTransmissionUtil;


/**
 * AuthStringBuilder implementation for OAuth auth scheme.
 */
public class OAuthAuthStringBuilder implements AuthStringBuilder
{
    private static final Logger log = Logger.getLogger(OAuthAuthStringBuilder.class.getName());

    // Lazy initialized.
    private OAuthSignatureGenerator oauthSignatureGenerator = null;
    public OAuthSignatureGenerator getOAuthSignatureGenerator()
    {
        if(oauthSignatureGenerator == null) {
            oauthSignatureGenerator = new OAuthSignatureGenerator();
        }
        return oauthSignatureGenerator;
    }
    
    public OAuthAuthStringBuilder()
    {
    }
    

    @Override
    public String generateAuthorizationString(
            String transmissionType, Map<String, String> authCredential, String httpMethod,
            URI baseURI, Map<String,String> authHeader, Map<String, String[]> formParams, Map<String, String[]> queryParams) throws MiniAuthException
    {
//        String consumerSecret = null;
//        String tokenSecret = null;
//        if(authCredential != null) {
//            if(authCredential.containsKey(AuthCredentialConstants.CONSUMER_SECRET)) {
//                consumerSecret = authCredential.get(AuthCredentialConstants.CONSUMER_SECRET);
//            }
//            if(authCredential.containsKey(AuthCredentialConstants.TOKEN_SECRET)) {
//                tokenSecret = authCredential.get(AuthCredentialConstants.TOKEN_SECRET);
//            }
//        }
//        AccessCredential credential = new OAuthAccessCredential(consumerSecret, tokenSecret);
//        BaseURIInfo uriInfo = new BaseURIInfo(baseURI);
        Map<String,Object> oauthParameters = getOAuthSignatureGenerator().generateOAuthParamMap(authCredential, httpMethod, baseURI, authHeader, formParams, queryParams);
        OAuthParamMap oauthParamMap = new OAuthParamMap(oauthParameters);

        if(! ParameterTransmissionUtil.isTransmissionTypeValid(transmissionType)) {
            transmissionType = ParameterTransmissionUtil.getDefaultTransmissionType();
        }
        String paramString = oauthParamMap.buildUrlEncodedParamString(transmissionType);
        
        String authString = null;
        if(ParameterTransmissionType.HEADER.equals(transmissionType)) {
            authString = AuthScheme.getAuthorizationHeaderAuthScheme(AuthScheme.OAUTH) + " " + paramString;
        } else {
            authString = paramString;
        }

        if(log.isLoggable(Level.FINER)) log.finer("authString = " + authString);
        return authString;
    }

    @Override
    public String generateAuthorizationString(String transmissionType,
            Map<String, String> authCredential, String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> requestParams)
            throws MiniAuthException
    {
//        String consumerSecret = null;
//        String tokenSecret = null;
//        if(authCredential != null) {
//            if(authCredential.containsKey(AuthCredentialConstants.CONSUMER_SECRET)) {
//                consumerSecret = authCredential.get(AuthCredentialConstants.CONSUMER_SECRET);
//            }
//            if(authCredential.containsKey(AuthCredentialConstants.TOKEN_SECRET)) {
//                tokenSecret = authCredential.get(AuthCredentialConstants.TOKEN_SECRET);
//            }
//        }
//        AccessCredential credential = new OAuthAccessCredential(consumerSecret, tokenSecret);
//        BaseURIInfo uriInfo = new BaseURIInfo(baseURI);
        Map<String,Object> oauthParameters = getOAuthSignatureGenerator().generateOAuthParamMap(authCredential, httpMethod, baseURI, authHeader, requestParams);
        OAuthParamMap oauthParamMap = new OAuthParamMap(oauthParameters);

        if(! ParameterTransmissionUtil.isTransmissionTypeValid(transmissionType)) {
            transmissionType = ParameterTransmissionUtil.getDefaultTransmissionType();
        }
        String paramString = oauthParamMap.buildUrlEncodedParamString(transmissionType);
        
        String authString = null;
        if(ParameterTransmissionType.HEADER.equals(transmissionType)) {
            authString = AuthScheme.getAuthorizationHeaderAuthScheme(AuthScheme.OAUTH) + " " + paramString;
        } else {
            authString = paramString;
        }

        if(log.isLoggable(Level.FINER)) log.finer("authString = " + authString);
        return authString;
    }

}
