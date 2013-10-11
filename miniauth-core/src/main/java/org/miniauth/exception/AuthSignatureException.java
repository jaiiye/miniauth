package org.miniauth.exception;

import org.miniauth.MiniAuthException;


// Exception while generating OAuth signatures.
public class AuthSignatureException extends MiniAuthException
{
    private static final long serialVersionUID = 1L;

    public AuthSignatureException()
    {
        super();
    }


    public AuthSignatureException(String message)
    {
        super(message);
    }
    public AuthSignatureException(String message, String resource)
    {
        super(message, resource);
    }

    public AuthSignatureException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public AuthSignatureException(String message, Throwable cause, String resource)
    {
        super(message, cause, resource);
    }

    public AuthSignatureException(Throwable cause)
    {
        super(cause);
    }
    public AuthSignatureException(Throwable cause, String resource)
    {
        super(cause, resource);
    }


}
