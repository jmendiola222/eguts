package api

import commands.CreateBaseSubscriptionCommand
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import helpers.Const
import helpers.DateUtils
import helpers.RestSearchHelper
import models.Subscription
import models.config.EndPoint
import models.result.SubscriptionResult
import models.user.Role
import models.user.User
import models.user.UserProfile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import javax.transaction.Transactional

class SubscriptionController extends PaginableRestController {

	static allowedMethods = [subscribeAnonymous: "POST"]

	def endPointService

	def subscriptionRunnerService

	def rest

	@Autowired
	SubscriptionController(RestSearchHelper restSearchHelper) {
		super(Subscription.class, restSearchHelper);
	}

	@Transactional
	def Subscription subscribeAnonymous(CreateBaseSubscriptionCommand command) {

		def subscriberRole = Role.findByAuthority(Const.ROLE_SUBSCRIBER)
		def username =command.email.substring(0, command.email.indexOf("@"))
		def user = User.findByEmail(command.email) ?: new User(email: command.email , username: username, enabled: true, role: subscriberRole)
		user.userProfile = new UserProfile()
		user.save(failOnError: true)

		def endPoint = endPointService.getEndPointByUrl(command.url)
		if(endPoint == null)
			return error(message(code:'subscription.error.non.endpoint'))

		def subscription = new Subscription(url: command.url, endPoint : endPoint,
				subscriber : user, startDate : DateUtils.now())

		if (subscription.hasErrors()) {
			errors(subscription.errors)
			return
		}
		subscription.save(failOnError: true)

		return ok(message(code:'default.created.message'))
	}

	def run() {
		try {
			subscriptionRunnerService.run()
			ok()
		}catch(Exception ex){
			log.error(ex)
			error(ex.message)
		}
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

			return jsonOk(currentResult)

		}catch(Exception ex){
			log.error(ex)
			error(ex.message)
		}
	}

	def hitEndPoint = {

		def id = params.id;
		Subscription subscription = Subscription.findById(id);
		if(subscription == null) {
			render(status:HttpStatus.BAD_REQUEST, text: "No Subscription found for Id: " + id)
			return;
		}

		def resp = rest.get(subscription.getUrl()){
			contentType "application/json"
		}

		return jsonOk(resp.json)
	}
}