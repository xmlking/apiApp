package org.sumo.apiapp

import grails.mongodb.geo.Point
import grails.rest.Resource
import org.bson.types.ObjectId

class Restaurant {
    ObjectId id
    String name
    City city
    Point location

    static constraints = {
        name blank:false
        city nullable:true
        location nullable:false
    }

    static mapping = {
        location geoIndex:'2dsphere'
    }
}