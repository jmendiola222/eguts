package helpers

import grails.plugin.mail.MailService
import models.Subscription
import models.result.SubscriptionResult
import models.user.User
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MailNotifierHelper {

	@Autowired
	MailService mailService

	private String sender;

	@Autowired
	public MailNotifierHelper(GrailsApplication grailsApplication) {
		sender = grailsApplication.config.app.notifications.from.toString();
	}

	def sendUserCreated(User user, String password) {
		mailService.sendMail {
			async true
			from sender
			to user.email
			subject "[Eguts] Credenciales de acceso"
			html( view: "/email/userCreatedEmailTemplate",
			model: [user : user, password : password])
		}
	}

	def sendUserUpdates(User user, SubscriptionResult subscriptionResult) {
		mailService.sendMail {
			async true
			from sender
			to user.email
			subject "[Eguts] Actualizacion de subscripcion"
			html( view: "/email/notificationsEmailTemplate",
					model: [user : user, subscriptionResult : subscriptionResult])
		}
	}
}
