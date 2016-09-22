package com.securitymanager.v1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.JsonNode;
import com.securitymanager.v1.service.CreateUserRequest;
import com.securitymanager.v1.service.SecurityManagerService;

@Controller	
public class CreateUserController {

	protected Logger log=LoggerFactory.getLogger(getClass());
	
	@Autowired 
	private SecurityManagerService securityManagerService;
	
	@Autowired
	private CreateUserRequestValidator createUserRequestValidator;
	
	@InitBinder
	public void initiBinder(WebDataBinder webDataBinder)
	{
		webDataBinder.setValidator(createUserRequestValidator);
	}
	
	@RequestMapping(value="/v1/signup/users", method=RequestMethod.POST,consumes="application/json",produces="application/json")
	public ResponseEntity<JsonNode> createAnalyticsUser(HttpServletRequest httpServletRequest,@Valid @RequestBody CreateUserRequest createUserRequest) throws Exception
	{
		log.info(createUserRequest.getLoginId() + ":" + createUserRequest.getUserFirstName());
		JsonNode jsonNode=securityManagerService.createAnalyticsUser(createUserRequest);
		return new ResponseEntity<JsonNode>(jsonNode,HttpStatus.OK);
	}
	
}
