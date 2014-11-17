class UrlMappings {

	static mappings = {

        "/api/secured"(controller: "Secured", action: "index")

        "/api/restaurants"(resources:'restaurant')

        "/api/restaurants/near"(controller:"restaurant",action:"near")

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
