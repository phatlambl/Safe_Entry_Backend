package com.example.demo.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

public class RegisterDto {
	
	@NotBlank
	private String id;
	
    private String email;
    
    private String username;
    
    private String password;
    
    @NotEmpty
    private String name;
    
    private String faceId;
  

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String face_id) {
		this.faceId = face_id;
	}

	public RegisterDto(String id, String email, String username, String password, String name) {		
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.name = name;
	}

	public RegisterDto() {
		
	}

	public RegisterDto(@NotBlank String id, String email, String username, String password, @NotEmpty String name,
			String faceId) {		
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.name = name;
		this.faceId = faceId;
	}
	

    


}
