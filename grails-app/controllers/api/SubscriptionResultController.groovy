package api

import helpers.RestSearchHelper
import models.result.SubscriptionResult
import org.springframework.beans.factory.annotation.Autowired

class SubscriptionResultController extends PaginableRestController {

	@Autowired
	SubscriptionResultController(RestSearchHelper restSearchHelper) {
		super(SubscriptionResult.class, restSearchHelper);
	}
}