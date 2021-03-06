package org.sumo.apiapp.security

import org.bson.types.ObjectId

class User {

	transient springSecurityService

	ObjectId id
	String username
	String password
	String firstName
	String lastName
	String title
	String email
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	Set<Role> authorities
	static embedded = ['authorities']

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true, size: 2..32,matches: "[a-zA-Z0-9_]+"
		email blank: false, unique: true, email: true
		password blank: false, size: 6..64
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
