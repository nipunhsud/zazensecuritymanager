package com.securitymanager.v1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.JsonNode;
import com.securitymanager.v1.service.LoginManagerRequest;
import com.securitymanager.v1.service.SecurityManagerService;
import com.securitymanager.v1.service.ValidateUserRequest;

@Controller
public class SecurityManagerController {

	@Autowired 
	private SecurityManagerService securityManagerService;
	
	protected Logger log=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LoginManagerRequestValidator loginManagerRequestValidator;
	
	@Autowired
	private ValidateUserRequestValidator validateUserRequestValidator;
	
	@InitBinder("loginManagerRequestValidator")
	public void initLoginManagerRequestValidatorBinder(WebDataBinder binder)
	{
		binder.setValidator(loginManagerRequestValidator);
	}
	
	@InitBinder("validateUserRequestValidator")
	public void initValidateUserRequestValidatorBinder(WebDataBinder binder)
	{
		binder.setValidator(validateUserRequestValidator);
	}
	
	@RequestMapping(value="/v1/auth/users",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public ResponseEntity<JsonNode> authenticateUser(HttpServletRequest httpServletRequest,@Valid @RequestBody LoginManagerRequest loginManagerRequest) throws Exception
	{
		log.info("Received request : {}",loginManagerRequest.getUserid());
		JsonNode jsonNode= securityManagerService.authenticateUser(loginManagerRequest);
		ResponseEntity<JsonNode> responseEntity=new ResponseEntity<JsonNode>(jsonNode, HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping(value="/v1/validate/users",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public ResponseEntity<JsonNode> validateUserAndGetTokenContents(HttpServletRequest httpServletRequest,@Valid @RequestBody ValidateUserRequest validateUserRequest) throws Exception
	{
		log.info("Received request : {}",validateUserRequest.getAccessToken());
		JsonNode jsonNode= securityManagerService.validateTokenAndGetContents(validateUserRequest);
		ResponseEntity<JsonNode> responseEntity=new ResponseEntity<JsonNode>(jsonNode, HttpStatus.OK);
		return responseEntity;
	}
}
