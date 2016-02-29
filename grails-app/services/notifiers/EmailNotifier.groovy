package notifiers

import helpers.MailNotifierHelper
import models.result.SubscriptionResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by julian on 06/06/15.
 */
@Component
class EmailNotifier implements INotifier{

    @Autowired
    MailNotifierHelper mailNotifierHelper

    @Override
    def notify(SubscriptionResult subscriptionResult){

        mailNotifierHelper.sendUserUpdates(subscriptionResult.getSubscription().getSubscriber(), subscriptionResult);
    }

}