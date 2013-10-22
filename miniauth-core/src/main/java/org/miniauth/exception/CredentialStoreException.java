package org.miniauth.exception;

import org.miniauth.MiniAuthException;


/**
 * Failure to store or retrieve auth credentials.
 * To be used, primarily, to indicate the errors in Credential Mapper callbacks.
 */
public class CredentialStoreException extends MiniAuthException
{
    private static final long serialVersionUID = 1L;

    public CredentialStoreException()
    {
        super();
    }


    public CredentialStoreException(String message)
    {
        super(message);
    }
    public CredentialStoreException(String message, String resource)
    {
        super(message, resource);
    }

    public CredentialStoreException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public CredentialStoreException(String message, Throwable cause, String resource)
    {
        super(message, cause, resource);
    }

    public CredentialStoreException(Throwable cause)
    {
        super(cause);
    }
    public CredentialStoreException(Throwable cause, String resource)
    {
        super(cause, resource);
    }


}
