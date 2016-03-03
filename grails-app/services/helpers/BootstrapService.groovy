package helpers

import models.Subscription
import models.SubscriptionStatus
import models.config.EndPoint
import models.config.EndPointElement
import models.config.EndPointElementType
import models.config.MatchCriteria
import models.user.Role
import models.user.User
import models.user.UserProfile

class BootstrapService {

    def grailsApplication;

    def dataInit() {
        def sysAdminRole = Role.findByAuthority(Const.ROLE_SYSADMIN) ?: new Role(authority: Const.ROLE_SYSADMIN, description: 'SysAdmin').save(failOnError: true)
        def adminRole = Role.findByAuthority(Const.ROLE_ADMIN) ?: new Role(authority: Const.ROLE_ADMIN, description: 'Administrador').save(failOnError: true)
        def userRole = Role.findByAuthority(Const.ROLE_USER) ?: new Role(authority: Const.ROLE_USER, description: 'User').save(failOnError: true)
        def subscriberRole = Role.findByAuthority(Const.ROLE_SUBSCRIBER) ?: new Role(authority: Const.ROLE_SUBSCRIBER, description: 'Subscriber').save(failOnError: true)

        def sysAdminUsr = User.findByUsername("sysadmin");
        if (sysAdminUsr == null) {
            sysAdminUsr = new User(email: 'sysadmin@eguts.com', username: 'sysadmin', password: 'eguts2014', enabled: true, role: sysAdminRole);
            sysAdminUsr.init = true;

            sysAdminUsr.save(failOnError: true, flush: true);
        }
        def adminUsr = User.findByUsername("admin") ?: new User(email: 'admin@eguts.com', username: 'admin', password: 'eguts2014', enabled: true, role: adminRole).save(failOnError: true)
        def testUsr = User.findByUsername("jmendiola") ?: new User(email: 'jmendiola222@gmail.com', username: 'jmendiola', password: 'test2014', enabled: true, role: userRole, userProfile: new UserProfile()).save(failOnError: true)

        //-------------------------

        def mercadoLibreEP = EndPoint.findByName("Mercado Libre");
        if(!mercadoLibreEP){
            mercadoLibreEP = new EndPoint(
                    name : "Mercado Libre",
                    target : "https://www.kimonolabs.com/api/d95rruok?apikey=Yj3HnrAbhZibWvWeozjQWHeDDdkvkhiv"
            )
            def matcher = new MatchCriteria(value: ".*.mercadolibre.com.*")
            mercadoLibreEP.addToUrlMatchs(matcher)

            mercadoLibreEP.endPointElements = [
                    new EndPointElement(type: EndPointElementType.ELEMENT_SELECTOR, name: "url", endPoint: mercadoLibreEP),
                    new EndPointElement(type: EndPointElementType.ELEMENT_ID, name: "url", endPoint: mercadoLibreEP),
                    new EndPointElement(type: EndPointElementType.NAME, name: "titulo.text", endPoint: mercadoLibreEP),
                    new EndPointElement(type: EndPointElementType.LINK, name: "url", endPoint: mercadoLibreEP),
                    //new EndPointElement(type: EndPointElementType.PHOTO, name: "c", endPoint: mercadoLibreEP),
                    //new EndPointElement(type: EndPointElementType.PRICE, name: "d", endPoint: mercadoLibreEP),
                    new EndPointElement(type: EndPointElementType.DESCRIPTOR, name: "anio", description : "anio", endPoint: mercadoLibreEP)
            ]
            mercadoLibreEP.save(failOnError: true)
        };

        String url = "http://autos.mercadolibre.com.ar/nissan/pathfinder/_YearRange_1992-2003_PriceRange_0-150000";
        def subs = Subscription.findBySubscriberAndUrl(testUsr, url)
        if(!subs){
            Subscription subscription = new Subscription(subscriber: testUsr, url: url, endPoint: mercadoLibreEP, status: SubscriptionStatus.ACTIVE, startDate: DateUtils.now())
            subscription.save(failOnError: true)
        }

        /********************************************/

        def localEP = EndPoint.findByName("Local");
        if(!localEP){
            localEP = new EndPoint(
                    name : "Local",
                    target : "http://www.jmendiola.com:8882/api/v1/ml-autos"
            )
            def matcher = new MatchCriteria(value: ".*.www.jmendiola.com:8882/api/v1/ml-autos.*")
            localEP.addToUrlMatchs(matcher)

            localEP.endPointElements = [
                    new EndPointElement(type: EndPointElementType.ELEMENT_SELECTOR, name: "items", endPoint: localEP),
                    new EndPointElement(type: EndPointElementType.ELEMENT_ID, name: "id", endPoint: localEP),
                    new EndPointElement(type: EndPointElementType.NAME, name: "title", endPoint: localEP),
                    //new EndPointElement(type: EndPointElementType.LINK, name: "url", endPoint: localEP),
                    //new EndPointElement(type: EndPointElementType.PHOTO, name: "c", endPoint: localEP),
                    //new EndPointElement(type: EndPointElementType.PRICE, name: "d", endPoint: localEP),
                    //new EndPointElement(type: EndPointElementType.DESCRIPTOR, name: "anio", description : "anio", localEP: mercadoLibreEP)
            ]
            localEP.save(failOnError: true)
        };

        url = "http://www.jmendiola.com:8882/api/v1/ml-autos-vw-gol";
        subs = Subscription.findBySubscriberAndUrl(testUsr, url)
        if(!subs){
            Subscription subscription = new Subscription(subscriber: testUsr, url: url, endPoint: localEP, status: SubscriptionStatus.ACTIVE, startDate: DateUtils.now())
            subscription.save(failOnError: true)
        }
    }

    def init(){

        dataInit()

        grailsApplication.domainClasses.each { domainClass ->

            domainClass.metaClass.jsonPart = { m ->

                def recursiveCall
                recursiveCall = { objAsMap, obj, fields ->
                    def currentField = fields.head();
                    if (fields.size() == 1) {
                        def value = obj."${currentField}"
                        if (value instanceof Closure) {
                            value = value.call()
                        }
                        if (value instanceof java.util.Collection) {
                            objAsMap[currentField] = value.collect {
                                return it.jsonPart();
                            }
                        }else{
                            objAsMap[currentField] = value
                        }

                    } else {
                        if (!objAsMap.containsKey(currentField)) {
                            objAsMap[currentField] = [:]
                        }
                        def nextObj = obj."${currentField}"
                        if (nextObj instanceof java.util.Collection) {
                            def tail = fields.tail()
                            def tailFields = tail[0].toString().tokenize('-')
                            objAsMap[currentField] = nextObj.collect {
                                return it.jsonPart(include: tailFields);
                            }
                        } else {
                            if (nextObj != null) {
                                call(objAsMap[currentField], nextObj, fields.tail())
                            }
                        }
                    }
                }

                def map = [:]

                if (m.'include') {
                    m.'include'.each {
                        def tokenized = it.tokenize(".");
                        recursiveCall(map, delegate.delegate, tokenized)
                    }
                }
                return map
            }

        }
    }
}
