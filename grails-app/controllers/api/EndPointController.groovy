package api

import helpers.RestSearchHelper
import models.config.EndPoint
import org.springframework.beans.factory.annotation.Autowired

class EndPointController extends PaginableRestController {

	@Autowired
	EndPointController(RestSearchHelper restSearchHelper) {
		super(EndPoint.class, restSearchHelper);
	}

	@Override
	protected EndPoint createResource() {
		EndPoint endPoint = super.createResource()
		def elements = endPoint.endPointElements.collectAll { it }
		endPoint.endPointElements.clear()
		elements.each { endPoint.addToEndPointElements( it ) }
		return endPoint
	}

	protected void beforeUpdate(instance){
		EndPoint endPoint = instance
		def elements = endPoint.endPointElements.collectAll { it }
		endPoint.endPointElements.clear()
		elements.each { endPoint.addToEndPointElements( it ) }
	}
}