import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.web.authentication.AjaxAwareAuthenticationEntryPoint
import marshallers.*
import notifiers.*
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.RequestMatcher
import security.CsrfFilter
import security.LoginBadCredentialEventListener
import security.LoginSuccessEventListener
import helpers.SecurityMessageResolver
import helpers.security.Http401EntryPoint
import helpers.security.UrlRequestMatcher

// Place your Spring DSL code here
beans = {

    def securityConfig = SpringSecurityUtils.securityConfig

    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        defaultLocale = new Locale("es","AR")
        java.util.Locale.setDefault(defaultLocale)
    }

    authenticationProcessingFilter(CsrfFilter) {
        authenticationManager = ref('authenticationManager')
        sessionAuthenticationStrategy = ref('sessionAuthenticationStrategy')
        authenticationSuccessHandler = ref('authenticationSuccessHandler')
        authenticationFailureHandler = ref('authenticationFailureHandler')
        rememberMeServices = ref('rememberMeServices')
        authenticationDetailsSource = ref('authenticationDetailsSource')
        requiresAuthenticationRequestMatcher = ref('filterProcessUrlRequestMatcher')
        usernameParameter = SpringSecurityUtils.securityConfig.apf.usernameParameter // j_username
        passwordParameter = SpringSecurityUtils.securityConfig.apf.passwordParameter // j_password
        continueChainBeforeSuccessfulAuthentication = SpringSecurityUtils.securityConfig.apf.continueChainBeforeSuccessfulAuthentication // false
        allowSessionCreation = SpringSecurityUtils.securityConfig.apf.allowSessionCreation // true
        postOnly = SpringSecurityUtils.securityConfig.apf.postOnly // true
        storeLastUsername = SpringSecurityUtils.securityConfig.apf.storeLastUsername // false
    }


    /*adminEntryPoint(AjaxAwareAuthenticationEntryPoint, "/adminLogin") {
     //loginFormUrl = securityConfig.auth.loginFormUrl
     forceHttps = securityConfig.auth.forceHttps
     ajaxLoginFormUrl = securityConfig.auth.ajaxLoginFormUrl
     useForward = securityConfig.auth.useForward
     portMapper = ref('portMapper')
     portResolver = ref('portResolver')
     }*/

    apiRequestMatcher(UrlRequestMatcher, "/api/**");
    //adminRequestMatcher(UrlRequestMatcher, "/admin/**");
    //frontRequestMatcher(UrlRequestMatcher, "/front**");
    http401EntryPoint(Http401EntryPoint)
    //adminEntryPoint(NoCheckEntryPoint)
    //frontEntryPoint(NoCheckEntryPoint)

    LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> authenticationEntryPointMap =
            [
                    (apiRequestMatcher) : http401EntryPoint //,
                    //(adminRequestMatcher) : adminEntryPoint,
                    //(frontRequestMatcher) : frontEntryPoint,
            ]

    defaultAuthenticationEntryPoint(AjaxAwareAuthenticationEntryPoint, securityConfig.auth.loginFormUrl) {
        //loginFormUrl = securityConfig.auth.loginFormUrl
        forceHttps = securityConfig.auth.forceHttps
        ajaxLoginFormUrl = securityConfig.auth.ajaxLoginFormUrl
        useForward = securityConfig.auth.useForward
        portMapper = ref('portMapper')
        portResolver = ref('portResolver')
    }

    authenticationEntryPoint(DelegatingAuthenticationEntryPoint, authenticationEntryPointMap) {  defaultEntryPoint = ref("defaultAuthenticationEntryPoint")  }

    customObjectMarshallers( CustomObjectMarshallers ) {
        marshallers = [new UserMarshaller()]
    }

    securityMessageResolver(SecurityMessageResolver){
        i18nService = ref('i18nService')
    }

    loginBadCredentialEventListener(LoginBadCredentialEventListener) {}

    loginSuccessEventListener(LoginSuccessEventListener) {}

    consoleNotifier(ConsoleNotifier) {}
    emailNotifier(EmailNotifier) {}
    fileNotifier(FileNotifier) {}

    rest(grails.plugins.rest.client.RestBuilder)

}
