package helpers

import org.springframework.context.MessageSource
import org.springframework.web.servlet.i18n.SessionLocaleResolver

class I18nService {

	boolean transactional = false

	SessionLocaleResolver localeResolver
	MessageSource messageSource
	
	def grailsApplication

	/**
	 *
	 * @param msgKey
	 * @param defaultMessage default message to use if none is defined in the message source
	 * @param objs objects for use in the message
	 * @return
	 */
	def message(String msgKey, List objs = null, String defaultMessage = null) {

		def msg = messageSource.getMessage(msgKey, objs?.toArray(), defaultMessage, localeResolver.defaultLocale)

		if (msg == null || msg == defaultMessage) {
			log.warn("No i18n messages specified for msgKey: ${msgKey}")
			msg = defaultMessage
		}

		return msg
	}
	
	def errorMessage(error) {
		def g = grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
		return g.message(error: error)
	 }

}