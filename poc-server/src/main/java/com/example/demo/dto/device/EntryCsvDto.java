package com.example.demo.dto.device;

import java.util.Date;

/*
 * Download deviceLog to CSV 
 *  * */
public class EntryCsvDto {

	private String userId;   
    private String name;
    private String cardType;
    private float temperature;
    private Date timestamp;
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
