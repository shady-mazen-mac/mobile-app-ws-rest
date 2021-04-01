package com.apps.developer.mobile.ws.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.apps.developer.mobile.ws.model.response.ErrorMessage;
import com.apps.developer.mobile.ws.model.response.ErrorMessages;

/**
 *
 * @author sergeykargopolov
 */
@Provider
public class CouldNotUpdateRecordExceptionMapper implements ExceptionMapper<CouldNotUpdateRecordException>{

    public Response toResponse(CouldNotUpdateRecordException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                ErrorMessages.COULD_NOT_UPDATE_RECORD.name(), "https://www.bts.com.tn/");
        
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
    }
    
}

