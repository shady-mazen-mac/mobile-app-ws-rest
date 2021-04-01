package com.apps.developer.mobile.ws.entrypoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.apps.developer.mobile.ws.dto.UserDto;
import com.apps.developer.mobile.ws.model.request.LoginCredentials;
import com.apps.developer.mobile.ws.model.response.AuthenticationDetails;
import com.apps.developer.mobile.ws.service.AuthenticationService;
import com.apps.developer.mobile.ws.service.Implementation.AuthenticationServiceImpl;

@Path("/authentication")
public class AuthenticationEntryPoint {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public AuthenticationDetails userLogin(LoginCredentials loginCredentials) {

		AuthenticationDetails returnValue = new AuthenticationDetails();

		AuthenticationService authenticationService = new AuthenticationServiceImpl();
		UserDto authenticatedUser = authenticationService.authenticate(loginCredentials.getUserName(),
				loginCredentials.getUserPassword());
		// reset Access Token
		authenticationService.restSecurityCridentials(loginCredentials.getUserPassword(), authenticatedUser);
		String accessToken = authenticationService.issueAcessToken(authenticatedUser);
		returnValue.setId(authenticatedUser.getUserId());
		returnValue.setToken(accessToken);

		return returnValue;

	}

}
