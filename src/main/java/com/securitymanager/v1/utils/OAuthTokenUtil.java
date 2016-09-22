package com.securitymanager.v1.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.DefaultOAuth2RequestAuthenticator;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.client.HttpClientErrorException;

import com.securitymanager.v1.configuration.SecurityManagerConstants;
import com.securitymanager.v1.model.AnalyticsUserDataModel;
import com.securitymanager.v1.model.SecurityManagerAccessToken;
import com.securitymanager.v1.model.SecurityManagerUserDetails;
import com.securitymanager.v1.service.ValidateUserRequest;

public class OAuthTokenUtil {
	

	public static SecurityManagerAccessToken generateAccessToken(AnalyticsUserDataModel analyticsUserDataModel,long expiryTimeInMilliSeconds)
	{
		SecurityManagerAccessToken securityManagerAccessToken=new SecurityManagerAccessToken();
		String encodedUserName=Base64.encodeBase64String(analyticsUserDataModel.getUserName().getBytes()) + Base64.encodeBase64String(SecurityManagerConstants.COLON.getBytes()) + Base64.encodeBase64String(analyticsUserDataModel.getFirstName().getBytes())
		+Base64.encodeBase64String(SecurityManagerConstants.COLON.getBytes()) + Base64.encodeBase64String(analyticsUserDataModel.getLastName().getBytes());
		
		/*DefaultOAuth2AccessToken oAuth2AccessToken=new DefaultOAuth2AccessToken(encodedUserName);
		Map<String, Object> additionalInformationMap=new HashMap<String, Object>();
		additionalInformationMap.put("First Name", analyticsUserDataModel.getFirstName());
		additionalInformationMap.put("Last Name", analyticsUserDataModel.getLastName());
		oAuth2AccessToken.setAdditionalInformation(additionalInformationMap);*/
		
		System.out.println("Now time:" + Instant.now().toEpochMilli());
		Instant instant=Instant.now().plusMillis(expiryTimeInMilliSeconds);
		System.out.println("Expiry Time: " + instant.toEpochMilli());
		Date expiryDate=new Date(instant.toEpochMilli());
		System.out.println("Date: " + expiryDate.toGMTString());
		
		securityManagerAccessToken.setAccessToken(encodedUserName);
		securityManagerAccessToken.setExpired(false);
		securityManagerAccessToken.setExpiryTime(instant.toEpochMilli());
		securityManagerAccessToken.setFirstName(analyticsUserDataModel.getFirstName());
		securityManagerAccessToken.setLastName(analyticsUserDataModel.getLastName());

		
		
		return securityManagerAccessToken;
		
	}
	
	public static SecurityManagerUserDetails validateAccessTokenAndGetContents(ValidateUserRequest validateUserRequest) throws Exception
	{
		SecurityManagerUserDetails securityManagerUserDetails=new SecurityManagerUserDetails();
		SecurityManagerAccessToken securityManagerAccessToken= validateUserRequest.getAccessToken();
		
		long currentTime=Instant.now().toEpochMilli();
		if(securityManagerAccessToken.getExpiryTime() < currentTime)
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED,"User session has expired!");
		
		if(!validateUserRequest.getUserFirstName().equalsIgnoreCase(securityManagerAccessToken.getFirstName()))
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED,"User not authorized!");
		
		if(!validateUserRequest.getUserLastName().equalsIgnoreCase(securityManagerAccessToken.getLastName()))
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED,"User not authorized!");
		
		
		securityManagerUserDetails.setFirstName(securityManagerAccessToken.getFirstName());
		securityManagerUserDetails.setLastName(securityManagerAccessToken.getLastName());
		securityManagerUserDetails.setTokenIsValid(true);
		return securityManagerUserDetails;
	}
}
