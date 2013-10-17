package org.miniauth.oauth.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.oauth.core.OAuthConstants;
import org.miniauth.oauth.core.SignatureMethod;
import org.miniauth.oauth.credential.OAuthAccessIdentity;
import org.miniauth.oauth.util.OAuthAuthorizationValueUtil;
import org.miniauth.oauth.util.ParameterTransmissionUtil;
import org.miniauth.util.ParamMapUtil;


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
    // The arg paramMap can be either Map<String,Object> or Map<String,String>.
    // If it is Map<String,String>, it should be converted to Map<String,Object>.
    public OAuthParamMap(Map<String, ? extends Object> paramMap)
    {
        this(paramMap, true);  // default true. ???
    }
    // if copyOAuthParamOnly is set to true, two things happen.
    // (1) we "clone" the input map (rather than just copying its reference).  --> Now we always clone....
    // (2) we filter out all non-OAuth params.
    public OAuthParamMap(Map<String, ? extends Object> paramMap, boolean copyOAuthParamOnly)
    {
//        if(paramMap == null) {
//            this.paramMap = new HashMap<>();
//        } else {
//            if(copyOAuthParamOnly) {
//                this.paramMap = new HashMap<>();
//                for(String p : OAuthConstants.getAllOAuthParams()) {
//                    Object val = paramMap.get(p);
//                    if(val != null) {
//                        this.paramMap.put(p, val);
//                    }
//                }
//            } else {
//                // TBD: Validation???
//                this.paramMap = (Map<String, Object>) paramMap;
//            }
//        }
        this.paramMap = new HashMap<>();
        if(paramMap != null) {
            if(copyOAuthParamOnly) {
                for(String p : OAuthConstants.getAllOAuthParams()) {
                    Object val = paramMap.get(p);
                    if(val != null) {
                        if(p.equals(OAuthConstants.PARAM_OAUTH_TIMESTAMP) && (val instanceof String)) {
                            try {
                                Integer ts = Integer.valueOf((String) val);
                                this.paramMap.put(p, ts);
                            } catch(Exception e) {
                                // Ignore.
                                log.log(Level.INFO, "Invalid type for timestamp: " + val, e);
                            }
                        } else {
                            this.paramMap.put(p, val);
                        }
                    }
                }
            } else {
                for(String k : paramMap.keySet()) {
                    Object val = paramMap.get(k);
                    if(val != null) {
                        if(k.equals(OAuthConstants.PARAM_OAUTH_TIMESTAMP) && (val instanceof String)) {
                            try {
                                Integer ts = Integer.valueOf((String) val);
                                this.paramMap.put(k, ts);
                            } catch(Exception e) {
                                // Ignore.
                                log.log(Level.INFO, "Invalid type for timestamp: " + val, e);
                            }
                        } else {
                            this.paramMap.put(k, val);
                        }
                    }
                }
            }
        }
    }
    
    // TBD: How to make the returned map immutable???
//    public Map<String,Object> getParamMap()
//    {
//        return paramMap;
//    }
    public Map<String,Object> getReadOnlyParamMap()
    {
        // Shallow copy...
        // This is not exactly "read only" in general since the caller can change the object referenced by the value.
        // But, in this case, the value "object" is only string or integer...
        // So, in effect, it's readonly....
        return new HashMap<>(this.paramMap);
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
        return buildUrlEncodedParamString(ParameterTransmissionUtil.getDefaultTransmissionType());
    }
    public String buildUrlEncodedParamString(String transmissionType) throws MiniAuthException
    {
        Map<String,String> params = ParamMapUtil.convertObjectValueMapToStringValueMap(paramMap);
        String paramString = OAuthAuthorizationValueUtil.buildOAuthAuthorizationValueString(params, transmissionType); 
        if(log.isLoggable(Level.FINER)) log.finer("paramString = " + paramString);
        return paramString;
    }


    // For debugging...
    @Override
    public String toString()
    {
        return "OAuthParamMap [paramMap=" + paramMap + "]";
    }


}
