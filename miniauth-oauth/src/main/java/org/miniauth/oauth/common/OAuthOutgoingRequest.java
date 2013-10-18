package org.miniauth.oauth.common;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.common.RequestBase;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.oauth.service.OAuthRequestUtil;


/**
 * Client side request, which is being built by the client, for services that require OAuth.
 * Note that OAuthOutgoingRequest does not represent a full request.
 * It's just a partial wrapper for attributes that are needed for OAuth signing.
 */
public class OAuthOutgoingRequest extends OutgoingRequest
{
    private static final long serialVersionUID = 1L;

    // State variables.
    private boolean endorsed = false;
    
    // OAuth parameter wrapper. "Cache".
    // This can be generated based on the internal attributes.
    // And, it can be overridden by a setter (endorse()), which can make it "out of sync" with the itnernal attributes.
    private volatile OAuthParamMap oauthParamMap = null;
    // private AccessIdentity accessIdentity = null;    // Just use oauthParamMap.accessIdentity


    // Note that Ctor's are not public.
    // Use the builder class to create OAuthOutgoingRequest objects.
    protected OAuthOutgoingRequest()
    {
        super();
    }
    protected OAuthOutgoingRequest(String httpMethod, URI baseURI)
    {
        super(httpMethod, baseURI);
    }
    protected OAuthOutgoingRequest(String httpMethod, URI baseURI,
            Map<String, String> authHeader, Map<String, String[]> formParams,
            Map<String, String[]> queryParams)
    {
        super(httpMethod, baseURI, authHeader, formParams, queryParams);
    }
    protected OAuthOutgoingRequest(RequestBase request)
    {
        super(request);
    }

    
    // TBD: Who calls this method?
    // Build OAuthParamMap (excluding the signature)...
    public void buildOAuthParamMap() throws MiniAuthException
    {
        buildOAuthParamMap(null);
    }
    public void buildOAuthParamMap(AccessIdentity accessIdentity) throws MiniAuthException
    {
        oauthParamMap = OAuthRequestUtil.buildOAuthParams(this, accessIdentity);
        setReady(true);
    }
    public OAuthParamMap getOauthParamMap()
    {
        return oauthParamMap;
    }
    
    // The arg oauthParamMap should contain the generated signature
    //     as well as other required oauth_x params.
    public void endorse(OAuthParamMap oauthParamMap) throws MiniAuthException
    {
        // TBD: What about other OAuth required params ???
        if(oauthParamMap == null || ! oauthParamMap.isSignatureSet()) {
            // Can this happen???
            // If we have failed to generate a signature, 
            //    we should have thrown exception earlier in the call chain.
            throw new AuthSignatureException("Signature is not set. The request cannot be endorsed.");
        }
        if(this.oauthParamMap == null) {
            this.oauthParamMap = new OAuthParamMap(oauthParamMap);
        } else {
            this.oauthParamMap.updateParams(oauthParamMap);
        }
        
        // TBD:
        // Now, we need to update the internal vars based on the new oauthParamMap
        // ....
        
        
        
        
        setEndorsed(true);
    }


    /**
     * Returns true if this request has been "endorsed"
     *    (e.g., if it includes the oauth_signature param in the case of OAuth, etc.).
     * Note that the request can be endorsed only if it is "ready".
     * Also, once it's endorsed, it cannot be endorsed again or it cannot be changed.
     * @return the "endorsement" state of this request.
     */
    @Override
    public boolean isEndorsed()
    {
        return this.endorsed;
    }
    protected void setEndorsed(boolean endorsed)
    {
        this.endorsed = endorsed;
    }

    
    
}
