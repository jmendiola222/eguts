package api

import grails.plugin.springsecurity.annotation.Secured
import helpers.RestSearchHelper
import org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder
import org.codehaus.groovy.grails.web.util.WebUtils

import javax.transaction.Transactional

import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED

class PaginableRestController<T> extends JsonRestfulController<T> {

    static String itemCountHeader = "itemsCount"
    static responseFormats = ['json']
    static notToQuery = [
            'sort',
            'page',
            'pageSize',
            'order',
            'fields'
    ]

    private RestSearchHelper restSearchHelper;

    PaginableRestController(Class<T> resource) {
        super(resource);
    }

    PaginableRestController(Class<T> resource, RestSearchHelper restSearchHelper) {
        super(resource)
        this.restSearchHelper = restSearchHelper;
    }

    @Override
    def index() {

        def queryString = [:]
        if (request.queryString != null) {
            queryString = WebUtils.fromQueryString(request.queryString)
        }
        this.doGetIndex(queryString)
    }

    def doGetIndex(queryString) {
        Integer page = params.page ? params.int("page") : 0;
        Integer max = params.pageSize ? params.int("pageSize") : 20

        Integer offset = (page - 1) * max;

        queryString = queryString.findAll({ key, value ->
            !notToQuery.contains(key)
        });

        def criteria = super.resource.createCriteria()
        def closure = restSearchHelper.queryFromQueryString(criteria, super.resource, queryString, offset, max, params.get("sort"), params.get("order"))
        def listed = criteria.list(closure)

        criteria = super.resource.createCriteria()
        def countClosure = restSearchHelper.countFromQueryString(criteria, super.resource, queryString)
        def itemsCount = criteria.get(countClosure)
        header "itemsCount", itemsCount

        String fields = params.get("fields");
        if (fields != null) {
            ok(listed*.jsonPart(include: fields.tokenize(',')))
        } else {
            ok(listed)
        }
    }

    @Override
    def show() {
        def obj = queryForResource(params.id)

        String fields = params.get("fields");
        if (fields != null) {
            ok(obj.jsonPart(include: fields.tokenize(',')))
        } else {
            ok(obj)
        }
    }


    @Override
    protected T createResource() {
        return createResource(request.JSON)
    }

    @Override
    protected Map getParametersToBind() {
        def requestAsJson = request.JSON
        return requestAsJson
    }

    @Transactional
    @Override
    def delete() {
        def parameters = getParametersToBind()
        fillToken(parameters)
        withForm {
            refreshToken()
            doDelete()
        }.invalidToken {
            response.status = METHOD_NOT_ALLOWED.value();
        }
    }

    def doDelete() {
        if (handleReadOnly()) {
            return
        }

        def instance = queryForResource(params.id)
        if (instance == null) {
            notFound()
            return
        }

        def isDeleteValid = isDeleteValid(instance)
        if (!isDeleteValid) {
            instance.delete flush: true
            ok();
        } else {
            error(isDeleteValid)
        }
    }

    @Transactional
    @Override
    def save() {
        def parameters = getParametersToBind()
        fillToken(parameters)
        withForm {
            refreshToken()
            doSave();
        }.invalidToken {
            response.status = METHOD_NOT_ALLOWED.value();
        }
    }

    @groovy.transform.CompileStatic
    def doSave() {
        super.save();
    }

    @Transactional
    @Override
    def update() {
        def parameters = getParametersToBind()
        fillToken(parameters)
        withForm {
            refreshToken()
            doUpdate()
        }.invalidToken {
            response.status = METHOD_NOT_ALLOWED.value();
        }
    }

    def doUpdate() {
        super.update();
    }

    //FIXME It could be an object
    protected String isDeleteValid(instance) {
        return null;
    }

    def fillToken(parameters) {
        webRequest.params[SynchronizerTokensHolder.TOKEN_KEY] = webRequest.getHeader(SynchronizerTokensHolder.TOKEN_KEY);
        webRequest.params[SynchronizerTokensHolder.TOKEN_URI] = webRequest.getHeader(SynchronizerTokensHolder.TOKEN_URI);
    }

    def refreshToken() {
        def tokensHolder = SynchronizerTokensHolder.store(session)
        def uri = webRequest.params[SynchronizerTokensHolder.TOKEN_URI]
        def uuid = tokensHolder.getCurrentTokens().get(uri)
        def newToken = '';
        if (uuid == null || uuid.size() == 0)
            newToken = tokensHolder.generateToken(uri)
        else
            newToken = uuid[uuid.size() - 1].toString()
        webRequest.response.addHeader(SynchronizerTokensHolder.TOKEN_KEY, newToken)
    }

    protected String message(String msg)
    {
        return msg;
    };
}
