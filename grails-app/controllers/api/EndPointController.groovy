package api

import helpers.RestSearchHelper
import models.config.EndPoint
import org.springframework.beans.factory.annotation.Autowired

class EndPointController extends PaginableRestController {

	@Autowired
	EndPointController(RestSearchHelper restSearchHelper) {
		super(EndPoint.class, restSearchHelper);
	}
}