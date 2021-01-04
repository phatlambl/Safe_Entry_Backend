package com.example.demo.dto.device;

/*
 * deviceLog: mean when user login/access device
 * 
 * */
public class DeviceLogDto {
	
    private String userId;   
    private String name;
    private String type;
    private float temperature;
    private Long timestamp;
    private String deviceId;
    private String location;  
    private String ttCode;

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
		return type;
	}

	public void setCardType(String cardType) {
		this.type = cardType;
	}

	public String getTtCode() {
		return ttCode;
	}

	public void setTtCode(String ttCode) {
		this.ttCode = ttCode;
	}

    
}
