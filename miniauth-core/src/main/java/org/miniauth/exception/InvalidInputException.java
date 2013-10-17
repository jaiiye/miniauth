package org.miniauth.exception;

import org.miniauth.MiniAuthException;


/**
 * Invalid request input exception.
 */
public class InvalidInputException extends MiniAuthException
{
    private static final long serialVersionUID = 1L;

    public InvalidInputException()
    {
        super();
    }


    public InvalidInputException(String message)
    {
        super(message);
    }
    public InvalidInputException(String message, String resource)
    {
        super(message, resource);
    }

    public InvalidInputException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public InvalidInputException(String message, Throwable cause, String resource)
    {
        super(message, cause, resource);
    }

    public InvalidInputException(Throwable cause)
    {
        super(cause);
    }
    public InvalidInputException(Throwable cause, String resource)
    {
        super(cause, resource);
    }


}
