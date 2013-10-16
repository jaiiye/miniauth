package org.miniauth.exception;

import org.miniauth.MiniAuthException;


// Similar to IlligalStateException.
public class InvalidStateException extends MiniAuthException
{
    private static final long serialVersionUID = 1L;

    public InvalidStateException()
    {
        super();
    }


    public InvalidStateException(String message)
    {
        super(message);
    }
    public InvalidStateException(String message, String resource)
    {
        super(message, resource);
    }

    public InvalidStateException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public InvalidStateException(String message, Throwable cause, String resource)
    {
        super(message, cause, resource);
    }

    public InvalidStateException(Throwable cause)
    {
        super(cause);
    }
    public InvalidStateException(Throwable cause, String resource)
    {
        super(cause, resource);
    }


}
