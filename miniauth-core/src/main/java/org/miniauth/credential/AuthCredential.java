package org.miniauth.credential;

import java.util.Map;


/**
 * "Marker" interface for all auth credentials.
 */
public interface AuthCredential
{
    // All authCredentials can be "converted" to "read only" Map<String,String>.
    Map<String,String> toReadOnlyMap();
}
