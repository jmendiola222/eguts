package security

import grails.plugin.springsecurity.web.authentication.RequestHolderAuthenticationFilter
import org.codehaus.groovy.grails.web.metaclass.WithFormMethod
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.context.request.RequestContextHolder

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CsrfFilter extends RequestHolderAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        def withForm = new WithFormMethod();

        withForm.withForm((GrailsWebRequest) RequestContextHolder.currentRequestAttributes(), {
            super.attemptAuthentication(request, response)
        }).invalidToken {
            throw new AuthenticationServiceException("Missing token");
        };
    }
}
