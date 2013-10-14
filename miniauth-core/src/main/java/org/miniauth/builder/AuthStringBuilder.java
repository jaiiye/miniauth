package org.miniauth.builder;

import java.net.URI;
import java.util.Map;

import org.miniauth.MiniAuthException;


public interface AuthStringBuilder
{
    // The auth string after "Authorization: ".
    //  or, part of the query string, url-encoded form string, depending on the auth transmission type.
    // request params contain all auth params (regardless of where (header, query, form) they are)
    //    as well as all query params (and single part url-encoded form params?).
    // requestParams should contain necessary auth credentials as well (relevant to given auth method)????
    //    --> Or, just use AuthCredential
    String generateAuthorizationString(String transmissionType, Map<String,String> authCredential, String httpMethod, URI baseURI, Map<String,String[]> requestParams) throws MiniAuthException;

}
