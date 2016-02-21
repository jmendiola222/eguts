package api

import grails.converters.JSON
import grails.util.Holders

class VersionController {

	def index = {
		render (['version' : Holders.grailsApplication.metadata['app.version']] as JSON)
	}	
}
