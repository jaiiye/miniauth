package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.oauth.credential.mapper.OAuthTokenCredentialMapper;
import org.miniauth.service.EndorserService;


/**
 * An EndorserService can be defined per Consumer.
 * (Consumer credential is implicitly set via the embedded OAuthTokenCredentialMapper.)
 */
public class OAuthEndorserService extends OAuthCredentialService implements EndorserService
{
    private static final Logger log = Logger.getLogger(OAuthEndorserService.class.getName());
    private static final long serialVersionUID = 1L;

    // private OAuthRequestEndorser requestEndorser = null;
    
    public OAuthEndorserService(OAuthTokenCredentialMapper credentialMapper)
    {
        super(credentialMapper);
        // requestEndorser = OAuthRequestEndorser.getInstance();
    }

    public OAuthTokenCredentialMapper getOAuthTokenCredentialMapper()
    {
        return (OAuthTokenCredentialMapper) getOAuthCredentialMapper();
    }

    @Override
    public boolean endorse(OutgoingRequest request) throws MiniAuthException
    {
        AccessIdentity accessIdentity = ((OAuthOutgoingRequest) request).getAccessIdentity();
        // log.warning(">>>>>>>>>>>>>>>>>>>>>>>>>>> accessIdentity = " + accessIdentity);
//        AccessCredential accessCredential = getOAuthCredentialMapper().getAccesssCredential(accessIdentity);
        AccessCredential accessCredential = getOAuthCredentialMapper().getAccesssCredential(accessIdentity);
        // log.warning(">>>>>>>>>>>>>>>>>>>>>>>>>>> accessCredential = " + accessCredential);
        // if(accessCredential == null) {
        if(accessCredential == null || (accessCredential.getConsumerSecret() == null && accessCredential.getTokenSecret() == null) ) {
            throw new InvalidCredentialException("AccessCredential not found.");
        }
        return OAuthRequestEndorser.getInstance().endorse(accessCredential, request);
    }
    
    
}
