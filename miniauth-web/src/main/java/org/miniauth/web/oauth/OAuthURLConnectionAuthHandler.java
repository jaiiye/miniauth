package org.miniauth.web.oauth;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.BaseURIInfo;
import org.miniauth.core.HttpMethod;
import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.InternalErrorException;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.exception.InvalidStateException;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.oauth.common.OAuthOutgoingRequestBuilder;
import org.miniauth.oauth.credential.mapper.OAuthCredentialMapper;
import org.miniauth.oauth.service.OAuthEndorserService;
import org.miniauth.web.URLConnectionAuthHandler;
import org.miniauth.web.oauth.util.OAuthURLConnectionUtil;
import org.miniauth.web.util.URLConnectionUtil;


// TBD:
// URL?
// URLConnection?
// HttpClient?
// ....
// Probably, the best way is to create wrappers on these objects????
// ....
// Note:
// This class has not been fully implemented....
// .....
public class OAuthURLConnectionAuthHandler extends OAuthAuthHandler implements URLConnectionAuthHandler
{
    private static final Logger log = Logger.getLogger(OAuthURLConnectionAuthHandler.class.getName());

    // TBD: Is it safe to reuse this???
//    private final AuthStringBuilder authStringBuilder;
//    private final SignatureGenerator signatureGenerator;
    private final OAuthEndorserService endorserService;

    public OAuthURLConnectionAuthHandler(OAuthCredentialMapper credentialMapper)
    {
        super(credentialMapper);
//        authStringBuilder = new OAuthAuthStringBuilder();
//        signatureGenerator = ((OAuthAuthStringBuilder) authStringBuilder).getOAuthSignatureGenerator();  // ???
        endorserService = new OAuthEndorserService(getOAuthCredentialMapper());
    }
    
    // Note that "signing" is the last step.
    // Once the request is signed, it cannot be modified.
    private boolean isEndorsed(HttpURLConnection conn) throws MiniAuthException, IOException
    {
        // TBD: ...
        // return OAuthURLConnectionUtil.isOAuthParamPresent(conn);
        return OAuthURLConnectionUtil.isOAuthSignaturePresent(conn);
    }

