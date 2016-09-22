package com.securitymanager.v1.dao;

import org.apache.ibatis.annotations.Results;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.securitymanager.v1.model.AnalyticsUserDataModel;

public interface LoginManagerDAO {

	@Select("Select username,first_name,last_name from analytics_user where password=sha1(#{password}) and username=#{username};")
	 @Results(value={@Result(property= "userName", column ="username"),
		      @Result(property = "firstName", column = "first_name"),
		      @Result(property = "lastName", column = "last_name")
	 })
	AnalyticsUserDataModel authenticateUser(@Param(value = "password") String password,@Param(value="username") String username);
	

}
