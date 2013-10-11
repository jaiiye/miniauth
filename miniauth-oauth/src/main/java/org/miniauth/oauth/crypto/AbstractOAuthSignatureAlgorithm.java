package org.miniauth.oauth.crypto;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.miniauth.crypto.SignatureAlgorithm;
import org.miniauth.exception.AuthSignatureException;
import org.miniauth.exception.InternalErrorException;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.oauth.credential.AccessCredential;
import org.miniauth.oauth.signature.OAuthSignatureGenerator;
import org.miniauth.oauth.util.PercentEncoder;


public abstract class AbstractOAuthSignatureAlgorithm implements OAuthSignatureAlgorithm
{
    private static final Logger log = Logger.getLogger(OAuthSignatureGenerator.class.getName());

    private SignatureAlgorithm signatureAlgorithm;
    
    public AbstractOAuthSignatureAlgorithm()
    {
        signatureAlgorithm = null;
    }

    protected SignatureAlgorithm getSignatureAlgorithm()
    {
        return signatureAlgorithm;
    }
    protected void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm)
    {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    protected String buildKeyString(AccessCredential credential) throws InvalidCredentialException 
    {
        String consumerSecret = credential.getConsumerSecret();
        String tokenSecret = credential.getTokenSecret();
        return buildKeyString(consumerSecret, tokenSecret);
    }

    private String buildKeyString(String consumerSecret, String tokenSecret) throws InvalidCredentialException 
    {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(PercentEncoder.encode(consumerSecret));
            sb.append("&");
            sb.append(PercentEncoder.encode(tokenSecret));
        } catch (InternalErrorException e) {
            throw new InvalidCredentialException("Bad consumer/token secrets.", e);
        }
        String keyString = sb.toString();
        if(log.isLoggable(Level.FINER)) log.finer("keyString = " + keyString);
        return keyString;
    }


    @Override
    public boolean verify(String text, AccessCredential credential, String signature) throws AuthSignatureException, InvalidCredentialException
    {
        String expectedSignature = generate(text, credential);
        return expectedSignature.equals(signature);
    }

}
