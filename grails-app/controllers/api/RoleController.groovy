package api

import helpers.RestSearchHelper
import models.user.Role
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.beans.factory.annotation.Autowired

@Secured(['ROLE_SYSADMIN'])
class RoleController extends PaginableRestController {

	@Autowired
	RoleController(RestSearchHelper restSearchHelper) {
		super(Role.class, restSearchHelper);
	}
	
}