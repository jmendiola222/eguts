package custom

import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.web.pages.discovery.GrailsConventionGroovyPageLocator

class RenderIfExistsTagLib {
	static namespace = "g"
	
	private static final log = LogFactory.getLog(this)

	GrailsConventionGroovyPageLocator groovyPageLocator

	def renderIfExists = { attrs,body ->
		def found = groovyPageLocator.findTemplate(attrs.template);
		
		if(found) {
			out << g.render(template:attrs.template)
		}
	}
}
