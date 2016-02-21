package common

import grails.plugin.springsecurity.SpringSecurityUtils

abstract class UserLogoutController {

	private String redirectUrl;
	
	public UserLogoutController(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
	/**
	 * Index action. Redirects to the Spring security logout uri.
	 */
	def index() {

		if (!request.post && SpringSecurityUtils.getSecurityConfig().logout.postOnly) {
			response.sendError HttpServletResponse.SC_METHOD_NOT_ALLOWED // 405
			return
		}

		//Put any pre-logout code here
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl + "?logout-success-url=" + this.redirectUrl; // '/j_spring_security_logout'
	}
}