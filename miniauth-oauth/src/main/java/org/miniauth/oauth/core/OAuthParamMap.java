package org.miniauth.oauth.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.oauth.credential.OAuthAccessIdentity;
import org.miniauth.oauth.util.OAuthAuthorizationHeaderUtil;
import org.miniauth.util.FormParamUtil;


/**
 * A "bean" class for encapsulating OAuth parameters.
 * Cf. http://tools.ietf.org/html/rfc5849#section-3.5.1
 */
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
    // (By using a map, we can accommodate some extra fields as well such as "realm", etc.)
    private final Map<String,Object> paramMap;

    public OAuthParamMap()
    {
        this(null);
    }
    public OAuthParamMap(Map<String,Object> paramMap)
    {
        if(paramMap == null) {
            paramMap = new HashMap<>();
        }
        // TBD: Validation???
        this.paramMap = paramMap;
    }
    
//    // TBD: How to make the returned map immutable???
//    public Map<String,Object> getParamMap()
//    {
//        return paramMap;
//    }


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
        // TBD: Validate ???
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


    //////////////////////////////////////////////////////
    // OAuth related convenience methods
    
    public boolean isSignatureSet()
    {
        String signature = getSignature();
        String signatureMethod = getSignatureMethod();
        return (SignatureMethod.isValid(signatureMethod) && (signature != null && !signature.isEmpty()));
    }

    public AccessIdentity getAccessIdentity()
    {
        String consumerKey = getConsumerKey();
        String accessToken = getToken();
        AccessIdentity accessIdentity = new OAuthAccessIdentity(consumerKey, accessToken);
        return accessIdentity;
    }
    public void setAccessIdentity(AccessIdentity accessIdentity)
    {
        if(accessIdentity != null) {
            String consumerKey = accessIdentity.getConsumerKey();
            String token = accessIdentity.getAccessToken();
            setConsumerKey(consumerKey);
            setToken(token);            
        } else {
            // ???
        }
    }
    
    public String buildUrlEncodedParamString() throws MiniAuthException
    {
        Map<String,String> params = new HashMap<>();
        for(String key : paramMap.keySet()) {
            Object val = paramMap.get(key);
            if(val != null) {
                params.put(key, val.toString());  // ???
            }
        }
        String paramString = OAuthAuthorizationHeaderUtil.buildOAuthAuthorizationValueString(params);
        // if(log.isLoggable(Level.FINER)) log.finer("paramString = " + paramString);
        return paramString;
    }


    // For debugging...
    @Override
    public String toString()
    {
        return "OAuthParamMap [paramMap=" + paramMap + "]";
    }


}
