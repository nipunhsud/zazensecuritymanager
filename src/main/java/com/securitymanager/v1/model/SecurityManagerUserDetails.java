package com.securitymanager.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecurityManagerUserDetails {

	public boolean isTokenIsValid() {
		return tokenIsValid;
	}

	public void setTokenIsValid(boolean tokenIsValid) {
		this.tokenIsValid = tokenIsValid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("TokenValid")
	private boolean tokenIsValid;
	
	@JsonProperty("FirstName")
	private String firstName;
	
	@JsonProperty("LastName")
	private String lastName;
	
}
