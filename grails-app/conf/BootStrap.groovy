import org.sumo.apiapp.security.Role
import org.sumo.apiapp.security.User

class BootStrap {

    def init = { servletContext ->
        def role = new Role(authority: 'ROLE_USER').save()
        new User(username: 'admin', password: 'password',
                email: 'example@email.com', authorities: [role]).save(flush:true)
    }
    def destroy = {
    }
}
