/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package api

import grails.artefact.Artefact
import grails.converters.JSON
import grails.transaction.Transactional
import grails.util.GrailsNameUtils
import org.codehaus.groovy.grails.web.servlet.HttpHeaders
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.*

/**
 * Base class that can be extended to get the basic CRUD operations needed for a RESTful API.
 *
 * @author Graeme Rocher
 * @since 2.3
 * modification by jmendiola
 */
@Artefact("Controller")
@Transactional(readOnly = true)
class JsonRestfulController<T> {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    Class<T> resource
    String resourceName
    String resourceClassName
    boolean readOnly

    JsonRestfulController(Class<T> resource) {
        this(resource, false)
    }

    JsonRestfulController(Class<T> resource, boolean readOnly) {
        this.resource = resource
        this.readOnly = readOnly
        resourceClassName = resource.simpleName
        resourceName = GrailsNameUtils.getPropertyName(resource)
    }

    /**
     * Lists all resources up to the given maximum
     *
     * @param max The maximum
     * @return A list of resources
     */
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond listAllResources(params), model: [("${resourceName}Count".toString()): countResources()]
    }

    /**
     * Shows a single resource
     * @param id The id of the resource
     * @return The rendered resource or a 404 if it doesn't exist
     */
    def show() {
        jsonOk(queryForResource(params.id))
    }

    /**
     * Displays a form to create a new resource
     */
    def create() {
        if(handleReadOnly()) {
            return
        }
        jsonOk(createResource())
    }

    /**
     * Saves a resource
     */
    @Transactional
    def save() {
        if(handleReadOnly()) {
            return
        }
        def instance = createResource()

        instance.validate()
        if (instance.hasErrors()) {
            errors(instance.errors)
            return
        }

        instance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: "${resourceName}.label".toString(), default: resourceClassName), instance.id])
                jsonOk(instance)
            }
            '*' {
                response.addHeader(HttpHeaders.LOCATION,
                        g.createLink(
                                resource: this.controllerName, action: 'show',id: instance.id, absolute: true,
                                namespace: hasProperty('namespace') ? this.namespace : null ))
                response.setStatus(CREATED.value())
                render instance as JSON
            }
        }
    }

    def edit() {
        if(handleReadOnly()) {
            return
        }
        jsonOk(queryForResource(params.id))
    }

    /**
     * Updates a resource for the given id
     * @param id
     */
    @Transactional
    def update() {
        if(handleReadOnly()) {
            return
        }

        T instance = queryForResource(params.id)
        if (instance == null) {
            notFound()
            return
        }

        instance.properties = getParametersToBind()

        if (instance.hasErrors()) {
            errors(instance.errors)
            return
        }

        beforeUpdate(instance)

        instance.save flush:true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: "${resourceClassName}.label".toString(), default: resourceClassName), instance.id])
                jsonOk(instance)
            }
            '*'{
                response.addHeader(HttpHeaders.LOCATION,
                        g.createLink(
                                resource: this.controllerName, action: 'show',id: instance.id, absolute: true,
                                namespace: hasProperty('namespace') ? this.namespace : null ))

                jsonOk(instance)
            }
        }
    }

    /**
     * Deletes a resource for the given id
     * @param id The id
     */
    @Transactional
    def delete() {
        if(handleReadOnly()) {
            return
        }

        def instance = queryForResource(params.id)
        if (instance == null) {
            notFound()
            return
        }

        instance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: "${resourceClassName}.label".toString(), default: resourceClassName), instance.id])
                redirect action:"index", method:"GET"
            }
            '*'{
                response.setStatus(NO_CONTENT.value())
            } // NO CONTENT STATUS CODE
        }
    }

    /**
     * handles the request for write methods (create, edit, update, save, delete) when controller is in read only mode
     *
     * @return true if controller is read only
     */
    protected boolean handleReadOnly() {
        if(readOnly) {
            response.setStatus(METHOD_NOT_ALLOWED.value())
            return true
        } else {
            return false
        }
    }

    /**
     * The parameters that can be bound to a domain instance. Defaults to all, subclasses should override and customize the behavior
     *
     * @return The parameters
     */
    protected Map getParametersToBind() {
        params
    }

    /**
     * Queries for a resource for the given id
     *
     * @param id The id
     * @return The resource or null if it doesn't exist
     */
    protected T queryForResource(Serializable id) {
        resource.get(id)
    }

    /**
     * Creates a new instance of the resource for the given parameters
     *
     * @param params The parameters
     * @return The resource instance
     */
    protected T createResource(Map params) {
        resource.newInstance(params)
    }

    /**
     * Creates a new instance of the resource.  If the request
     * contains a body the body will be parsed and used to
     * initialize the new instance, otherwise request parameters
     * will be used to initialized the new instance.
     *
     * @return The resource instance
     */
    protected T createResource() {
        T instance = resource.newInstance()
        bindData instance, request
        instance
    }

    /**
     * List all of resource based on parameters
     *
     * @return List of resources or empty if it doesn't exist
     */
    protected List<T> listAllResources(Map params) {
        resource.list(params)
    }

    /**
     * Counts all of resources
     *
     * @return List of resources or empty if it doesn't exist
     */
    protected Integer countResources() {
        resource.count()
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: '${propertyName}.label', default: '${className}'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{
                response.setStatus(NOT_FOUND.value())
            }
        }
    }

    /************* ADITIONALS ****************/

    protected void beforeUpdate(instance){ }

    protected void ok(String message = ""){
        render(status: HttpStatus.OK, text: message)
    }

    protected void jsonOk(Object toJSON){
        response.status = HttpStatus.OK.value()
        render toJSON as JSON
    }

    protected void error(String error){
        Object errorArray = [errors: Arrays.asList([message: error])]
        errors(errorArray)
    }

    protected void errors(Object errors){
        response.status = HttpStatus.BAD_REQUEST.value();
        render errors as JSON
    }
}
