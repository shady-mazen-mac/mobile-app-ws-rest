package com.apps.developer.mobile.ws.dao;

import java.util.List;

import com.apps.developer.mobile.ws.dto.UserDto;

public interface DAO {

	void openConnection();

	UserDto getUserByUserName(String userName);

	void closeConnection();

	UserDto getUser(String id);
	List<UserDto> getUsers(int start, int limit);
	UserDto saveUser(UserDto user);

	void updateUser(UserDto userProfile);

}
