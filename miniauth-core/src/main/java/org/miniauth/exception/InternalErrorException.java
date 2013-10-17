package org.miniauth.exception;

import org.miniauth.MiniAuthException;


/**
 * Any generic exception.
 */
public class InternalErrorException extends MiniAuthException
{
    private static final long serialVersionUID = 1L;

    public InternalErrorException()
    {
        super();
    }


    public InternalErrorException(String message)
    {
        super(message);
    }
    public InternalErrorException(String message, String resource)
    {
        super(message, resource);
    }

    public InternalErrorException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public InternalErrorException(String message, Throwable cause, String resource)
    {
        super(message, cause, resource);
    }

    public InternalErrorException(Throwable cause)
    {
        super(cause);
    }
    public InternalErrorException(Throwable cause, String resource)
    {
        super(cause, resource);
    }


}
