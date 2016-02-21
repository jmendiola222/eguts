grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        mavenRepo "http://repo.grails.org/grails/core"
        mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://repository.codehaus.org/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        compile 'mysql:mysql-connector-java:5.1.29'
        compile 'commons-beanutils:commons-beanutils:1.8.3'

        compile "org.springframework:spring-orm:$springVersion"

        compile "edu.vt.middleware:vt-password:3.1.2"
        compile "edu.vt.middleware:vt-dictionary:3.0"
        compile "edu.vt.middleware:vt-crypt:2.2"

    }

    plugins {
        build ":tomcat:7.0.53"

        // plugins for the compile step
        compile ":scaffolding:2.1.0"
        compile ':cache:1.1.6'
        compile ":asset-pipeline:1.8.7"
        compile ":export:1.6"
        compile ':locale-configuration:1.0'
        compile ":spring-security-core:2.0-RC3"
        compile ":mail:1.0.5"
        //provided ":quartz:1.0.1"

        // plugins needed at runtime but not for compilation
        runtime ":hibernate4:4.3.5.3" // or ":hibernate:3.6.10.15"
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.1"
        runtime ":cors:1.1.6"
        runtime ':xframeoptions:1.0'

        // Uncomment these to enable additional asset-pipeline capabilities
        compile ":sass-asset-pipeline:1.7.4"

        compile ":bcrypt:1.0"

        compile ":rest-client-builder:2.0.0"
    }
}
