package org.sumo.apiapp

import grails.converters.JSON
import grails.mongodb.geo.Distance
import grails.mongodb.geo.Metric
import grails.rest.RestfulController
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_USER'])
//@Secured(value=["hasRole('ROLE_USER')"])
class RestaurantController extends RestfulController<Restaurant> {

    static scaffold = true
//    static responseFormats = ['html', 'json', 'xml', 'hal']
//    static allowedMethods = [show: "GET"]

    RestaurantController() {
        super(Restaurant, true) //readOnly
    }

    @Override
    def index(Integer pageSize) {
        params.pageSize = Math.min(pageSize ?: 10, 100)
         respond resource.list(params),
                 [includes: includeFields, excludes: ['class', 'errors', 'version'],
                  meta: [total: countResources(), pageSize: params.pageSize, offset: params.offset?:0, facets:[]],
                  model: [("${resourceName}InstanceCount".toString()): countResources()]]
    }

    def show(Restaurant restaurantInstance) {
        respond restaurantInstance,
                [includes: includeFields, excludes: ['class', 'errors', 'version']]
    }

    //@RequestMapping(value="/near/{cityName}", method = GET)
    def near(String cityName, double maxDistance) {
        def city = City.where { name == cityName }.find()
        List<Restaurant> closest = Restaurant.findAllByLocationNear(city.location,new Distance(maxDistance,Metric.MILES) )
        respond closest ? closest : NOT_FOUND
    }

    private getIncludeFields() {
        params.fields?.tokenize(',')
    }

}
