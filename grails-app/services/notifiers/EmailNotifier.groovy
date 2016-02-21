package notifiers

import models.result.SubscriptionResult
import org.springframework.stereotype.Component

/**
 * Created by julian on 06/06/15.
 */
@Component
class EmailNotifier implements INotifier{

    @Override
    def notify(SubscriptionResult subscriptionResult){
        //TODO
    }

}