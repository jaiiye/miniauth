package org.miniauth.exception;

import org.miniauth.MiniAuthException;


/**
 * Auth error.
 * Depending on the context,
 * InvalidCredentialException might be more suitable than Unauthorized exception (which is generally to be used by the "provider"). 
 */
public class UnauthorizedException extends MiniAuthException
{
    private static final long serialVersionUID = 1L;

    public UnauthorizedException()
    {
        super();
    }


    public UnauthorizedException(String message)
    {
        super(message);
    }
    public UnauthorizedException(String message, String resource)
    {
        super(message, resource);
    }

    public UnauthorizedException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public UnauthorizedException(String message, Throwable cause, String resource)
    {
        super(message, cause, resource);
    }

    public UnauthorizedException(Throwable cause)
    {
        super(cause);
    }
    public UnauthorizedException(Throwable cause, String resource)
    {
        super(cause, resource);
    }


}
