package org.miniauth.exception;

import org.miniauth.MiniAuthException;


// Input error.
public class BadRequestException extends MiniAuthException
{
    private static final long serialVersionUID = 1L;

    public BadRequestException()
    {
        super();
    }


    public BadRequestException(String message)
    {
        super(message);
    }
    public BadRequestException(String message, String resource)
    {
        super(message, resource);
    }

    public BadRequestException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public BadRequestException(String message, Throwable cause, String resource)
    {
        super(message, cause, resource);
    }

    public BadRequestException(Throwable cause)
    {
        super(cause);
    }
    public BadRequestException(Throwable cause, String resource)
    {
        super(cause, resource);
    }


}
