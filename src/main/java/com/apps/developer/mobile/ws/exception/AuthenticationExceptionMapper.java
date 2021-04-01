package com.apps.developer.mobile.ws.exception;

import javax.ws.rs.core.Response;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.apps.developer.mobile.ws.model.response.ErrorMessage;
import com.apps.developer.mobile.ws.model.response.ErrorMessages;


@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

	
	public Response toResponse(AuthenticationException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.AUTHENTICATION_FAILED.name(),"https://www.bts.com.tn/");

		
		return Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
	}
}