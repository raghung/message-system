class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

		"/api/v1/$action?/$id?(.$format)?"(controller: "restResource")
		"/guest/v1/$action?/$id?(.$format)?"(controller: "restResource")
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
