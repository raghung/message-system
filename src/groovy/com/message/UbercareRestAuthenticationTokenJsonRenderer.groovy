package com.message

import com.odobo.grails.plugin.springsecurity.rest.RestAuthenticationToken
import com.odobo.grails.plugin.springsecurity.rest.oauth.OauthUser
import com.odobo.grails.plugin.springsecurity.rest.token.rendering.RestAuthenticationTokenJsonRenderer

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import groovy.util.logging.Slf4j

import org.pac4j.core.profile.CommonProfile
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.util.Assert

import com.security.User
import grails.converters.JSON

/**
 * Generates a JSON response like the following: <code>{"username":"john.doe","token":"1a2b3c4d","roles":["ADMIN","USER"]}</code>.
 * If the principal is an instance of {@link OauthUser}, also "email" ({@link CommonProfile#getEmail()}) and
 * "displayName" ({@link CommonProfile#getDisplayName()}) will be rendered
 */
@Slf4j
class UbercareRestAuthenticationTokenJsonRenderer implements RestAuthenticationTokenJsonRenderer {

    String generateJson(RestAuthenticationToken restAuthenticationToken) {
		
		Assert.isInstanceOf(UserDetails, restAuthenticationToken.principal, "A UserDetails implementation is required")
        UserDetails userDetails = restAuthenticationToken.principal as UserDetails
		def user = User.findByUsername(userDetails.username)
		
		def result = [
			access_token : restAuthenticationToken.tokenValue,
			token_type : 'Bearer',
			username : userDetails.username,
            roles : userDetails.authorities.collect {GrantedAuthority role -> role.authority },
			validated : user.validated,
			activated : user.activated
        ]

        if (userDetails instanceof OauthUser) {
            CommonProfile profile = (userDetails as OauthUser).userProfile
            result.with {
                email = profile.email
                displayName = profile.displayName
            }
        }

        def jsonResult = result as JSON

        log.debug "Generated JSON:\n${jsonResult.toString(true)}"

        return jsonResult.toString()
    }
}
