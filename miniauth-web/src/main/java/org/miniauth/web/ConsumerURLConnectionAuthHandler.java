package org.miniauth.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import org.miniauth.MiniAuthException;


/**
 * Client-side auth handler using HttpURLConnection
 *    when the users do not require authcerendtial, such as in 2-Legged OAuth.
 */
public interface ConsumerURLConnectionAuthHandler extends ClientAuthHandler
{
    /**
     * "Signs" or "endorses" the request.
     * In the context of 2LO OAuth, this method should be really called signRequest().
     * However, we (plan to) use this for other auth schemes,
     *   hence we use a more generic (but somewhat unusual name), "endorse".
     * @param consumerKey. The consumer key to be used to sign the request.
     * @param conn URLConnection. conn is an "in-out" param.
     * @return true if "endorsing" is successful.
     * @throws MiniAuthException
     * @throws IOException
     */
    boolean endorseRequest(String consumerKey, HttpURLConnection conn) throws MiniAuthException, IOException;
}
