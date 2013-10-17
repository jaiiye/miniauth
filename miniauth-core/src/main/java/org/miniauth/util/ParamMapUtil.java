package org.miniauth.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.miniauth.exception.ValidationException;


/**
 * We mainly use three types are maps in handling query/form parameters:
 *    Map<Stirng,String>
 *    Map<String,String[]> (or, Map<String,List<String>)
 *    Map<String,Object>
 * These are all related, but unfortunately, depending on the context, one form is more convenient than others.
 * Note: The original request parameters can always be represented as Map<String,String[]> 
 *       since a param can have multiple values.
 *       Once we assume the value is (or, should be) unique, we can use Map<String,String>.
 *       The value has an intrinsic type (e.g., int, or a serialized object, etc.), 
 *           and hence Map<String,String> can be ultimately "cast" to Map<String,Object>.
 * This class defines "conversion" routines among these different Map types.
 * TBD: We need to be more consistent in the use of these maps and reduce the need for these "conversions".
*/
public final class ParamMapUtil
{
    private static final Logger log = Logger.getLogger(ParamMapUtil.class.getName());

    private ParamMapUtil() {}

    // This works only if each param is known/required to have a single value. 
    // Throws ValidationException if this condition is not met.
    public static Map<String,Object> convertStringArrayMapToObjectValueMap(Map<String,String[]> inputMap) throws ValidationException
    {
        if(inputMap == null) {
            return null;
        }
        Map<String,Object> outputMap = new HashMap<>();
        for(String key : inputMap.keySet()) {
            String[] vals = inputMap.get(key);
            Object val = null;
            if(vals == null || vals.length == 0) {
                val = null;
            } else {
                if(vals.length > 1) {
                    throw new ValidationException("Multiple values are set for key = " + key);
                } else {
                    val = vals[0];
                }
            }
            outputMap.put(key, val);
        }
        return outputMap;
    }

    public static Map<String,Object> convertStringListMapToObjectValueMap(Map<String,List<String>> inputMap) throws ValidationException
    {
        if(inputMap == null) {
            return null;
        }
        Map<String,Object> outputMap = new HashMap<>();
        for(String key : inputMap.keySet()) {
            List<String> vals = inputMap.get(key);
            Object val = null;
            if(vals == null || vals.isEmpty()) {
                val = null;
            } else {
                if(vals.size() > 1) {
                    throw new ValidationException("Multiple values are set for key = " + key);
                } else {
                    val = vals.get(0);
                }
            }
            outputMap.put(key, val);
        }
        return outputMap;
    }

    public static Map<String,String[]> convertStringListMapToStringArrayMap(Map<String,List<String>> inputMap) throws ValidationException
    {
        if(inputMap == null) {
            return null;
        }
        Map<String,String[]> outputMap = new HashMap<>();
        for(String key : inputMap.keySet()) {
            List<String> vals = inputMap.get(key);
            String[] val = null;
            if(vals == null || vals.isEmpty()) {
                val = null;
            } else {
                if(vals.size() > 1) {
                    throw new ValidationException("Multiple values are set for key = " + key);
                } else {
                    val = new String[]{vals.get(0)};
                }
            }
            outputMap.put(key, val);
        }
        return outputMap;
    }

    public static Map<String,String[]> convertObjectValueMapToStringArrayMap(Map<String,Object> inputMap) throws ValidationException
    {
        if(inputMap == null) {
            return null;
        }
        Map<String,String[]> outputMap = new HashMap<>();
        for(String key : inputMap.keySet()) {
            Object obj = inputMap.get(key);
            String[] val = null;
            if(obj == null) {
                val = null;
            } else {
                try {
                    val = new String[]{obj.toString()};
                } catch(Exception e) {
                    throw new ValidationException("Failed to convert to String[] for key = " + key, e);                    
                }
                
            }
            outputMap.put(key, val);
        }
        return outputMap;
    }
 

    public static Map<String,String> convertObjectValueMapToStringValueMap(Map<String,Object> inputMap) throws ValidationException
    {
        if(inputMap == null) {
            return null;
        }
        Map<String,String> outputMap = new HashMap<>();
        for(String key : inputMap.keySet()) {
            Object obj = inputMap.get(key);
            String val = null;
            if(obj == null) {
                val = null;
            } else {
                try {
                    val = obj.toString();
                } catch(Exception e) {
                    throw new ValidationException("Failed to convert the value to String for key = " + key, e);                    
                }
                
            }
            outputMap.put(key, val);
        }
        return outputMap;
    }
 

}
