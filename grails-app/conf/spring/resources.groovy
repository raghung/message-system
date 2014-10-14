import grails.rest.render.json.JsonCollectionRenderer;
import grails.rest.render.json.JsonRenderer;
import grails.rest.render.xml.XmlCollectionRenderer;

import com.security.User;

// Place your Spring DSL code here
beans = {
	jsonUserCollectionRenderer(JsonCollectionRenderer, User) {
		excludes = ['class', 'password']
	}
	jsonUserRenderer(JsonRenderer, User) {
		excludes = ['class']
	}
	
	// Uncomment the following to register collection renderers
	// for all domain classes in the application.
	// for (domainClass in grailsApplication.domainClasses) {
	//     "json${domainClass.shortName}CollectionRenderer(JsonCollectionRenderer, domainClass.clazz)
	//     "xml${domainClass.shortName}CollectionRenderer(XmlCollectionRenderer, domainClass.clazz)
	// }
}
