package com.example.demo.dto.user;

import com.example.demo.model.user.User;

public class faceIdResponse {

	
	private int statusCode;
	private String message;
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
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
	public faceIdResponse(User user, int statusCode, String message) {		
		this.user = user;
		this.statusCode = statusCode;
		this.message = message;
	}
	public faceIdResponse() {
		
	}	
	

}
