package org.miniauth.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import org.miniauth.MiniAuthException;


// Client-side auth handler using HttpURLConnection...
public interface URLConnectionAuthHandler extends ClientAuthHandler
{
    // In the context of OAuth, this should be really called signRequest().
    // However, we (plan to) use this for other auth schemes,
    //   hence we use a more generic (but somewhat unusual name), "endorse".

    // conn is an "in-out" param.
    boolean endorseRequest(Map<String, String> authCredential, HttpURLConnection conn) throws MiniAuthException, IOException;
}
