package com.securitymanager.v1.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpClientErrorException;

import com.securitymanager.v1.service.CreateUserRequest;

@Component
public class CreateUserRequestValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		
		return CreateUserRequest.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		if(errors.hasErrors())
			return;
		CreateUserRequest createUserRequest=(CreateUserRequest)target;
		validateUserId(createUserRequest.getLoginId());
		validatePassword(createUserRequest.getPassword());
		validateFirstName(createUserRequest.getUserFirstName());
		
		
	}
	
	private void validateUserId(String userId)
	{
		if(StringUtils.isBlank(userId))
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"LoginId is a required field");
	}
	
	private void validatePassword(String password)
	{
		if(StringUtils.isBlank(password))
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Password is a required field");
	}
	
	private void validateFirstName(String fisrtName)
	{
		if(StringUtils.isBlank(fisrtName))
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"First name is a required field");
	}
	

}
