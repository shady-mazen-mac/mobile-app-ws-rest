package com.apps.developer.mobile.ws.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.apps.developer.mobile.ws.model.response.ErrorMessage;
import com.apps.developer.mobile.ws.model.response.ErrorMessages;

@Provider
public class GenericExceptionMapper  implements  ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable exception) {
		
			ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.INTERNAL_SERVER_ERROR.name(), "https://www.bts.com.tn/");

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}

	
	
	
}
