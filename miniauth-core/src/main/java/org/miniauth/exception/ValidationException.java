package org.miniauth.exception;

import org.miniauth.MiniAuthException;


/**
 * Data format error, etc.
 */
public class ValidationException extends MiniAuthException
{
    private static final long serialVersionUID = 1L;

    public ValidationException()
    {
        super();
    }


    public ValidationException(String message)
    {
        super(message);
    }
    public ValidationException(String message, String resource)
    {
        super(message, resource);
    }

    public ValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public ValidationException(String message, Throwable cause, String resource)
    {
        super(message, cause, resource);
    }

    public ValidationException(Throwable cause)
    {
        super(cause);
    }
    public ValidationException(Throwable cause, String resource)
    {
        super(cause, resource);
    }


}
