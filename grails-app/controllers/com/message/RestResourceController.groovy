package com.message

import com.security.AuthenticationToken;
import com.security.Role;
import com.security.User;
import com.security.UserRole;

import grails.converters.JSON;
import grails.plugin.springsecurity.annotation.Secured;
import grails.rest.RestfulController;

class RestResourceController extends RestfulController {

	static allowedMethods = [logout:'POST', getUser:'GET',
							 getOrganizations:'GET', getSecQuestions:'GET',
							 validateUsername:'GET',
							 validateUser:'POST', registerUser:'POST',
							 getPersonalImage:'GET',
							 needVerification:'GET']
	
	def springSecurityService
	
	@Secured('permitAll')
    def index() {
		/*def result = User.findAll()

		def users = []
		for (u in result) {
			def user = [:]
			user.name = u.username
			user.id = u.id 
			users.add(user)
		}

		render (contentType: "application/json") {
			users
		}*/
		//respond result, [excludes: ['class']]
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
			def user = [:]
			user.name = userobj.username
			user.id = userobj.id
			render user as JSON
			
		} else {
			resMap = ["message":"User ${params.username} not found."];
			response.status = 404
			render resMap as JSON
		}
	}
	
	@Secured('permitAll')
	def getOrganizations() {
		def lst = [["id":1, "value":"Solo Practice"],
				   ["id":2, "value":"KEKH School of Medicine"],
				   ["id":3, "value":"Norris Cancer Research"],
				   ["id":4, "value":"UCLA Medical Center"]]
		
		render lst as JSON
	}

	@Secured('permitAll')
	def getSecQuestions() {
		def lst = [
			["id":1, "value": "What is the first and last name of your first boyfriend or girlfriend?"],
			["id":2, "value":"Which phone number do you remember most from your childhood?"],
			["id":3, "value":"What was your favorite place to visit as a child?"],
			["id":4, "value":"Who is your favorite actor, musician, or artist?"],
			["id":5, "value":"What is the name of your favorite pet?"],
			["id":6, "value":"In what city were you born?"],
			["id":7, "value":"What high school did you attend?"],
			["id":8, "value":"What is the name of your first school?"],
			["id":9, "value":"What is your favorite movie?"],
			["id":10, "value":"What is your mother's maiden name?"],
			["id":11, "value":"What street did you grow up on?"],
			["id":12, "value":"What was the make of your first car?"],
			["id":13, "value":"When is your anniversary?"],
			["id":14, "value":"What is your favorite color?"],
			["id":15, "value":"What is your father's middle name?"],
			["id":16, "value":"What is the name of your first grade teacher?"],
			["id":17, "value":"What was your high school mascot?"],
			["id":18, "value":"Which is your favorite web browser?"]
		]
		
		render lst as JSON
	}
	
	@Secured('permitAll') 
	def getUserTypes() {
		def lst = [["id":1, "value":"Doctor"],["id":2, "value":'Staff']]
 
		render lst as JSON
	}
	
	@Secured('permitAll') 
	def validateUsername() {
		def resMap = ["result":"not exist"]
		if (User.findByUsername(params.username)) {
			resMap = ["result":"exist"]
		}
		
		render resMap as JSON
	}
	
	@Secured('permitAll')
	def validateUser() {
		
		def var = JSON.parse(request)
		
		def missing = []
		if (!var.username) {
			missing << "username"
		}
		if (!var.password) {
			missing << "password"
		}
		if (!var.firstname) {
			missing << "firstname"
		}
		if (!var.lastname) {
			missing << "lastname"
		}
		if (!var.dob) {
			missing << "dob"
		}
		if (!var.zip) {
			missing << "zip"
		}
		if (!var.phone) {
			missing << "phone"
		}
		if (!var.firstname) {
			missing << "org"
		}
		if (!var.email) {
			missing << "email"
		}
		
		def resMap = ""
		if (missing.size() == 0) {
			def user = new User(username: var.username, 
				                password: var.password).save(flush: true, failOnError: true)
			UserRole.create(user, Role.findByAuthority("ROLE_DOCTOR"), true)
			
			resMap = ["heading":"Select your Graduation place",
					  "entries":[1:"Kekh School of Medicine, CA",
						  		 2:"Mysore Medical College, India",
								 3:"UCLA School of Medicine, CA"],
					  "valid":1]
			
		} else {
			resMap = ["required": missing]
			response.status = 404
		}
		
		render resMap as JSON
	}
	
	@Secured('permitAll')
	def registerUser() {
		def var = JSON.parse(request)
		
		def user = User.findByUsername(var.username)
		
		def resMap = ["result":"successful"]
		if (user) {
			user.personalImage = var.personalImage
			user.save(flush: true, failOnError: true)
		} else {
			resMap = ["result":"unsuccessful"]
		}
		
		render resMap as JSON
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_DOCTOR'])
	def getPersonalImage() {
		def currentUser = springSecurityService.currentUser
		
		def resMap = ["result": currentUser.personalImage]
		render resMap as JSON
		
	}
	
	@Secured('permitAll')
	def needVerification() {
				
		def resMap = ["result": params.username + " will be verified"]
		render resMap as JSON
		
	}
}
