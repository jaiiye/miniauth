package org.miniauth;


// Base exception to be used throughout MiniAuth modules.
public class MiniAuthException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    // Resource URL (or, URL path segment).
    private final String resource;       // From request


    public MiniAuthException() 
    {
        this((String) null);
    }

    public MiniAuthException(String message) 
    {
        this(message, (String) null);
    }
    public MiniAuthException(String message, String resource) 
    {
        super(message);
        this.resource = resource;
    }

    public MiniAuthException(String message, Throwable cause) 
    {
        this(message, cause, (String) null);
    }
    public MiniAuthException(String message, Throwable cause, String resource) 
    {
        super(message, cause);
        this.resource = resource;
    }

    public MiniAuthException(Throwable cause) 
    {
        this(cause, (String) null);
    }
    public MiniAuthException(Throwable cause, String resource) 
    {
        super(cause);
        this.resource = resource;
    }


    // Getters only.

    public String getResource()
    {
        return this.resource;
    }

}
