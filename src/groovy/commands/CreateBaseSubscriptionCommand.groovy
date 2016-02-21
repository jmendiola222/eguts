package commands

import grails.validation.Validateable

/**
 * Created by julian on 28/06/15.
 */
@Validateable
class CreateBaseSubscriptionCommand {

    String email
    String url

    static constraints = {

        email(nullable: false, blank: false, email: true)
        url(nullable: false, blank: false, url: true)
    }
}
