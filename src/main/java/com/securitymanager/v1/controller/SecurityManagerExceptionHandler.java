package com.securitymanager.v1.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityManagerExceptionHandler {

	@Autowired 
	ObjectMapper objectMapper;
	
	@ExceptionHandler
	public @ResponseBody ResponseEntity<JsonNode> exceptionHandler(HttpServletRequest httpServletRequest,Exception ex){
		ResponseEntity<JsonNode> response=null;
		if(ex instanceof HttpClientErrorException)
		{
			HttpClientErrorException httpClientErrorException=(HttpClientErrorException)ex;
			response=new ResponseEntity<JsonNode>(generateErrorMessage(httpClientErrorException.getStatusText()),httpClientErrorException.getStatusCode());
		}
		return response;
	}
	
	private JsonNode generateErrorMessage(String errorMessage)
	{
		return objectMapper.convertValue(errorMessage, JsonNode.class);
	}
}
