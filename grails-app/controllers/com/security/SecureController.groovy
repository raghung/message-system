package com.security

import grails.plugin.springsecurity.annotation.Secured;

class SecureController {
	@Secured(['ROLE_ADMIN'])
    def index() { 
		render 'Secure Access Only'
	}
}
