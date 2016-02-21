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

	def subscriptionRunnerService

	def rest

	@Autowired
	SpringSecurityService springSecurityService

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

	//@Secured([Const.ROLE_ADMIN])
	def run() {
		try {
			subscriptionRunnerService.run()
		}catch(Exception ex){
			log.error(ex)
			render(status:HttpStatus.BAD_REQUEST, text: ex.message )
		}
		render(status: HttpStatus.OK, text: "ok")
	}

	def runNow() {
		try {
			def id = params.id;
			Subscription subscription = Subscription.findById(id);
			if(subscription == null) {
				render(status:HttpStatus.BAD_REQUEST, text: "No Subscription found for Id: " + id)
				return;
			}

			subscriptionRunnerService.runNow(subscription)

			def currentResult = SubscriptionResult.findBySubscription(subscription);

			response.status = HttpStatus.OK.value()
			render currentResult as JSON;

		}catch(Exception ex){
			log.error(ex)
			render(status:HttpStatus.BAD_REQUEST, text: ex.message )
		}
		render(status: HttpStatus.OK, text: "ok")
	}

	def hitEndPoint = {

		EndPoint endPoint = EndPoint.findByName("Mercado Libre");
		String targetUrl = endPoint.getTarget()

		def resp = rest.get(targetUrl){
			contentType "application/json"
		}
		response.status = HttpStatus.OK.value()
		render resp.json as JSON
	}
}
