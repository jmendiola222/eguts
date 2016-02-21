package models.user

import grails.util.Holders
import notifiers.ConsoleNotifier;
import notifiers.INotifier

class UserProfile {

    public List<INotifier> getNotifiers(){
        def myNotifier = Holders.grailsApplication.mainContext.getBean(ConsoleNotifier);
        def result = new LinkedList<INotifier>()
        result.add(myNotifier)
        return result;
    }

    /*static hasMany = [notifierClass : Class<INotifier>]

    static mapping = {
        notifierClass cascade: 'all-delete-orphan'
    }
    */

}
