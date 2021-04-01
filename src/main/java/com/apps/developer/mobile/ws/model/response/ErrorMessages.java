package com.apps.developer.mobile.ws.model.response;

public enum ErrorMessages {
	
	
	MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
	RECORD_ALREADY_EXISTS("ALREADY USER"),
	INTERNAL_SERVER_ERROR("Internal Server Error"),
	NO_RECORD_FOUND("A record is not find "), 
	AUTHENTICATION_FAILED("Authentication Failed"), 
    COULD_NOT_UPDATE_RECORD("Could not update record");

	private String errorMessage;
	
	ErrorMessages(String errorMessage){
	
	this.errorMessage=errorMessage;}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	  
	
	
}
