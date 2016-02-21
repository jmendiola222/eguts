package models.config

/**
 * Created by julian on 30/05/15.
 */
class EndPointElement {

    String name
    String description
    EndPointElementType type

    static belongsTo = [endPoint: EndPoint]

    static constraints = {

        name(nullable: false, blank: false)
        description(nullable: true, blank: false)
        type(nullable: false)
        endPoint(nullable: false, unique: false)
    }
}

enum EndPointElementType{
    ELEMENT_SELECTOR, //Indicates the class or path to identify all items
    ELEMENT_ID, //it the element that indicated the item id field
    NAME, //Name or title of the item
    LINK, //Link to the item itself
    PHOTO, //Element with a photo link of the item
    PRICE, //Price of the element
    DESCRIPTOR, //Adds description to the item
}
