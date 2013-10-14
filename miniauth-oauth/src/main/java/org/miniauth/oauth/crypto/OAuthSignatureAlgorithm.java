package org.miniauth.oauth.crypto;

import java.util.Map;

import org.miniauth.credential.AccessCredential;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.exception.ValidationException;
import org.miniauth.oauth.common.OAuthParamMap;


public interface OAuthSignatureAlgorithm
{
    // Embedded crypto "signature algorithm" ???
    // SignatureAlgorithm getSignatureAlgorithm();
    // String getSignatureAlgorithmName();

    // Generates a signature for the given plain text.
    String generate(String text, AccessCredential credential) throws AuthSignatureException, InvalidCredentialException;

    // The requestParams may include the params already included in the text (possibly).
    // This param is used to input oauth parmas so that OAuthParamMap can be properly populated
    //    (without having to parse the text even if the text possibly includes oauth params).
    OAuthParamMap generateOAuthParamMap(String text, AccessCredential credential, Map<String,String[]> requestParams) throws AuthSignatureException, InvalidCredentialException, ValidationException;

    // Returns true if the signature generated from the given text/credential matches the input signature.
    boolean verify(String text, AccessCredential credential, String signature) throws AuthSignatureException, InvalidCredentialException;
    
}
