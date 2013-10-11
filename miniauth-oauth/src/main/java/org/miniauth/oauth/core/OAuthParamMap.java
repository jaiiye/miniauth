package org.miniauth.oauth.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;


public final class OAuthParamMap implements Serializable
{
    private static final Logger log = Logger.getLogger(OAuthParamMap.class.getName());
    private static final long serialVersionUID = 1L;

//    private String consumerKey;
//    private String token;
//    private String signature;
//    private String signatureMethod;
//    private String nonce;
//    private int timestamp;
//    private String version;

    // Somewhat unusual "bean" implementation.
    // We use a map instead of individual fields.
    private final Map<String,Object> paramMap;

    public OAuthParamMap()
    {
        paramMap = new HashMap<>();
    }

    //////////////////////////////////////////////////////
    // "Bean" interface
    
    public String getConsumerKey()
    {
        return (String) paramMap.get(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY);
    }
    public void setConsumerKey(String consumerKey)
    {
        paramMap.put(OAuthConstants.PARAM_OAUTH_CONSUMER_KEY, consumerKey);
    }

    public String getToken()
    {
        return (String) paramMap.get(OAuthConstants.PARAM_OAUTH_TOKEN);
    }
    public void setToken(String token)
    {
        paramMap.put(OAuthConstants.PARAM_OAUTH_TOKEN, token);
    }

    public String getSignature()
    {
        return (String) paramMap.get(OAuthConstants.PARAM_OAUTH_SIGNATURE);
    }
    public void setSignature(String signature)
    {
        paramMap.put(OAuthConstants.PARAM_OAUTH_SIGNATURE, signature);
    }

    public String getSignatureMethod()
    {
        return (String) paramMap.get(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD);
    }
    public void setSignatureMethod(String signatureMethod)
    {
        paramMap.put(OAuthConstants.PARAM_OAUTH_SIGNATURE_METHOD, signatureMethod);
    }

    public String getNonce()
    {
        return (String) paramMap.get(OAuthConstants.PARAM_OAUTH_NONCE);
    }
    public void setNonce(String nonce)
    {
        paramMap.put(OAuthConstants.PARAM_OAUTH_NONCE, nonce);
    }

    public int getTimestamp()
    {
        Integer timestamp = (Integer) paramMap.get(OAuthConstants.PARAM_OAUTH_TIMESTAMP);
        if(timestamp != null) {
            return timestamp;
        } else {
            return 0;   // ????
        }
    }
    public void setTimestamp(int timestamp)
    {
        paramMap.put(OAuthConstants.PARAM_OAUTH_TIMESTAMP, timestamp);
    }

    public String getVersion()
    {
        return (String) paramMap.get(OAuthConstants.PARAM_OAUTH_VERSION);
    }
    public void setVersion(String version)
    {
        paramMap.put(OAuthConstants.PARAM_OAUTH_VERSION, version);
    }


    //////////////////////////////////////////////////////
    // Map interface...

    public boolean containsKey(Object key)
    {
        return paramMap.containsKey(key);
    }

    public Object get(Object key)
    {
        return paramMap.get(key);
    }

    public Object put(String key, Object value)
    {
        return paramMap.put(key, value);
    }

    public Object remove(Object key)
    {
        return paramMap.remove(key);
    }

    public Set<String> keySet()
    {
        return paramMap.keySet();
    }

    public Set<java.util.Map.Entry<String, Object>> entrySet()
    {
        return paramMap.entrySet();
    }


    // For debugging...
    @Override
    public String toString()
    {
        return "OAuthParamMap [paramMap=" + paramMap + "]";
    }


}
