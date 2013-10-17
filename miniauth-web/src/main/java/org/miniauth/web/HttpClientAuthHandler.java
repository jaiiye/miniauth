package org.miniauth.web;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.miniauth.MiniAuthException;


// Client-side auth handler using Apache HttpClient...
public interface HttpClientAuthHandler extends ClientAuthHandler
{
    // client is an "in-out" param.
    boolean endorseRequest(Map<String, String> authCredential, HttpClient client) throws MiniAuthException, IOException;
}