    /**
     * "Signs" or "endorses" the request.
     * In the context of OAuth, this method should be really called signRequest().
     * However, we (plan to) use this for other auth schemes,
     *   hence we use a more generic (but somewhat unusual name), "endorse".
     * @param accessToken Auth token needed for making a request. 
     * @param conn URLConnection. conn is an "in-out" param.
     * @return true if "endorsing" is successful.
     * @throws MiniAuthException
     * @throws IOException
     */
    @Override
    public boolean endorseRequest(String accessToken, HttpURLConnection conn) throws MiniAuthException, IOException
    {
        // Note:
        // At this point, conn should not contain any oauth_x parameters.
        // .... 
        // This seems to call conn.connect() for some reason...
        // which prevents us from subsequently writing auth headers, etc.
//        if(isEndorsed(conn)) {
//            // return false;
//            throw new InvalidStateException("Request already endorsed.");
//        }
        // ...
        // 
        // TBD:
        // We can use any existing oauth_ parameters already included in conn
        //   and fill in what's missing including signature.
        // For now, we do not support such scenario.
        // ...
        // boolean oauthParamIncluded = OAuthServletRequestUtil.isOAuthParamPresent(conn);
        // if(transmissionType == null) {
        //     transmissionType = OAuthServletRequestUtil.getOAuthParamTransmissionType(conn);
        // }
//        if(transmissionType == null) {
//            // We always use the header type if it's not already set...
//            transmissionType = ParameterTransmissionType.HEADER;
//        } 
        // else validate ????
       
        
        AccessIdentity accessIdentity = getOAuthCredentialMapper().getAccessIdentity(accessToken);
        // log.warning(">>>>>>>>>>>>>>>>>>>>>>>>>>> accessIdentity = " + accessIdentity);
//        AccessCredential accessCredential = getOAuthCredentialMapper().getAccesssCredential(accessIdentity);
//        log.warning(">>>>>>>>>>>>>>>>>>>>>>>>>>> accessCredential = " + accessCredential);


        // ...

        String httpMethod = conn.getRequestMethod();
        
        // TBD: Exclude query params...
        URL url = conn.getURL();
        if(url == null) {
            throw new InvalidStateException("Request URL is not set.");
        }
        URI baseURI = BaseURIInfo.createBaseURI(url);
       
        // ???
        Map<String,String> authHeader = OAuthURLConnectionUtil.getAuthParams(conn);
        // Map<String,String[]> requestParams = URLConnectionUtil.getRequestParams(conn);  // ???
        // Map<String,String[]> formParams = URLConnectionUtil.getFormParams(conn);        // ???
        Map<String,String[]> formParams = null;        // ???
        Map<String,String[]> queryParams = URLConnectionUtil.getQueryParams(conn);      // ???
        
        // ...
        
        
  
        
//        // Add oauth_X params...
//        switch(transmissionType) {
//        case ParameterTransmissionType.HEADER:
//            // note that autheHeader should be null at this point (or, maybe an empty map?)
//            // ...
//            authHeader = OAuthParamUtil.buildNewOAuthHeaderMap(accessCredential);
//            
//            break;
//        default:
//            
//            // TBD:
//            // This makes sense only if the current form/url params is null (e.g., body is not set), etc...
//            // ????
//            
//            // Not implemented...
//            throw new InternalErrorException("Not supported/implemented transmissionType: " + transmissionType);
//            // ....
//        }
//        
//        
//        String authString = authStringBuilder.generateAuthorizationString(transmissionType, authCredential, httpMethod, baseURI, authHeader, requestParams);
//
//        
//        // add oauth_X params including the signature to conn.
//        //
//        switch(transmissionType) {
//        case ParameterTransmissionType.HEADER:
//            // ...
//            // ???
//            // add a header to the conn
//            conn.addRequestProperty("Authorization", authString);
//
//            break;
//        default:
//            // TBD:
//            // signatureGenerator.generateOAuthParamMap(credential, httpMethod, uriInfo, authHeader, formParams, queryParams);
//            
//            // Not implemented...
//            throw new InternalErrorException("Not supported/implemented transmissionType: " + transmissionType);
//            // ....
//        }

        // Note that the order of chained methods is important.
        // Later methods overwrites the previously set value, if they affect the same internal variables.
        OAuthOutgoingRequest outgoingRequest = new OAuthOutgoingRequestBuilder().setHttpMethod(httpMethod).setBaseURI(baseURI).setAuthHeader(authHeader).setFormParams(formParams).setQueryParams(queryParams).setAccessIdentity(accessIdentity).build();
        
        boolean endorsed = endorserService.endorse(outgoingRequest);
        if(endorsed == false) {
            throw new InternalErrorException("Failed to endorse the request due to unknown errors.");
        }
        
        
        String transmissionType = outgoingRequest.getAuthParamTransmissionType();
        switch(transmissionType) {
        case ParameterTransmissionType.HEADER:
            String authHeaderStr = outgoingRequest.getAuthHeaderAuthorizationString();
            conn.setRequestProperty("Authorization", authHeaderStr);
            break;
        case ParameterTransmissionType.QUERY:
            // ????
//            String newQueryString = request.getQueryParamString();
//            URL newURL = BaseURIInfo.createURL(baseURI, newQueryString);
//            conn.setURL(newURL);   // ?????
//            break;
            throw new InvalidInputException("Query string cannot be used to include the OAuth signature. ");
        case ParameterTransmissionType.FORM:
            // Does this really make sense??
            conn.setRequestMethod(HttpMethod.POST);
            conn.setDoOutput(true);   // This works with POST/PUT but not with GET....
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // String formBody = FormParamUtil.buildUrlEncodedFormParamString(getFormParams());
            String formBody = outgoingRequest.getFormParamString();

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(formBody);
            // ...
            break;
        default:
            throw new InternalErrorException("Not supported/implemented transmissionType: " + transmissionType);
        }

        return endorsed;
    }
    
    
    
    
}
