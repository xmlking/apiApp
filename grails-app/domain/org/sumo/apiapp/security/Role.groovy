package org.sumo.apiapp.security

import org.bson.types.ObjectId

class Role {

	ObjectId id
	String authority

//	static mapping = {
//		cache true
//	}

	static constraints = {
		authority blank: false, unique: true
	}
}
