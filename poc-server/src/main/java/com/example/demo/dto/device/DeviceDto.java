package com.example.demo.dto.device;

import javax.validation.constraints.NotEmpty;

public class DeviceDto {
	
	@NotEmpty
	private String id;
	
	@NotEmpty
	private String Location;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public DeviceDto(String id, String location) {		
		this.id = id;
		Location = location;
	}
	public DeviceDto() {
		
	}

}
