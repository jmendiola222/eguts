package app

import grails.plugin.springsecurity.SpringSecurityService
import grails.util.Holders
import models.user.User

class AppFilters {

    def filters = {

        frontUser(controller:'front', action:'*') {
            after = { model ->
                def ctx = Holders.grailsApplication.mainContext
                SpringSecurityService springSecurityService = (SpringSecurityService) ctx.getBean("springSecurityService");

                User user = springSecurityService.getCurrentUser();
                if(user) {
                    model.isLoggedIn = true
                    model.userName = user.username
                    model.isAdmin = user.isAdmin()
                }
                return true;
            }
        }

        backUser(controller:'front|assets|user|logout|login|error|userView', invert:true) {
            after = { model ->
                if( model != null) {
                    def ctx = Holders.grailsApplication.mainContext
                    SpringSecurityService springSecurityService = (SpringSecurityService) ctx.getBean("springSecurityService");
                    User user = springSecurityService.getCurrentUser();
                    model.userName = user.username
                    model.appName = "Eguts";
                }
                return true;
            }
        }
    }
}