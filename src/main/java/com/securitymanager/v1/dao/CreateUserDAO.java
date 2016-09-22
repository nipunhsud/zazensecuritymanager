package com.securitymanager.v1.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import com.securitymanager.v1.service.CreateUserRequest;

public interface CreateUserDAO {
	
	final String INSERT_STRING="INSERT INTO ANALYTICS_USER (username, password, first_name, last_name, email)"
			+ " values (#{loginId},sha1(#{password}),#{userFirstName},#{userLastName},#{userEmail})";

	@Insert(INSERT_STRING)
	@Options(useGeneratedKeys=true,keyProperty="id",flushCache=true)
	int createAnalyticsUser(CreateUserRequest createUserRequest);
}
