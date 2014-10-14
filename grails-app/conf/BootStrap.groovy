import com.security.Role;
import com.security.User;
import com.security.UserRole;

class BootStrap {

    def init = { servletContext ->
		def adminRole = Role.findByAuthority("ROLE_ADMIN")?:new Role(authority: 'ROLE_ADMIN').save(flush: true)
		def userRole = Role.findByAuthority("ROLE_ADMIN")?:new Role(authority: 'ROLE_USER').save(flush: true)
  
		def testUser = User.findByUsername("me")?:new User(username: 'me', password: 'password').save(flush: true)
  
		UserRole.create testUser, adminRole, true
  
		//assert User.count() == 1
		//assert Role.count() == 2
		//assert UserRole.count() == 1
    }
    def destroy = {
    }
}
