package front

import grails.plugin.springsecurity.SpringSecurityService
import models.user.User
import org.springframework.beans.factory.annotation.Autowired

class FrontController {

	@Autowired
	SpringSecurityService springSecurityService

	def index() {
		if(springSecurityService.loggedIn) {
			User currentLoggedInUser = springSecurityService.getCurrentUser();

			if (currentLoggedInUser.role.authority == 'ROLE_SYSADMIN' ) {
				redirect(controller: "userView", mapping: "backendMapping");
				return;
			}

			[isLoggedIn:true,isAdmin:currentLoggedInUser.isAdmin(), userName: currentLoggedInUser.username]
		}
		[isLoggedIn:false]
	}

	def userProfile = {
		User currentLoggedInUser = springSecurityService.getCurrentUser();

		boolean isExpired;
		boolean isFirstOrReset = (currentLoggedInUser.getLastPasswordChange() == null);
		if(isFirstOrReset) {
			flash.message = message(code: 'springSecurity.errors.login.passwordReseted')
		}else{
			use(groovy.time.TimeCategory) {
				def duration = new Date() - currentLoggedInUser.getLastPasswordChange();
				//TODO: read from config
				isExpired = (duration.getDays() > 45);
			}
			if(isExpired)
				flash.message = message(code: 'springSecurity.errors.login.passwordExpired')
		}

		render(view: "userProfile", model: [isLoggedIn:true,isAdmin:currentLoggedInUser.isAdmin(), userName: currentLoggedInUser.username, user: currentLoggedInUser, subPage: "personalData"])
	}

	def logIn = {
		redirect(controller: "login", action: "auth",mapping: "backendMapping");
		return;
	}

}
