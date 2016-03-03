import helpers.SecurityConstants

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = eguts // change this to alter the default package name and Maven publishing destination

def jvmArgs = ['-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005']

grails.project.fork = [
        test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true, jvmArgs: jvmArgs],
        run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false, jvmArgs: jvmArgs],
        war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false, jvmArgs: jvmArgs],
        console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, jvmArgs: jvmArgs]
]

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'prototype'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

grails.databinding.dateFormats = [
        //	'MMddyyyy',
        //	'yyyy-MM-dd HH:mm:ss.S',
        "yyyy-MM-dd",
        "yyyy-MM-dd'T'hh:mm:ss.S'Z'"
]

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = ['helpers']
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']
grails.exceptionresolver.logRequestParameters=false

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        grails.logging.jul.usebridge = true
        images.location = "/tmp/images"
        images.service = "/eguts/images/"
        files.location = "/tmp/files"
        files.service = "/eguts/file/"
        grails.serverURL = "http://localhost:8080/eguts"

        grails {
            mail {
                host = "smtp.mandrillapp.com"
                port = 587
                username = "averbner@atixlabs.com"
                password = "G-UiXfBJFAeom-FaVlP3Hg"
                props = ["mail.smtp.starttls.enable": "true",
                         "mail.smtp.port"           : "587"]
            }
        }
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%d{yyyy-MM-dd/HH:mm:ss.SSS} [%t] %x %-5p %c{2} - %m%n')
        environments {
            development {
                rollingFile name: "myAppender", maxFileSize: "1024KB", file: "/tmp/eguts.log"
                rollingFile name:'auditAppender',
                        file: "/tmp/audit.eguts.log",
                        maxFileSize:"1024KB", maxBackupIndex: 15,
                        layout: pattern(conversionPattern: '%d{yyyy-MM-dd HH:mm:ss.SSS} - %m%n')
            }
        }
    }

    info 'auditAppender' : 'auditLogger', additivity:false

    root {
        info 'stdout','myAppender'
    }

    error 'org.codehaus.groovy.grails.web.servlet',        // controllers
            'org.codehaus.groovy.grails.web.pages',          // GSP
            'org.codehaus.groovy.grails.web.sitemesh',       // layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping',        // URL mapping
            'org.codehaus.groovy.grails.commons',            // core / classloading
            'org.codehaus.groovy.grails.plugins',            // plugins
            'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    info "grails.app"
}

// Avoid assets minify
grails.assets.minifyJs = false
// Deep JSON render
grails.converters.json.default.deep = true


grails.plugins.localeConfiguration.supportedLocales = [
        new Locale("es-ES"),
        new Locale("es")
]
grails.plugins.localeConfiguration.defaultLocale = new Locale("es-ES")

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'models.user.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'models.user.UserRole'
grails.plugin.springsecurity.authority.className = 'models.user.Role'

grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.rejectIfNoRule = false
grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.plugin.springsecurity.securityConfigType = 'Annotation'
grails.plugin.springsecurity.securityConfigType = 'Annotation'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [

        'version'        : [SecurityConstants.PERMIT_ALL],
        '/login/**'      : [SecurityConstants.PERMIT_ALL],
        '/'              : [SecurityConstants.PERMIT_ALL],
        '/front'         : [SecurityConstants.PERMIT_ALL],
        '/front/**'      : [SecurityConstants.PERMIT_ALL],
        '/controls/**'   : [SecurityConstants.HAS_APP_ROLE],
        '/**View/**'     : [SecurityConstants.HAS_ADMIN_ROLE],
        '/**userView/**' : [SecurityConstants.HAS_SYS_ADMIN_ROLE],
        '/dbconsole/**'  : [SecurityConstants.HAS_ADMIN_ROLE],
        '/test'          : [SecurityConstants.HAS_ADMIN_ROLE],
        '/test/**'       : [SecurityConstants.HAS_ADMIN_ROLE],
        '/index'         : [SecurityConstants.PERMIT_ALL],
        '/index.gsp'     : [SecurityConstants.PERMIT_ALL],
        '/assets/**'     : [SecurityConstants.PERMIT_ALL],
        '/**/js/**'      : [SecurityConstants.PERMIT_ALL],
        '/**/css/**'     : [SecurityConstants.PERMIT_ALL],
        '/**/images/**'  : [SecurityConstants.PERMIT_ALL],
        '/**/favicon.ico': [SecurityConstants.PERMIT_ALL],

        //App Business Access
        '/**/show'       : [SecurityConstants.HAS_BACK_ABM_ROLE],
        '/**/save'    : [SecurityConstants.HAS_BACK_ABM_ROLE],
        '/**/update'    : [SecurityConstants.HAS_BACK_ABM_ROLE],
        '/**/delete'    : [SecurityConstants.HAS_BACK_ABM_ROLE],

        '/api/endPoint/index' : [SecurityConstants.PERMIT_ALL]

]

grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/'
grails.plugin.springsecurity.adh.errorPage = '/error/accessDenied'
grails.plugin.springsecurity.useSecurityEventListener = true

grails.plugin.databasemigration.updateOnStart = false
grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']

// APP properties
app.notifications.from = "info@egutsapp.com.ar"
app.notifications.reminderDays = 3

app.subscription.service.iterMins = 1 //The iteration process sleep time
app.subscription.refresh.iterMins = 1 //Time between same subscription refresh

