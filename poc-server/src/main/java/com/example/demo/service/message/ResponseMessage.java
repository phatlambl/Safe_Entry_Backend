package com.example.demo.service.message;

public class ResponseMessage {

	public int statusCode;
	
	public String message;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseMessage(int statusCode, String message) {		
		this.statusCode = statusCode;
		this.message = message;
	}

	public ResponseMessage() {
		
	}

	

	
	
	
	

	

}
