package com.apps.developer.mobile.ws.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.apps.developer.mobile.ws.model.response.ErrorMessage;
import com.apps.developer.mobile.ws.model.response.ErrorMessages;


@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException> {

	
	public Response toResponse(MissingRequiredFieldException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.MISSING_REQUIRED_FIELD.name(), "https://www.bts.com.tn/");

		
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}

}
 