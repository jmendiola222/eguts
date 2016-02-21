package models.config.endPointParsers

import interfaces.IEndPointParserStgy
import models.Subscription
import models.result.SubscriptionResult
import models.result.SubscriptionResultStatus

/**
 * Created by julian on 30/05/15.
 */
class EndPointHTMLStgy implements IEndPointParserStgy{

    @Override
    public SubscriptionResult getResult(Subscription subscription){
        //TODO
        String itemList = "1,2,3,4,5,6,7,8,9";
        return new SubscriptionResult(
                subscription : subscription,
                itemList: itemList,
                status: SubscriptionResultStatus.OK
        )
    }

    @Override
    boolean loadResultDetails(SubscriptionResult SubscriptionResult) {
        return true;
    }
}