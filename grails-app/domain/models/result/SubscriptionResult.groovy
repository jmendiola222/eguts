package models.result

import dto.ResultItemDTO
import helpers.DateUtils
import models.Subscription

/**
 * Created by julian on 30/05/15.
 */
class SubscriptionResult {

    String itemListHash
    Date lastStart
    Date lastEnd
    Date lastSuccessfulUpdate

    SubscriptionResultStatus status
    String description


    def data
    String[] updates
    List<ResultItemDTO> resultItemDTOs

    static hasMany = [itemIds: String]

    static transients = ['data','updates', 'resultItemDTOs']

    static constraints = {
        subscription(nullable: false)
        lastStart(nullable:false)
        lastEnd(nullable:false)
        lastSuccessfulUpdate(nullable:true)
        status(nullable: false)
        description(nullable: true)
        itemListHash(nullable: true)
        itemIds(nullable: true)
    }

    static belongsTo = [subscription : Subscription]

    public boolean hasResults(){
        return itemIds != null  && !itemIds.isEmpty();
    }

    def beforeValidate() {
        this.getItemListHash();
    }

    public getItemListHash(){
        if(this.itemListHash == null && this.itemIds != null)
            this.itemListHash = hashIds(this.itemIds)
        return this.itemListHash;
    }

    private String hashIds(def toHash){
        return toHash.join();
    }

    boolean sameItems(SubscriptionResult subscriptionResult) {
        this.itemListHash.equals(subscriptionResult.itemListHash);
    }

    def calculateUpdates(SubscriptionResult subscriptionResult) {
        def myItemList = this.getItemIds();
        def newItemList = subscriptionResult.getItemIds();
        this.updates = newItemList - myItemList;
        if(this.updates.size() > 0){
            this.setItemIds(newItemList)
            this.data = subscriptionResult.data
            this.lastSuccessfulUpdate = DateUtils.now();
        }
    }
}

public enum SubscriptionResultStatus{
    OK,
    ERROR,
}