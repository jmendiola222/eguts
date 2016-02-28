package notifiers

import helpers.DateUtils
import models.result.SubscriptionResult
import org.springframework.stereotype.Component

/**
 * Created by julian on 06/06/15.
 */
@Component
class FileNotifier implements INotifier {

    @Override
    def notify(SubscriptionResult subscriptionResult){

        File file = new File("notifications.log")

        FileWriter fileWriter = new FileWriter(file, true)
        BufferedWriter buffWriter = new BufferedWriter(fileWriter)

        def dateStr = DateUtils.nowAsDateTime().toString("yyyy-MM-dd hh:mm:ss")
        buffWriter.write ("[" + dateStr + "] Subscription's notifications (id= " + subscriptionResult.id + "):\n")

        for(def item in subscriptionResult.getUpdates()){
            buffWriter.write ( "\tNew Item: " + item + "\n")
        }
        buffWriter.write ("\n")

        buffWriter.flush()
        buffWriter.close()
    }
}