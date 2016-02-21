import helpers.DateUtils
import models.Subscription
import models.SubscriptionStatus
import models.result.SubscriptionResult
import models.user.User
import org.springframework.stereotype.Component

import javax.transaction.Transactional

/**
 * Created by julian on 30/05/15.
 */
@Component
class SubscriptionRunnerService {

    def grailsApplication

    public void run(){

        def iterMins = grailsApplication.config.app.subscription.refresh.iterMins;
        def subscriptions = Subscription.findAllWhere( status : SubscriptionStatus.ACTIVE )

        runFor(subscriptions, iterMins)
    }

    public void runNow(Subscription subscription){

        def subscriptions = [];
        subscriptions << subscription

        runFor(subscriptions)
    }

    private void runFor(def subscriptions, def iterMins = 0){

        for(Subscription subscription in subscriptions){
            //TODO: this should be in JobThread
            try{
                def currentResult = SubscriptionResult.findBySubscription(subscription);
                if(currentResult == null || DateUtils.minutesSince(currentResult.lastStart) > iterMins) {

                    def stgy = subscription.endPoint.getParseStrategy();
                    def subscriptionResult = stgy.getResult(subscription);

                    if (subscriptionResult.hasResults()) {
                        handleResult(stgy, currentResult, subscriptionResult);
                    }
                }
            }catch(Exception ex){
                log.error("Error", ex)
                //TODO hanldle Exception
            }
        }
    }

    @Transactional
    private handleResult(def stgy, SubscriptionResult currentResult, SubscriptionResult subscriptionResult){

        def finalResult = currentResult;
        if(currentResult == null){
            subscriptionResult.lastSuccessfulUpdate = DateUtils.now()
            subscriptionResult.save(failOnError: true)
            subscriptionResult.updates = subscriptionResult.getItemIds();
            finalResult = subscriptionResult;
        }else{
            handleSubscriptionUpdates(currentResult, subscriptionResult);
        }
        if(finalResult.updates?.size() > 0){
            stgy.loadResultDetails(finalResult)
            notify(finalResult);
        }
    }

    boolean handleSubscriptionUpdates(SubscriptionResult currentResult, SubscriptionResult subscriptionResult) {
        if(currentResult.sameItems(subscriptionResult))
            return false;
        currentResult.calculateUpdates(subscriptionResult);
        currentResult.save();
        return currentResult.updates.size() > 0
    }

    private void notify(SubscriptionResult subscriptionResult){
        User user = subscriptionResult.subscription.subscriber;
        for(def notifier in user.userProfile.notifiers){
            notifier.notify(subscriptionResult);
        }
    }

}
