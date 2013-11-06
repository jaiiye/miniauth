package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.oauth.credential.OAuthAccessIdentity;
import org.miniauth.oauth.credential.mapper.OAuthSingleConsumerCredentialMapper;
import org.miniauth.service.EndorserService;


/**
 * This can be used for Two-Legged OAuth.
 * OAuthSingleConsumerEndorserService is associated with a single consumer credential.
 */
public class OAuthSingleConsumerEndorserService extends OAuthCredentialService implements EndorserService
{
    private static final Logger log = Logger.getLogger(OAuthSingleConsumerEndorserService.class.getName());
    private static final long serialVersionUID = 1L;

    // private OAuthRequestEndorser requestEndorser = null;
    
    public OAuthSingleConsumerEndorserService(OAuthSingleConsumerCredentialMapper credentialMapper)
    {
        super(credentialMapper);
        // requestEndorser = OAuthRequestEndorser.getInstance();
    }

    public OAuthSingleConsumerCredentialMapper getOAuthSingleConsumerCredentialMapper()
    {
        return (OAuthSingleConsumerCredentialMapper) getOAuthCredentialMapper();
    }
    
    
    public AccessIdentity getAccessIdentity()
    {
        return new OAuthAccessIdentity(getOAuthSingleConsumerCredentialMapper().getConsumerKey(), null);
    }

    
    @Override
    public boolean endorse(OutgoingRequest request) throws MiniAuthException
    {
        // TBD:
        // assert consumerKey == requestConsumerKey
        String requestConsumerKey = ((OAuthOutgoingRequest) request).getConumserKey();
        String consumerKey = getOAuthSingleConsumerCredentialMapper().getConsumerKey();
        // ...
        AccessCredential accessCredential = getOAuthSingleConsumerCredentialMapper().getAccesssCredential();
        // log.warning(">>>>>>>>>>>>>>>>>>>>>>>>>>> accessCredential = " + accessCredential);
        if(accessCredential == null || accessCredential.getTokenSecret() == null) {
            throw new InvalidCredentialException("AccessCredential not found.");
        }
        return OAuthRequestEndorser.getInstance().endorse(accessCredential, request);
    }
    
    
}
