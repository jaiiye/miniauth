package org.miniauth.exception;

import org.miniauth.MiniAuthException;


// Auth error.
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
