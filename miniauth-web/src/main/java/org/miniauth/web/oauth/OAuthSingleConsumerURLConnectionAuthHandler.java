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
import org.miniauth.exception.InternalErrorException;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.exception.InvalidStateException;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.oauth.common.OAuthOutgoingRequestBuilder;
import org.miniauth.oauth.credential.mapper.OAuthConsumerCredentialMapper;
import org.miniauth.oauth.credential.mapper.OAuthSingleConsumerCredentialMapper;
import org.miniauth.oauth.service.OAuthConsumerEndorserService;
import org.miniauth.web.SingleConsumerURLConnectionAuthHandler;
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
public class OAuthSingleConsumerURLConnectionAuthHandler extends OAuthAuthHandler implements SingleConsumerURLConnectionAuthHandler
{
    private static final Logger log = Logger.getLogger(OAuthSingleConsumerURLConnectionAuthHandler.class.getName());

    // TBD: Is it safe to reuse this???
//    private final AuthStringBuilder authStringBuilder;
//    private final SignatureGenerator signatureGenerator;
    private final OAuthConsumerEndorserService endorserService;

    public OAuthSingleConsumerURLConnectionAuthHandler(String consumerKey, String consumerSecret)
    {
        this(new OAuthSingleConsumerCredentialMapper(consumerKey, consumerSecret));
    }
    public OAuthSingleConsumerURLConnectionAuthHandler(OAuthSingleConsumerCredentialMapper credentialMapper)
    {
        super(credentialMapper);
//        authStringBuilder = new OAuthAuthStringBuilder();
//        signatureGenerator = ((OAuthAuthStringBuilder) authStringBuilder).getOAuthSignatureGenerator();  // ???
        
        endorserService = new OAuthConsumerEndorserService(getOAuthConsumerCredentialMapper());
    }

    public OAuthConsumerCredentialMapper getOAuthConsumerCredentialMapper()
    {
        return (OAuthConsumerCredentialMapper) getOAuthCredentialMapper();
    }
    public OAuthSingleConsumerCredentialMapper getOAuthSingleConsumerCredentialMapper()
    {
        return (OAuthSingleConsumerCredentialMapper) getOAuthCredentialMapper();
    }

    
    // TBD:...
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
     * @param conn URLConnection. conn is an "in-out" param.
     * @return true if "endorsing" is successful.
     * @throws MiniAuthException
     * @throws IOException
     */
    @Override
    public boolean endorseRequest(HttpURLConnection conn) throws MiniAuthException, IOException
    {
        // Note:
        // At this point, conn should not contain any oauth_x parameters.
        // .... 
        
        String consumerKey = getOAuthSingleConsumerCredentialMapper().getConsumerKey();
        
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
        
        // Note that the order of chained methods is important.
        // Later methods overwrites the previously set value, if they affect the same internal variables.
        OAuthOutgoingRequest outgoingRequest = new OAuthOutgoingRequestBuilder().setHttpMethod(httpMethod).setBaseURI(baseURI).setAuthHeader(authHeader).setFormParams(formParams).setQueryParams(queryParams).setConsumerKey(consumerKey).build();
        
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
