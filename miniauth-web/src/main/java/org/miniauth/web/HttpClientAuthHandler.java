package org.miniauth.web;


// TBD:
// This should be moved to a separate module.
// ...

// Client-side auth handler using Apache HttpClient...
public interface HttpClientAuthHandler extends ClientAuthHandler
{
    // client is an "in-out" param.
    // boolean endorseRequest(Map<String, String> authCredential, HttpClient client) throws MiniAuthException, IOException;
}
