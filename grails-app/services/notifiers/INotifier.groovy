package notifiers

import models.result.SubscriptionResult

/**
 * Created by julian on 06/06/15.
 */
interface INotifier {

    def notify(SubscriptionResult subscriptionResult);

}