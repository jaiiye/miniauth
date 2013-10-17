package org.miniauth.web.oauth;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.builder.AuthStringBuilder;
import org.miniauth.core.ParameterTransmissionType;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.exception.InvalidStateException;
import org.miniauth.oauth.builder.OAuthAuthStringBuilder;
import org.miniauth.signature.SignatureGenerator;
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
// ....
public class OAuthClientAuthHandler extends OAuthAuthHandler implements URLConnectionAuthHandler
{

    // TBD: Is it safe to reuse this???
    private final AuthStringBuilder authStringBuilder;
    private final SignatureGenerator signatureGenerator;

    public OAuthClientAuthHandler()
    {
        authStringBuilder = new OAuthAuthStringBuilder();
        signatureGenerator = ((OAuthAuthStringBuilder) authStringBuilder).getOAuthSignatureGenerator();  // ???
    }
    
    // Note that "signing" is the last step.
    // Once the request is signed, it cannot be modified.
    public boolean isEndorsed(HttpURLConnection conn) throws MiniAuthException, IOException
    {
        // TBD: ...
        // return OAuthURLConnectionUtil.isOAuthParamPresent(conn);
        return OAuthURLConnectionUtil.isOAuthSignaturePresent(conn);
    }

    @Override
    public boolean endorseRequest(Map<String, String> authCredential, HttpURLConnection conn) throws MiniAuthException, IOException
    {
        return endorseRequest(authCredential, conn, null);
    }
    public boolean endorseRequest(Map<String, String> authCredential, HttpURLConnection conn, String transmissionType) throws MiniAuthException, IOException
    {
        // Note:
        // At this point, conn should not contain any oauth_x parameters.
        if(isEndorsed(conn)) {
            // return false;
            throw new InvalidStateException("Request already endorsed.");
        }
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
        if(transmissionType == null) {
            // We always use the header type if it's not already set...
            transmissionType = ParameterTransmissionType.HEADER;
        } 
        // else validate ????
       
        
        
        // ...
        
        URL url = conn.getURL();
        if(url == null) {
            throw new InvalidStateException("Request URL is not set.");
        }

        String httpMethod = conn.getRequestMethod();
        URI baseURI = null;
        try {
            baseURI = url.toURI();
        } catch (URISyntaxException e) {
            // ??? This cannot happen.
            throw new InvalidInputException("Invalid requestUrl = " + url.toString(), e);
        }

        // ???
        Map<String,String> authHeader = OAuthURLConnectionUtil.getAuthParams(conn);
        Map<String,String[]> requestParams = URLConnectionUtil.getRequestParams(conn);  // ???
        
        // ...
        
        
        // Add oauth_X params...
        switch(transmissionType) {
        case ParameterTransmissionType.HEADER:
            // note that autheHeader should be null at this point (or, maybe an empty map?)
            // ...
            authHeader = new HashMap<>();
            
            break;
            // ....
        }
        
        
        String authString = authStringBuilder.generateAuthorizationString(transmissionType, authCredential, httpMethod, baseURI, authHeader, requestParams);

        
        // add oauth_X params including the signature to conn.
        //
        switch(transmissionType) {
        case ParameterTransmissionType.HEADER:
            // ...
            // ???
            // add a header to the conn

            break;
            // ....
        }
        
        
        
        return false;
    }
    
    
    
    
}
