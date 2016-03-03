package models.config.endPointParsers

import dto.ResultItemDTO
import helpers.DateUtils
import interfaces.IEndPointParserStgy
import models.Subscription
import models.config.EndPointElementType
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

        def itemList = []

        def selector = subscription.getEndPoint().elementSelector.name
        def identifier = subscription.getEndPoint().elementID.name
        def items = json."${selector}"
        println "Items: " + items

        items.each {
            def itemId = it."${identifier}"
            println "Item Id: " + itemId
            itemList.push(itemId)
        }

        def result = new SubscriptionResult(
                data: items,
                subscription : subscription,
                lastStart: start,
                lastEnd: DateUtils.now(),
                status: SubscriptionResultStatus.OK,
                itemIds: itemList
        )
        result.data = items;
        return result;
    }

    @Override
    boolean loadResultDetails(SubscriptionResult subscriptionResult) {
        def resultItemDTOs = new LinkedList<ResultItemDTO>()
        def endpointElems = subscriptionResult.subscription.getEndPoint().endPointElements

        def identifier = subscriptionResult.subscription.getEndPoint().elementID.name
        def newElements = subscriptionResult.data.findAll {
            def id = it."${identifier}"
            subscriptionResult.updates.contains(id.toString())
        }

        newElements.each { it ->
            def item = new ResultItemDTO( id: it."${identifier}" )
            endpointElems.each { elem ->
                try{
                    switch (elem.type){
                        case  EndPointElementType.ELEMENT_ID:
                        case  EndPointElementType.ELEMENT_SELECTOR:
                            break;
                        case EndPointElementType.DESCRIPTOR:
                            item.extra.push(elem.name, it."${elem.name}")
                            break;
                        case EndPointElementType.LINK:
                        case EndPointElementType.NAME:
                        case EndPointElementType.PHOTO:
                        case EndPointElementType.PRICE:
                            item."${elem.type.toString().toLowerCase()}" = it."${elem.name}"
                            break;
                    }
                }catch (Exception ex){
                    log.error('Error parsing item detail.' + elem, ex)
                }
            }
            resultItemDTOs.add(item)
        }
        subscriptionResult.resultItemDTOs = resultItemDTOs;
    }
}