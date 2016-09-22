package com.securitymanager.v1.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginManagerRequest {

	@JsonProperty("UserId")
	private String userid;
	
	@JsonProperty("Password")
	private String password;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}
