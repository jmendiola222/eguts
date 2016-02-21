package interfaces

import models.Subscription
import models.config.EndPoint
import models.result.SubscriptionResult

/**
 * Created by julian on 30/05/15.
 */
interface IEndPointParserStgy {

    public SubscriptionResult getResult(Subscription subscription);

    public boolean loadResultDetails(SubscriptionResult SubscriptionResult);

}