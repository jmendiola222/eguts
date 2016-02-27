package api

import helpers.RestSearchHelper
import helpers.SecurityConstants
import models.user.Role
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.beans.factory.annotation.Autowired

@Secured(SecurityConstants.HAS_SYS_ADMIN_ROLE)
class RoleController extends PaginableRestController {

	@Autowired
	RoleController(RestSearchHelper restSearchHelper) {
		super(Role.class, restSearchHelper);
	}
	
}