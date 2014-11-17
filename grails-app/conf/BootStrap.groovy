import grails.converters.JSON
import org.bson.types.ObjectId
import org.sumo.apiapp.City
import org.sumo.apiapp.security.Role
import org.sumo.apiapp.security.User
import org.sumo.apiapp.Restaurant
import grails.mongodb.geo.Point
import com.mongodb.BasicDBObject

class BootStrap {

    def init = { servletContext ->
        // Check whether the test data already exists.
        if (!User.count()) {
            createUsers()
        }
        if (!Restaurant.count()) {
            createRestaurants()
        }

        JSON.registerObjectMarshaller(ObjectId) {
            return it?.toString() //it.toStringMongod()
        }
    }
    def destroy = {
    }

    private void createUsers() {
        def superAdminRole = new Role(authority: 'ROLE_SUPER_ADMIN')
        def switchUserRole = new Role(authority: 'ROLE_SWITCH_USER')
        def businessAdminRole = new Role(authority: 'ROLE_BUSINESS_ADMIN')
        def itAdminRole = new Role(authority: 'ROLE_IT_ADMIN')
        def dataAdminRole = new Role(authority: 'ROLE_DATA_ADMIN')
        def userRole = new Role(authority: 'ROLE_USER')

        new User(username: 'sumo', password: 'sumodemo',
                firstName: 'Sumanth', lastName: 'Chintha',
                title: 'IT Consultant', email: 'sumo@demo.com',
                authorities: [superAdminRole, userRole]).save(flush: true, failOnError: true)

        new User(username: 'businessadmin', password: 'businessadmin',
                firstName: 'Sumanth', lastName: 'Chintha',
                title: 'Business User', email: 'businessadmin@demo.com',
                authorities: [businessAdminRole, userRole]).save(flush: true)

        new User(username: 'dataadmin', password: 'dataadmin',
                firstName: 'Sumanth', lastName: 'Chintha',
                title: 'Data Admin', email: 'dataadmin@demo.com',
                authorities: [dataAdminRole, userRole]).save(flush: true)

        new User(username: 'itadmin', password: 'itadmin',
                firstName: 'Sumanth', lastName: 'Chintha',
                title: 'IT Admin', email: 'itadmin@demo.com',
                authorities: [itAdminRole, userRole]).save(flush: true)

        new User(username: 'baseuser', password: 'baseuser',
                firstName: 'Jeson', lastName: 'Chang',
                title: 'IT User', email: 'baseuser@demo.com',
                authorities: [userRole]).save(flush: true)
    }

    private void createRestaurants() {
        def london =  new City( name:"London", location: Point.valueOf([-0.125487, 51.508515]))
        def paris = new City( name:"Paris", location: Point.valueOf([2.352222, 48.856614]))
        def newYork = new City( name:"New York", location: Point.valueOf([-74.005973, 40.714353]))
        def sanFrancisco = new City( name:"San Francisco", location: Point.valueOf([-122.419416, 37.774929]))

        City.withTransaction {
            City.collection.remove(new BasicDBObject())
            City.saveAll([london,paris,newYork,sanFrancisco])
        }
        Restaurant.withTransaction {
            Restaurant.collection.remove(new BasicDBObject())
            Restaurant.saveAll([new Restaurant(name: "London Rest", city: london, location: Point.valueOf([-0.125487, 51.508515])),
                                new Restaurant(name: "Paris Rest", city: paris, location: Point.valueOf([2.352222, 48.856614])),
                                new Restaurant(name: "New York Rest", city: newYork, location: Point.valueOf([-74.005973, 40.714353])),
                                new Restaurant(name: "San Francisco Rest", city: sanFrancisco, location: Point.valueOf([-122.419416, 37.774929]))])
        }

    }
}
