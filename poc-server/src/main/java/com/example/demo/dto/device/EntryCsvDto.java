package com.example.demo.dto.device;

/*
 * Download deviceLog to CSV 
 *  * */
public class EntryCsvDto {

	private String userId;   
    private String name;
    private String type;
    private float temperature;
    private String date;
    private String time;
    private String deviceId;
    private String location;
    private String TTCode;
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public String getTTCode() {
		return TTCode;
	}
	public void setTTCode(String tTCode) {
		TTCode = tTCode;
	}  
    
    
}
