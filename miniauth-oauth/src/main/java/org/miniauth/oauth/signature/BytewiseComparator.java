package org.miniauth.oauth.signature;

import java.util.Comparator;


/**
 * OAuth requires sorting param keys and values according to a byte order.
 *     (not in string order, in a certain locale).
 * BytewiseComparator implements byte-wise comparison.
 */
public class BytewiseComparator implements Comparator<String>
{

    public BytewiseComparator()
    {
    }

    @Override
    public int compare(String o1, String o2)
    {
        if(o1 == null && o2 == null) {
            return 0;
        } else if(o1 == null) {
            return -1;  // ???
        } else if(o2 == null) {
            return 1;   // ???
        }
        byte[] b1 = o1.getBytes();
        byte[] b2 = o2.getBytes();
        int len1 = b1.length;
        int len2 = b2.length;
        int length = Math.min(len1, len2);
        for(int i=0;i<length; i++) {
            if(b1[i] < b2[i]) {
                return -1;
            } else if(b1[i] > b2[i]) {
                return 1;
            } else {
                // continue;
            }
        }
        if(len1 < len2) {
            return -1;
        } else if(len1 > len2) {
            return 1;
        } else {
            return 0;
        }
    }

}
