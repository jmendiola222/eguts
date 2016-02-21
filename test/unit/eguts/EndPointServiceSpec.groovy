package eguts

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import models.config.EndPoint
import models.config.EndPointElement
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(EndPointService)
@Mock(EndPoint)
class EndPointServiceSpec extends Specification {

    private static final String EP_NAME_1 = "Test1";
    private static final String EP_NAME_2 = "Test2";
    private static final String EP_NAME_3 = "Test3";

    private void mockEndPoint(name, urlMatchs){
        new EndPoint( name: name, urlMatchs: urlMatchs, elementId : new EndPointElement(name : "id"), elementSelector : new EndPointElement(name : "selector") ).save(failOnError: true)
    }

    def setup() {
        mockEndPoint(EP_NAME_1, ["aa.*", "bb.*"])
        mockEndPoint(EP_NAME_2, ["cc.*", "dd.*"])
        mockEndPoint(EP_NAME_3, ["ee.*", "ff.*"])
    }

    def cleanup() {
    }

    void "test getEndPointByUrl ok"() {
        when:
        def endPoint  = service.getEndPointByUrl("dd_test.com")
        then:
        endPoint.name.equals(EP_NAME_2)
    }


}
