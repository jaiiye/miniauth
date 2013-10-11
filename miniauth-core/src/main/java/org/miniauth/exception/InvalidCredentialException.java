package org.miniauth.exception;

import org.miniauth.MiniAuthException;


// Auth error.
public class InvalidCredentialException extends MiniAuthException
{
    private static final long serialVersionUID = 1L;

    public InvalidCredentialException()
    {
        super();
    }


    public InvalidCredentialException(String message)
    {
        super(message);
    }
    public InvalidCredentialException(String message, String resource)
    {
        super(message, resource);
    }

    public InvalidCredentialException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public InvalidCredentialException(String message, Throwable cause, String resource)
    {
        super(message, cause, resource);
    }

    public InvalidCredentialException(Throwable cause)
    {
        super(cause);
    }
    public InvalidCredentialException(Throwable cause, String resource)
    {
        super(cause, resource);
    }


}
