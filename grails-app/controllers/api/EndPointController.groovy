package api

import grails.plugin.springsecurity.annotation.Secured
import helpers.RestSearchHelper
import models.config.EndPoint
import models.user.Role
import org.springframework.beans.factory.annotation.Autowired

@Secured(['ROLE_ADMIN'])
class EndPointController extends PaginableRestController {

	@Autowired
	EndPointController(RestSearchHelper restSearchHelper) {
		super(EndPoint.class, restSearchHelper);
	}
	
}