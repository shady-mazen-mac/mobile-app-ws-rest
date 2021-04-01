package com.apps.developer.mobile.ws.service;

import com.apps.developer.mobile.ws.dto.UserDto;
import com.apps.developer.mobile.ws.exception.AuthenticationException;

public interface AuthenticationService {

	UserDto authenticate(String userName, String password) throws AuthenticationException;
	String issueAcessToken(UserDto userProfile) throws AuthenticationException;
	public void restSecurityCridentials(String password, UserDto userProfile);
}
