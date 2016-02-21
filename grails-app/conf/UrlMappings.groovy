import org.apache.commons.logging.LogFactory

import static org.codehaus.groovy.grails.web.mapping.DefaultUrlMappingEvaluator.ACTION_DELETE
import static org.codehaus.groovy.grails.web.mapping.DefaultUrlMappingEvaluator.ACTION_SHOW

class UrlMappings {

    private static final log = LogFactory.getLog(this)

    static mappings = {

        // RESTService api

        "/api/user"(resources: 'user')
        "/api/role"(resources: 'role')
        "/api/endPoint"(resources: 'endPoint')

        "/controls/bootstrap-grid"(view: "/controls/bootstrap-grid")
        "/controls/grails-alerts"(view: "/controls/grails-alerts")
        "/controls/visibility-picker"(view: "/controls/visibility-picker")

        "/api/$controller/$action?/$id?(.$format)?" {
            constraints {
                controller(validator: { !it.toString().contains('View') })
            }
        }

        "/$controller/$action?/$id?/$relationId?(.$format)?" {
            constraints {
                controller matches: /login|adminLogin|front|logout|test|version|error/
            }
        }


        name backendMapping: "/admin/$controller/$action?/$id?/$relationId?(.$format)?" { constraints {} }
        name apiMapping: "/api/$controller/$action?/$id?/$relationId?(.$format)?" { constraints {} }

        "/newsimages/$fileName.$format" {
            controller = "Upload"
            action = "uploaded"
        }

        "/file/$fileName" {
            controller = "Upload"
            action = "uploadedFile"
        }

        "/" { controller = "Front" }
        "/admin" { controller = "Home" }
        "500"(view: '/error')
        "404"(view: '/404')
        "403"(view: '/403')

    }
}
