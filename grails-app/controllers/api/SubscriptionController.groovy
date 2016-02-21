package api

import commands.CreateBaseSubscriptionCommand
import grails.plugin.springsecurity.annotation.Secured
import helpers.Const
import helpers.RestSearchHelper
import models.Subscription
import models.user.Role
import models.user.User
import org.springframework.beans.factory.annotation.Autowired

import javax.transaction.Transactional

class SubscriptionController extends PaginableRestController {

	def endPointService

	@Autowired
	SubscriptionController(RestSearchHelper restSearchHelper) {
		super(Subscription.class, restSearchHelper);
	}

	@Transactional
	def Subscription save(CreateBaseSubscriptionCommand command) {

		def subscriberRole = Role.findByAuthority(Const.ROLE_SUBSCRIBER)
		def user = User.findByEmail(command.email) ?: new User(email: command.email , username: command.email.substring(0, command.email.indexOf("@")), enabled: true, role: subscriberRole).save(failOnError: true)

		def endPoint = endPointService.getEndPointByUrl(command.url)
		if(endPoint == null)
			return error(message(code:'subscription.error.non.endpoint'))

		def subscription = new Subscription(url: command.url, endPoint : endPoint, user : user)

		if (subscription.hasErrors()) {
			errors(subscription.errors)
			return
		}
		subscription.save(failOnError: true)

		return ok(message(code:'default.created.message'))
	}
}