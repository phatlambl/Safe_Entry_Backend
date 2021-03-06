package com.example.demo.dto.device;

import javax.validation.constraints.NotNull;

public class DeviceLogSubmitDto {
	
	@NotNull
    private String deviceId;
	
	@NotNull
    private String userId;
    
	@NotNull
	private float temperature;
	
	@NotNull
    private Long timestamp;    
	
    private String type;
	
	private String ttCode;
	
	private String location;
	
	private String floor;
	
	private String room;
	
	private String name;
	
    public String getTtCode() {
		return ttCode;
	}

	public void setTtCode(String ttCode) {
		this.ttCode = ttCode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}
