package com.securitymanager.v1.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpClientErrorException;
import com.securitymanager.v1.service.LoginManagerRequest;

@Component
public class LoginManagerRequestValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return LoginManagerRequest.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {
		if(errors.hasErrors()){
			return;
		}
		LoginManagerRequest loginManagerRequest=(LoginManagerRequest)obj;
		validateUserId(loginManagerRequest.getUserid());
		validatePassword(loginManagerRequest.getPassword());
	}
	
	private void validateUserId(String userId)
	{
		boolean isEmpty=StringUtils.isEmpty(userId);
		if(isEmpty)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"User Id is a required field");
	}
	
	private void validatePassword(String password)
	{
		boolean isEmpty=StringUtils.isEmpty(password);
		if(isEmpty)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Password is a required field");
	}

}
