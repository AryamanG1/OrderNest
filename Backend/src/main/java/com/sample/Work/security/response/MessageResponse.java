package com.sample.Work.security.response;

import java.util.Map;

public class MessageResponse {
	private String message;
	private Map<String,String> errors;

	public MessageResponse(String message) {
		this.message = message;
	}

	

	public MessageResponse(String message, Map<String, String> errors) {
		this.message = message;
		this.errors = errors;
	}


	
	public Map<String, String> getErrors() {
		return errors;
	}



	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
