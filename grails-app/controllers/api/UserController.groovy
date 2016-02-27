package api

import helpers.MailNotifierHelper
import helpers.RestSearchHelper
import helpers.SecurityConstants
import models.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired

import javax.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Secured(SecurityConstants.HAS_SYS_ADMIN_ROLE)
class UserController extends PaginableRestController {

	private SpringSecurityService springSecurityService

	private MailNotifierHelper mailNotifierHelper;

	@Autowired
	UserController(RestSearchHelper restSearchHelper, MailNotifierHelper mailNotifierHelper, SpringSecurityService springSecurityService) {
		super(User.class, restSearchHelper);
		this.mailNotifierHelper = mailNotifierHelper;
		this.springSecurityService = springSecurityService;
	}

	@Override
	@Secured(SecurityConstants.HAS_APP_ROLE)
	def show() {
		if(!isAllowToViewUser(params.id))
			return;
		ok(queryForResource(params.id))
	}

	private boolean isAllowToViewUser(String userId){
		User currentLoggedInUser = springSecurityService.getCurrentUser();
		if(currentLoggedInUser.role.authority != 'ROLE_SYSADMIN' && !currentLoggedInUser.id.toString().trim().equals(userId.trim()))
		{
			response.status = FORBIDDEN.value();
			return false
		}
		return true
	}

	@Transactional
	@Override
	def save() {
		def parameters = getParametersToBind()

		fillToken(parameters)
		withForm {
			refreshToken();
			User theUserToInsert = createResource()
			String password = theUserToInsert.password
			def saved = super.doSave();
			mailNotifierHelper.sendUserCreated(theUserToInsert, password)

			return saved
		}.invalidToken {
			response.status = METHOD_NOT_ALLOWED.value();
		}
	}

	@Transactional
	@Override
	@Secured(SecurityConstants.HAS_APP_ROLE)
	def update() {
		def parameters = getParametersToBind()
		fillToken(parameters)
		withForm {
			refreshToken();

			if (!isAllowToViewUser(params.id))
				return;

			def newPassword = parameters.password
			def personalPassword = parameters.personalPassword

			User user = queryForResource(params.id)
			if (user == null) {
				notFound()
				return
			}

			User currentLoggedInUser = springSecurityService.getCurrentUser();
			boolean sameUser = currentLoggedInUser == user;

			String[] valResult = User.validatePassword(newPassword, user)

			if (newPassword != null && !newPassword.isEmpty() && !validateUserPersonalPassword(user, currentLoggedInUser, personalPassword)) {
				response.status = BAD_REQUEST.value();
				def msg = message(error: user.errors.getAllErrors()[0]);
				respond([errors: [[message: msg]]])
				return
			}

			if (valResult[0] != "ok") {
				if (valResult.length > 1) {
					response.status = BAD_REQUEST.value();
					respond([errors: [[message: valResult[1]]]])
				} else {
					response.status = BAD_REQUEST.value();
					respond([errors: [[message: message(code: valResult[0])]]])
				}
				return
			}

			user.setInit(!sameUser)
			super.doUpdate()
			User updatedUser = User.get(params.id)

			mailNotifierHelper.sendUserCreated(updatedUser, newPassword)
		}.invalidToken {
			response.status = METHOD_NOT_ALLOWED.value();
		}
	}

	def validateUserPersonalPassword(User submitedUser, User currentLoggedInUser, String personalPassword){

		if(personalPassword == null || StringUtils.isEmpty(personalPassword)) {
			submitedUser.errors.reject('user.personalPassword.required.message')
			return false;
		}

		boolean result = currentLoggedInUser.isPasswordValid(personalPassword, currentLoggedInUser.getPassword());
		if(!result)
			submitedUser.errors.reject('user.personalPassword.matches.invalid')
		return result;
	}

	@Transactional
	def unlock() {
		def parameters = getParametersToBind()
		fillToken(parameters)
		withForm {
			refreshToken();

			User toUpdate  = User.findById(params.id)

			if (toUpdate == null) {
				notFound()
				return
			}

			if(toUpdate != null && toUpdate.isAccountLocked()) {
				toUpdate.setAccountLocked(false)
				toUpdate.setLogInAttemps(0)
				toUpdate.save flush: true
			}
			request.withFormat {
				form {
					flash.message = message(code: 'default.unlocked.message', args: [
							message(code: 'user.label', default: 'User'),
							userInstance.username
					])
					redirect action:"index", method:"GET"
				}
				'*'{ render status: NO_CONTENT; }
			}
		}.invalidToken { response.status = METHOD_NOT_ALLOWED.value(); }
	}

}