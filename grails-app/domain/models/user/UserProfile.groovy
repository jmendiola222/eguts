package models.user

import grails.util.Holders
import notifiers.ConsoleNotifier
import notifiers.EmailNotifier
import notifiers.FileNotifier;
import notifiers.INotifier

class UserProfile {

    //UserProfileStatus status = UserProfileStatus.ACTIVE

    public List<INotifier> getNotifiers(){

        def result = new LinkedList<INotifier>()

        def myNotifier = Holders.grailsApplication.mainContext.getBean(ConsoleNotifier);
        result.add(myNotifier)
        myNotifier = Holders.grailsApplication.mainContext.getBean(FileNotifier);
        result.add(myNotifier)
        myNotifier = Holders.grailsApplication.mainContext.getBean(EmailNotifier);
        result.add(myNotifier)

        return result;
    }

    /*static hasMany = [notifierClass : Class<INotifier>]

    static mapping = {
        notifierClass cascade: 'all-delete-orphan'
    }
    */
}

public enum UserProfileStatus {
    ACTIVE,
    INACTIVE
}
