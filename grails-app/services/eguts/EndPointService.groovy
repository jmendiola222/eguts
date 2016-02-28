package eguts

import grails.transaction.Transactional
import models.config.EndPoint

@Transactional
class EndPointService {

    def getEndPointByUrl(String url) {
        return EndPoint.findAll().findResult {
            it.urlMatchs?.any { url.matches(it.value) } ? it : null
        }
    }
}
