package helpers

class BootstrapService {

    def grailsApplication;

    def init(){

        grailsApplication.domainClasses.each { domainClass ->

            domainClass.metaClass.jsonPart = { m ->

                def recursiveCall
                recursiveCall = { objAsMap, obj, fields ->
                    def currentField = fields.head();
                    if (fields.size() == 1) {
                        def value = obj."${currentField}"
                        if (value instanceof Closure) {
                            value = value.call()
                        }
                        if (value instanceof java.util.Collection) {
                            objAsMap[currentField] = value.collect {
                                return it.jsonPart();
                            }
                        }else{
                            objAsMap[currentField] = value
                        }

                    } else {
                        if (!objAsMap.containsKey(currentField)) {
                            objAsMap[currentField] = [:]
                        }
                        def nextObj = obj."${currentField}"
                        if (nextObj instanceof java.util.Collection) {
                            def tail = fields.tail()
                            def tailFields = tail[0].toString().tokenize('-')
                            objAsMap[currentField] = nextObj.collect {
                                return it.jsonPart(include: tailFields);
                            }
                        } else {
                            if (nextObj != null) {
                                call(objAsMap[currentField], nextObj, fields.tail())
                            }
                        }
                    }
                }

                def map = [:]

                if (m.'include') {
                    m.'include'.each {
                        def tokenized = it.tokenize(".");
                        recursiveCall(map, delegate.delegate, tokenized)
                    }
                }
                return map
            }

        }
    }
}
