package security

import models.user.User
import grails.transaction.Transactional
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent

class LoginBadCredentialEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    //deal with BadCredentials login
    @Transactional
    void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        def user = User.findByUsername(event.authentication.principal)
        //TODO: read from config
        if(user != null){
            def maxLogInAttempts = 5
            if(user.getLogInAttemps() >= maxLogInAttempts){
                user.setAccountLocked(true);
            }else{
                user.logInAttemps++;
            }
            user.save(failOnError: true, flush: true);
        }
    }
}
