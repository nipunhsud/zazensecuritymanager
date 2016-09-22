package com.securitymanager.v1.service;

import java.sql.SQLIntegrityConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.securitymanager.v1.configuration.SecurityManagerConstants;
import com.securitymanager.v1.dao.CreateUserDAO;
import com.securitymanager.v1.dao.LoginManagerDAO;
import com.securitymanager.v1.model.AnalyticsUserDataModel;
import com.securitymanager.v1.model.SecurityManagerAccessToken;
import com.securitymanager.v1.model.SecurityManagerUserDetails;
import com.securitymanager.v1.utils.OAuthTokenUtil;

@Service
public class SecurityManagerService {

	protected Logger log=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LoginManagerDAO loginManagerDAO;
	
	@Autowired
	private CreateUserDAO createUserDAO;
	
	@Value("${access.token.expirytime.inmilliseconds}")
	private long tokenExpiryTimeInMilliSeconds;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public JsonNode authenticateUser(LoginManagerRequest loginManagerRequest) throws Exception {
		JsonNode jsonNode = null;
		AnalyticsUserDataModel analyticsUserDataModel=loginManagerDAO.authenticateUser(loginManagerRequest.getPassword(),loginManagerRequest.getUserid());
		if(analyticsUserDataModel == null){
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"User doesn't exist!");
		}
		SecurityManagerAccessToken securityAccessToken=OAuthTokenUtil.generateAccessToken(analyticsUserDataModel, tokenExpiryTimeInMilliSeconds);
		jsonNode=generateResponseNode(analyticsUserDataModel,securityAccessToken);
		return jsonNode;
	}
	
	
	public JsonNode validateTokenAndGetContents(ValidateUserRequest validateUserRequest) throws Exception
	{
		JsonNode jsonNode=null;
		SecurityManagerUserDetails securityManagerUserDetails=OAuthTokenUtil.validateAccessTokenAndGetContents(validateUserRequest);
		jsonNode=generateResponseNodeForTokenContents(securityManagerUserDetails);
		return jsonNode;
	}
	
	public JsonNode createAnalyticsUser(CreateUserRequest createUserRequest) throws Exception
	{
		JsonNode jsonNode=null;
		try{
			int responseCode=createUserDAO.createAnalyticsUser(createUserRequest);
			jsonNode=generateResponseNodeForCreateUser();
		}
		catch(Exception ex)
		{
			if(ex instanceof DuplicateKeyException)
			{
				log.error(ex.getMessage());
				throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "User Id is already exists!");
			}
			else
				throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		
		return jsonNode;
	}
	
	private JsonNode generateResponseNode(AnalyticsUserDataModel analyticsUserDataModel,SecurityManagerAccessToken securityAccessToken)
	{
		JsonNode jsonNode;
		LoginManagerResponse loginManagerResponse=new LoginManagerResponse();
		loginManagerResponse.setSecurityManagerAccessToken(securityAccessToken);
		loginManagerResponse.setMessage(SecurityManagerConstants.SUCCESSMESSAGE);
		jsonNode=objectMapper.convertValue(loginManagerResponse, JsonNode.class);
		return jsonNode;
	}
	
	
	private JsonNode generateResponseNodeForCreateUser()
	{
		JsonNode jsonNode;
		LoginManagerResponse loginManagerResponse=new LoginManagerResponse();
		
		loginManagerResponse.setMessage(SecurityManagerConstants.SUCCESSMESSAGECREATEUSER);
		jsonNode=objectMapper.convertValue(loginManagerResponse, JsonNode.class);
		return jsonNode;
	}
	
	private JsonNode generateResponseNodeForTokenContents(SecurityManagerUserDetails securityManagerUserDetails)
	{
		JsonNode jsonNode;
		LoginManagerResponse loginManagerResponse=new LoginManagerResponse();
		
		loginManagerResponse.setMessage(SecurityManagerConstants.SUCCESSMESSAGE);
		loginManagerResponse.setSecurityManagerUserDetails(securityManagerUserDetails);
		jsonNode=objectMapper.convertValue(loginManagerResponse, JsonNode.class);
		return jsonNode;
	}
}
