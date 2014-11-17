// Place your Spring DSL code here

import grails.rest.render.json.JsonRenderer
import org.springframework.security.core.GrantedAuthority
import org.sumo.apiapp.security.MongoUserDetailsService
beans = {
    userDetailsService(MongoUserDetailsService)

    for (domainClass in grailsApplication.domainClasses) {
        "json${domainClass.shortName}Renderer"(JsonRenderer, domainClass.clazz) {
            excludes = ['class']
        }
    }
}
