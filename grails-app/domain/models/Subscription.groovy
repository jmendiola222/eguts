package models

import helpers.DateUtils
import models.config.EndPoint
import models.user.User

class Subscription {

    User subscriber
    String url

    Date startDate
    Date endDate

    SubscriptionStatus status = SubscriptionStatus.PENDING

    EndPoint endPoint

    static constraints = {
        url(nullable: false, blank: false, url: true)
        subscriber(nullable: false)
        startDate(nullable:false)
        endDate(nullable:true)
    }

    def beforeInsert() {
        if(this.startDate == null)
            this.startDate = DateUtils.now()
    }
}

public enum SubscriptionStatus{
    PENDING,
    ACTIVE,
    PAUSED,
    CANCELED
}
