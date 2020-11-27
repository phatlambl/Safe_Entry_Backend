package com.example.demo.dto.device;

import javax.validation.constraints.NotEmpty;

public class DeviceDto {
	
	@NotEmpty
	private String id;
	
	@NotEmpty
	private String location;
	
	private String floor;
	
	private String room;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}	
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public DeviceDto(@NotEmpty String id, @NotEmpty String location, String floor, String room) {
		this.id = id;
		this.location = location;
		this.floor = floor;
		this.room = room;
	}
	public DeviceDto() {
		
	}

}
