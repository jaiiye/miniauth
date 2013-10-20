package org.miniauth.web.oauth;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.miniauth.MiniAuthException;
import org.miniauth.exception.InvalidInputException;
import org.miniauth.oauth.common.OAuthIncomingRequest;
import org.miniauth.oauth.common.OAuthIncomingRequestBuilder;
import org.miniauth.oauth.credential.mapper.OAuthCredentialMapper;
import org.miniauth.oauth.service.OAuthVerifierService;
import org.miniauth.web.ProviderAuthHandler;
import org.miniauth.web.oauth.util.OAuthServletRequestUtil;
import org.miniauth.web.util.ServletRequestUtil;


/**
 * OAuthAuthHandler implementation for provider side.
 */
public class OAuthProviderAuthHandler extends OAuthAuthHandler implements ProviderAuthHandler
{
    private static final Logger log = Logger.getLogger(OAuthProviderAuthHandler.class.getName());

    // TBD: Is it safe to reuse this???
//    private final SignatureVerifier signatureVerifier;
    private final OAuthVerifierService verifierService;
    
    public OAuthProviderAuthHandler(OAuthCredentialMapper credentialMapper)
    {
        super(credentialMapper);
//        signatureVerifier = new OAuthSignatureVerifier();
        verifierService = new OAuthVerifierService(getOAuthCredentialMapper());
    }

    
    /**
     * Verifies the request for auth.
     * @param request the servlet request object.
     * @return true if the request has valid authorization token/signature.
     * @throws MiniAuthException
     * @throws IOException
     */
    @Override
    public boolean verifyRequest(HttpServletRequest request) throws MiniAuthException, IOException
    {
        String httpMethod = request.getMethod();
        String requestUrl = null;
        URI baseURI = null;
        try {
            requestUrl = request.getRequestURL().toString();
            baseURI = new URI(requestUrl);
        } catch (Exception e) {
            // ??? This cannot happen.
            throw new InvalidInputException("Invalid requestUrl = " + requestUrl, e);
        }

        Map<String,String> authHeader = OAuthServletRequestUtil.getAuthParams(request);
        // Map<String,String[]> requestParams = request.getParameterMap();
        Map<String,String[]> formParams = ServletRequestUtil.getFormParams(request);
        Map<String,String[]> queryParams = ServletRequestUtil.getQueryParams(request);
        
        
//        // SignatureVerifier signatureVerifier = new OAuthSignatureVerifier();
//        boolean verified = signatureVerifier.verify(authCredential, httpMethod, baseUri, authHeader, requestParams);

        OAuthIncomingRequest incomingRequest = new OAuthIncomingRequestBuilder().setHttpMethod(httpMethod).setBaseURI(baseURI).setAuthHeader(authHeader).setFormParams(formParams).setQueryParams(queryParams).build();
        boolean verified = verifierService.verify(incomingRequest);
        
        if(log.isLoggable(Level.FINE)) log.fine("verified = " + verified);
        return verified;
    }


}
