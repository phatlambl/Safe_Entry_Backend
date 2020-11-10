package com.example.demo.dto.device;

public class DeviceLogDto {
	
    private String userId;   
    private String name;
    private String cardType;
    private float temperature;
    private Long timestamp;
    private String deviceId;
    private String location;  

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
    public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

    
}
