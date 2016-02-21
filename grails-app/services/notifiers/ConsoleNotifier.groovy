package notifiers

import models.result.SubscriptionResult
import org.springframework.stereotype.Component

/**
 * Created by julian on 06/06/15.
 */
@Component
class ConsoleNotifier implements INotifier {

    @Override
    def notify(SubscriptionResult subscriptionResult){
        printf("Subscription's notifications (%s): %s\n\n",  subscriptionResult.id, subscriptionResult.description)
        for(def item in subscriptionResult.getUpdates()){
            printf("\tNew Item: %s\n", item);
        }
    }

}