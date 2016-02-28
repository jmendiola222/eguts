package models.config.endPointParsers

import helpers.DateUtils
import interfaces.IEndPointParserStgy
import models.Subscription
import models.result.SubscriptionResult
import models.result.SubscriptionResultStatus
import org.springframework.stereotype.Component

/**
 * Created by julian on 30/05/15.
 */
@Component
class EndPointJSONStgy implements IEndPointParserStgy{

    def rest

    @Override
    public SubscriptionResult getResult(Subscription subscription){

        def start = DateUtils.now();

        def resp = rest.get(subscription.url){
            contentType "application/json"
        }
        def json = resp.json

        log.info json

        //TODO
        def now = DateUtils.nowAsDateTime();
        String[] itemList = ["1","2","3", now.secondOfDay, now.secondOfMinute];
        def result = new SubscriptionResult(
                subscription : subscription,
                lastStart: start,
                lastEnd: DateUtils.now(),
                status: SubscriptionResultStatus.OK,
                itemIds: itemList
        )
        return result;
    }

    @Override
    boolean loadResultDetails(SubscriptionResult SubscriptionResult) {
        return true;
    }
}