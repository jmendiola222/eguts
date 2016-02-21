package security

import models.user.User
import grails.transaction.Transactional
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent

class LoginSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Transactional
    void onApplicationEvent(AuthenticationSuccessEvent event) {

        def user = User.findByUsername(event.authentication.principal.username)

        if (user != null) {
            user.setLogInAttemps(0);

            user.save(failOnError: true, flush: true);
        }
    }
}