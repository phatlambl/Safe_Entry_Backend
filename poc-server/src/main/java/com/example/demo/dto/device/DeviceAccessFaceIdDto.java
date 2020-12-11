package com.example.demo.dto.device;

import javax.validation.constraints.NotEmpty;

public class DeviceAccessFaceIdDto {
	
	@NotEmpty
	private String faceId;

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	
}
