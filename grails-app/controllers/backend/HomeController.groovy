package backend

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import models.Subscription
import models.config.EndPoint
import models.result.SubscriptionResult
import models.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class HomeController {

	@Autowired
	SpringSecurityService springSecurityService

	def bootstrapService

	@Secured(['ROLE_ADMIN', 'ROLE_SYSADMIN'])
	def index() {
		User currentLoggedInUser = springSecurityService.getCurrentUser();

		if(currentLoggedInUser.role.authority == 'ROLE_SYSADMIN')
			redirect(controller: "userView", mapping: "backendMapping")
		else {
			if (currentLoggedInUser.role.authority == 'ROLE_ADMIN')
				redirect(controller: "endPointView", mapping: "backendMapping")
			else
				response.status = HttpStatus.FORBIDDEN.value();
		}
	}

	def init() {
		bootstrapService.init()
		render(status: HttpStatus.OK, text: "init run ok")
	}
}
