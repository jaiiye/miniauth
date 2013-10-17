package org.miniauth.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import org.miniauth.MiniAuthException;


/**
 * Client-side auth handler using HttpURLConnection...
 */
public interface URLConnectionAuthHandler extends ClientAuthHandler
{

    /**
     * "Signs" or "endorses" the request.
     * In the context of OAuth, this method should be really called signRequest().
     * However, we (plan to) use this for other auth schemes,
     *   hence we use a more generic (but somewhat unusual name), "endorse".
     * @param authCredential Auth credential needed for making a request. 
     * @param conn URLConnection. conn is an "in-out" param.
     * @return true if "endorsing" is successufl.
     * @throws MiniAuthException
     * @throws IOException
     */
    boolean endorseRequest(Map<String, String> authCredential, HttpURLConnection conn) throws MiniAuthException, IOException;
}
