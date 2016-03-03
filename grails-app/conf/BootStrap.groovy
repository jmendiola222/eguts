import helpers.DateUtils

class BootStrap {

    def bootstrapService
    def grailsApplication
    def subscriptionRunnerService

    def init = { servletContext ->

        bootstrapService.init();

        runBackgroundService();
    }

    def destroy = {
        runBackgroundService = false;
        Thread.sleep(100);
    }

    private static boolean runBackgroundService = true;

    private void runBackgroundService(){

        Thread thread = new Thread()
        def iterMins = grailsApplication.config.app.subscription.service.iterMins;
        def iterMillis = iterMins * 60 * 1000;
        def diffInMillis = 0;

        thread.start {
            while(runBackgroundService) {
                try {
                    def sleepTime = Math.max(iterMillis - diffInMillis, 1);
                    Thread.sleep(sleepTime);
                    def startTime = DateUtils.nowAsDateTime();
                    log.info("Subscription service start")
                    subscriptionRunnerService.run();
                    diffInMillis = DateUtils.nowAsDateTime().getMillis() - startTime.getMillis();
                    log.info("Subscription service end. Took(sec): " + (diffInMillis / 1000))
                }catch(Exception ex){
                    log.error("Error in service iteration", ex)
                }
            }
        }
    }
}
