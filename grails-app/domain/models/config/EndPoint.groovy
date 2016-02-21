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
    List<String> urlMatchs

    String emailTemplate

    public EndPointElement getElementSelector(){
        return endPointElements.find({ type : EndPointElementType.ELEMENT_ID})
    }

    public EndPointElement getElementID(){
        return endPointElements.find({ type : EndPointElementType.ELEMENT_SELECTOR})
    }

    static hasMany = [
        endPointElements: EndPointElement,
    ]

    static constraints = {

        name(nullable: false, blank: false)
        descriptor(nullable: true, blank: false)
        target(nullable: true, blank: false)
        emailTemplate(nullable: true, blank: false)
        endPointElements nullable: false, validator: { endPointElement, obj ->
            endPointElement.every { it.validate() }
            if(endPointElement.findAll { it.type == EndPointElementType.ELEMENT_ID }.size() != 1)
                return false;
            if(endPointElement.findAll { it.type == EndPointElementType.ELEMENT_SELECTOR }.size() != 1)
                return false;
        }
    }

    public IEndPointParserStgy getParseStrategy(){
        //TODO: create bean
        new EndPointJSONStgy()
    }
}
