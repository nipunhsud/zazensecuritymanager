package com.securitymanager.v1.service;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.securitymanager.v1.model.SecurityManagerAccessToken;
import com.securitymanager.v1.model.SecurityManagerUserDetails;

public class LoginManagerResponse {

	public SecurityManagerAccessToken getSecurityManagerAccessToken() {
		return securityManagerAccessToken;
	}
	public void setSecurityManagerAccessToken(SecurityManagerAccessToken securityManagerAccessToken) {
		this.securityManagerAccessToken = securityManagerAccessToken;
	}
	@JsonProperty("ResponseMessge")
	private String message;
	
	@JsonProperty("AccessToken")
	private SecurityManagerAccessToken securityManagerAccessToken;
	
	@JsonProperty("UserDetails")
	private SecurityManagerUserDetails securityManagerUserDetails;
	
	public SecurityManagerUserDetails getSecurityManagerUserDetails() {
		return securityManagerUserDetails;
	}
	public void setSecurityManagerUserDetails(SecurityManagerUserDetails securityManagerUserDetails) {
		this.securityManagerUserDetails = securityManagerUserDetails;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
