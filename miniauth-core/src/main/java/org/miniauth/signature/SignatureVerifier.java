package org.miniauth.signature;

import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.core.BaseURIInfo;
import org.miniauth.credential.CredentialPair;


// This is primarily for OAuth v1.0a
// Other auth schemes do not require signature.
public interface SignatureVerifier
{
    // See the comment in SignatureGenerator regarding the reason why we have two versions of (almost identical) verify().
    boolean verify(CredentialPair credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException;
    boolean verify(CredentialPair credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException;

}
