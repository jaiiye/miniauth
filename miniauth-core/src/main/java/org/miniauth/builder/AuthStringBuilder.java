package org.miniauth.builder;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;


/**
 * Defines a class for generating "auth string",
 * a string that is used after "Authorization: " in the auth header.
 * Or, it can be a &-concatenated URL encoded query/form string depending on the transmissionType.
 * Note that, in general, unless transmissionType==header, using SignatureGeneraor.generateOAuthParamMap() is more convenient to use.
 */
public interface AuthStringBuilder
{
    // request params contain all necessary auth params (regardless of where (header, query, form) they are)
    //    as well as all query params (and single part url-encoded form params?).
    String generateAuthorizationString(String transmissionType, Map<String,String> authCredential, String httpMethod, URI baseURI, Map<String,String> authHeader, Map<String,String[]> formParams, Map<String,String[]> queryParams) throws MiniAuthException;
    String generateAuthorizationString(String transmissionType, Map<String,String> authCredential, String httpMethod, URI baseURI, Map<String,String> authHeader, Map<String,String[]> requestParams) throws MiniAuthException;

}
