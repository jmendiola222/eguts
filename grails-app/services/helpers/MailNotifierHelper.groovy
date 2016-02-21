package helpers

import grails.plugin.mail.MailService
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
		sender = grailsApplication.config.notifications.from.toString();
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
}
