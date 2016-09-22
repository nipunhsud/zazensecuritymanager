package com.securitymanager.v1.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securitymanager.v1.model.SecurityManagerAccessToken;

public class ValidateUserRequest {

	@JsonProperty("AccessToken")
	private SecurityManagerAccessToken  accessToken;
	
	@JsonProperty("FirstName")
	private String userFirstName;
	
	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	@JsonProperty("LastName")
	private String userLastName;

	public SecurityManagerAccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(SecurityManagerAccessToken accessToken) {
		this.accessToken = accessToken;
	}
	
}
