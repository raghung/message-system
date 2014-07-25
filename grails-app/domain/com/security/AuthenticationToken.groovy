package com.security

class AuthenticationToken {

	String tokenValue
	String username
	
    static constraints = {
		tokenValue blank:false
		username blank:false
    }
}
