package org.miniauth.web;

import org.miniauth.MiniAuthException;


/**
 * Any generic exception from Web module.
 */
public class MiniAuthWebException extends MiniAuthException
{
    private static final long serialVersionUID = 1L;

    public MiniAuthWebException()
    {
        super();
    }


    public MiniAuthWebException(String message)
    {
        super(message);
    }
    public MiniAuthWebException(String message, String resource)
    {
        super(message, resource);
    }

    public MiniAuthWebException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public MiniAuthWebException(String message, Throwable cause, String resource)
    {
        super(message, cause, resource);
    }

    public MiniAuthWebException(Throwable cause)
    {
        super(cause);
    }
    public MiniAuthWebException(Throwable cause, String resource)
    {
        super(cause, resource);
    }


}
