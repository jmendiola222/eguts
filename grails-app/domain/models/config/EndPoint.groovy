package models.config

import interfaces.IEndPointParserStgy
import models.config.endPointParsers.EndPointJSONStgy

/**
 * Created by julian on 30/05/15.
 */
class EndPoint {

    String name
    String descriptor
    String target
    boolean isPublic = true
    private Class<IEndPointParserStgy> parseStrategyClass

    String emailTemplate

    public EndPointElement getElementSelector(){
        return endPointElements.find({ it.type == EndPointElementType.ELEMENT_SELECTOR })
    }

    public EndPointElement getElementID(){
        return endPointElements.find({ it.type == EndPointElementType.ELEMENT_ID })
    }

    static hasMany = [
        endPointElements: EndPointElement,
        urlMatchs: MatchCriteria
    ]

    static constraints = {

        name(nullable: false, blank: false)
        descriptor(nullable: true, blank: false)
        target(nullable: true, blank: false)
        emailTemplate(nullable: true, blank: false)
        endPointElements nullable: false, validator: { endPointElement, obj ->
            endPointElement.every { it.validate() }
            //TODO i18n
            def ids = endPointElement.findAll { it.type == EndPointElementType.ELEMENT_ID }.size()
            if(ids != 1)
                return ids == 0 ? ['endPoint.element.id.empty'] : ['endPoint.element.id.duplicated']
            def selectors = endPointElement.findAll { it.type == EndPointElementType.ELEMENT_SELECTOR }.size()
            if(selectors != 1)
                return selectors == 0 ? ['endPoint.element.selector.empty'] : ['endPoint.element.selector.duplicated']
        }
    }

    public IEndPointParserStgy getParseStrategy(){
        //TODO: create bean
        new EndPointJSONStgy()
    }
}
