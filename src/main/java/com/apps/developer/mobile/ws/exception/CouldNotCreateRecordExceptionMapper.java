package com.apps.developer.mobile.ws.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.apps.developer.mobile.ws.model.response.ErrorMessage;
import com.apps.developer.mobile.ws.model.response.ErrorMessages;

@Provider
public class CouldNotCreateRecordExceptionMapper implements ExceptionMapper<CouldNotCreateRecordException> {

	
	public Response toResponse(CouldNotCreateRecordException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.RECORD_ALREADY_EXISTS.name(), "https://www.bts.com.tn/");

		
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}
}