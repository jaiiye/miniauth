package org.miniauth.oauth.service;

import java.util.logging.Logger;

import org.miniauth.MiniAuthException;
import org.miniauth.common.OutgoingRequest;
import org.miniauth.credential.AccessCredential;
import org.miniauth.credential.AccessIdentity;
import org.miniauth.exception.InvalidCredentialException;
import org.miniauth.oauth.common.OAuthOutgoingRequest;
import org.miniauth.oauth.credential.mapper.OAuthConsumerCredentialMapper;
import org.miniauth.service.EndorserService;


/**
 * An OAuthConsumerEndorserService can be used for Two-Legged OAuth.
 * It can be reused across multiple consumer credentials.
 * (Consumer credentials are implicitly set via the embedded OAuthConsumerCredentialMapper.)
 */
public class OAuthConsumerEndorserService extends OAuthCredentialService implements EndorserService
{
    private static final Logger log = Logger.getLogger(OAuthConsumerEndorserService.class.getName());
    private static final long serialVersionUID = 1L;

    // private OAuthRequestEndorser requestEndorser = null;
    
    public OAuthConsumerEndorserService(OAuthConsumerCredentialMapper credentialMapper)
    {
        super(credentialMapper);
        // requestEndorser = OAuthRequestEndorser.getInstance();
    }

    public OAuthConsumerCredentialMapper getOAuthConsumerCredentialMapper()
    {
        return (OAuthConsumerCredentialMapper) getOAuthCredentialMapper();
    }

    @Override
    public boolean endorse(OutgoingRequest request) throws MiniAuthException
    {
        AccessIdentity accessIdentity = ((OAuthOutgoingRequest) request).getAccessIdentity();
        // log.warning(">>>>>>>>>>>>>>>>>>>>>>>>>>> accessIdentity = " + accessIdentity);
//        AccessCredential accessCredential = getOAuthCredentialMapper().getAccesssCredential(accessIdentity);
        AccessCredential accessCredential = getOAuthCredentialMapper().getAccesssCredential(accessIdentity);
        // log.warning(">>>>>>>>>>>>>>>>>>>>>>>>>>> accessCredential = " + accessCredential);
        if(accessCredential == null || accessCredential.getTokenSecret() == null) {
            throw new InvalidCredentialException("AccessCredential not found.");
        }
        return OAuthRequestEndorser.getInstance().endorse(accessCredential, request);
    }
    
    
}
