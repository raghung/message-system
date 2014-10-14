package com.message

import com.security.AuthenticationToken;
import com.security.User;
import grails.converters.JSON;

import grails.plugin.springsecurity.annotation.Secured;
import grails.rest.RestfulController;

class RestResourceController extends RestfulController {

	def springSecurityService
	
	@Secured('permitAll')
    def index() {
		def result = User.findAll()

		def users = []
		for (u in result) {
			def user = [:]
			user.name = u.username
			user.id = u.id 
			users.add(user)
		}

		render (contentType: "application/json") {
			users
		}
		//respond result, [excludes: ['class']]
	}
	
	@Secured('ROLE_ADMIN')
	def giveme() {
		println "I am in"
		render "Checking"
	}
	
	@Secured('permitAll')
	def logout(){
		def resMap = [:]
		def currentUser = springSecurityService.currentUser
		
		if(currentUser) {
			def authObjects = AuthenticationToken.findAllByUsername(currentUser.username)
			authObjects.each{ auth->
				auth.delete(flush:true)
			}
			
			response.status = 200
			resMap = ["message":"Success"];
			
		}else{
		    response.status = 404
			resMap = ["message":"Failure"];
		}
		render resMap as JSON
	}
	
	@Secured('ROLE_ADMIN')
	def getUser(){
		def resMap = [:]
		def userobj = User.findAllByUsername(params.username)

		if(userobj){
			render userobj as JSON
			
		} else {
			resMap = ["message":"User ${params.username} not found."];
			response.status = 404
			render resMap as JSON
		}
	}

}
