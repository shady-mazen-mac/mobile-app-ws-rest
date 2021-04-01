package com.apps.developer.mobile.ws.service;

import java.util.List;

import com.apps.developer.mobile.ws.dto.UserDto;

public interface UserService {
	
	public UserDto createUser(UserDto user);

	public UserDto getUser(String id);

	public UserDto getUserByUserName(String userName);
	
	List<UserDto> getUsers(int start, int limit);
    void updateUserDetails(UserDto userDetails);


}
