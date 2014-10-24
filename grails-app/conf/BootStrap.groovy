import com.security.Role;
import com.security.User;
import com.security.UserRole;

class BootStrap {

    def init = { servletContext ->
		def adminRole = Role.findByAuthority("ROLE_ADMIN")?:new Role(authority: 'ROLE_ADMIN').save(flush: true)
		def doctorRole = Role.findByAuthority("ROLE_DOCTOR")?:new Role(authority: 'ROLE_DOCTOR').save(flush: true)
		def staffRole = Role.findByAuthority("ROLE_STAFF")?:new Role(authority: 'ROLE_STAFF').save(flush: true)
  
		def testUser = User.findByUsername("me")?:new User(username: 'me', password: 'password').save(flush: true)
  
		UserRole.create testUser, adminRole, true
  
		//assert User.count() == 1
		//assert Role.count() == 2
		//assert UserRole.count() == 1
    }
    def destroy = {
    }
}
