package backend

import grails.plugin.springsecurity.SpringSecurityService
import models.user.User
import org.springframework.beans.factory.annotation.Autowired

class UserViewController {

    @Autowired
    SpringSecurityService springSecurityService

    def index = {
    }

    def profile = {
        User currentLoggedInUser = springSecurityService.getCurrentUser();
        boolean pwdReseted = currentLoggedInUser.lastPasswordChange == null;
        if(pwdReseted){
            flash.message = message(code: 'springSecurity.errors.login.passwordReseted')
        }
        render(view: "profile", model: [user: currentLoggedInUser])
    }
}
