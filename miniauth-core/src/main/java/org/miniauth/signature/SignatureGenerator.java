package org.miniauth.signature;

import java.util.Map;

import org.miniauth.MiniAuthException;
import org.miniauth.core.BaseURIInfo;
import org.miniauth.credential.AccessCredential;


// This is primarily for OAuth v1.0a
// Other auth schemes do not require signature.
public interface SignatureGenerator
{
    // Why two versions of generate() ???
    // Javax.servlet APIs treat POST form params and query params (almost) equivalently.
    //    For example, ServletRequest.getParameterMap() returns the params regardless of where the params are found.
    // However, OAuth requires distinguishing these two types of parameters.
    // ...
    // Once the two param sets are merged, it is impossible to validate against errors like
    //  (1) oauth_X params cannot be put into more than one part.. e.g., if one oauth_x is in form param, and another oauth_y is in query param,
    //         then that is an error according to the (extremely messy) OAuth1 spec.
    //         However, once we merge these two param set we have no way to detect errors like this...
    //  etc...
    // ....
    // The client can use whichever version is more convenient, for now (since we don't do "full" validation).
    String generate(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException;
    String generate(AccessCredential credential, String httpMethod, BaseURIInfo uriInfo, Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException;

}
