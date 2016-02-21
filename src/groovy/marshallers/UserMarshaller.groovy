package marshallers

import grails.converters.JSON
import models.user.User

class UserMarshaller {

	void register() {
		JSON.registerObjectMarshaller( User ) {
			User user ->
				def properties  = [:]
				properties.id = user?.id
				properties.username = user?.username
				properties.email = user?.email
				properties.role = user?.role
				properties.enabled = user?.enabled
				properties.accountLocked = user?.accountLocked

			return properties;
		}
	}
}
