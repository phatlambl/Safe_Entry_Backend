package com.example.demo.dto.user;

public class JwtResponse {

	private final String jwtToken;
	private final int statusCode;
	private final String message;

	public JwtResponse(String jwttoken, int statusCode, String message) {
		this.jwtToken = jwttoken;
		this.statusCode = statusCode;
		this.message = message;
	}

	public String getJwttoken() {
		return jwtToken;
	}	

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}
	
	

}
