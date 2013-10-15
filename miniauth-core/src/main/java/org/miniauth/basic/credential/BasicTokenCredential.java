package org.miniauth.basic.credential;

import java.io.Serializable;

import org.miniauth.credential.TokenCredential;


// For uname/pword based credential.
public final class BasicTokenCredential implements TokenCredential, Serializable
{
    private static final long serialVersionUID = 1L;

    private final String uname;
    private final String pword;

    public BasicTokenCredential(String uname, String pword)
    {
        super();
        this.uname = uname;
        this.pword = pword;
    }

    @Override
    public String getAccessToken()
    {
        return uname;
    }
//    public void setAccessToken(String uname)
//    {
//        this.uname = uname;
//    }

    @Override
    public String getTokenSecret()
    {
        return pword;
    }
//    public void setTokenSecret(String pword)
//    {
//        this.pword = pword;
//    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((uname == null) ? 0 : uname.hashCode());
        result = prime * result
                + ((pword == null) ? 0 : pword.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BasicTokenCredential other = (BasicTokenCredential) obj;
        if (uname == null) {
            if (other.uname != null)
                return false;
        } else if (!uname.equals(other.uname))
            return false;
        if (pword == null) {
            if (other.pword != null)
                return false;
        } else if (!pword.equals(other.pword))
            return false;
        return true;
    }

    

}
