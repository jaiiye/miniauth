package org.miniauth.oauth2.util;

import java.util.logging.Logger;

import org.miniauth.core.ParameterTransmissionType;


public final class ParameterTransmissionUtil
{
    private static final Logger log = Logger.getLogger(ParameterTransmissionUtil.class.getName());

    private ParameterTransmissionUtil() {}

    
    public static boolean isTransmissionTypeValid(String type)
    {
        if(ParameterTransmissionType.HEADER.equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    // temporary
    public static String getDefaultTransmissionType()
    {
        return ParameterTransmissionType.HEADER;
    }
 

}
