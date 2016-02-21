import helpers.Const
import helpers.DateUtils
import models.Subscription
import models.SubscriptionStatus
import models.config.EndPointElementType
import models.user.Role
import models.user.User
import models.config.EndPoint
import models.config.EndPointElement
import models.user.UserProfile
import org.joda.time.DateTime

class BootStrap {

    def grailsApplication
    def subscriptionRunnerService

    def init = { servletContext ->

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
        def testUsr = User.findByUsername("test") ?: new User(email: 'test@eguts.com', username: 'test', password: 'test2014', enabled: true, role: userRole, userProfile: new UserProfile()).save(failOnError: true)

        //-------------------------

        def mercadoLibreEP = EndPoint.findByName("Mercado Libre");
        if(!mercadoLibreEP){
            mercadoLibreEP = new EndPoint(
                    name : "Mercado Libre",
                    target : "https://www.kimonolabs.com/api/d95rruok?apikey=Yj3HnrAbhZibWvWeozjQWHeDDdkvkhiv"
            );
            mercadoLibreEP.urlMatchs = new LinkedList<String>()
            mercadoLibreEP.urlMatchs.add(".*.mercadolibre.com.*");

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

        //runBackgroundService();
    }

    def destroy = {
        runBackgroundService = false;
        Thread.sleep(100);
    }

    private static boolean runBackgroundService = true;

    private void runBackgroundService(){

        Thread thread = new Thread()
        def iterMins = grailsApplication.config.app.subscription.service.iterMins;
        def iterMillis = iterMins * 60 * 60;
        def diffInMillis = 0;

        thread.start {
            while(runBackgroundService) {
                try {
                    def startTime = DateUtils.nowAsDateTime();
                    def sleepTime = Math.max(iterMillis - diffInMillis, 1);
                    Thread.sleep(sleepTime);
                    log.info("Subscription service start")
                    subscriptionRunnerService.run();
                    diffInMillis = DateUtils.nowAsDateTime().getMillis() - startTime.getMillis();
                    log.info("Subscription service end. Took(ms): " + diffInMillis)
                }catch(Exception ex){
                    log.error("Error in service iteration", ex)
                }
            }
        }
    }
}
