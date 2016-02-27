package models.config

class MatchCriteria {

    String value

    static constraints = {
        value(nullable: false, blank: false)
    }

}
