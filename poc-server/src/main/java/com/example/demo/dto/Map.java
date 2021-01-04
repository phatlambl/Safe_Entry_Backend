package com.example.demo.dto;

import com.example.demo.dto.device.DeviceLogDto;
import com.example.demo.dto.device.EntryCsvDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.user.UserTemperature;
import com.example.demo.model.device.DeviceLog;
import com.example.demo.model.user.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

@Service
public class Map {
    public DeviceLogDto deviceLogDto(DeviceLog deviceLog) {
        DeviceLogDto deviceLogDto = new DeviceLogDto();
        if(deviceLog.getUser() == null) {
        	deviceLogDto.setUserId("User is deleted");
        }else {
        	deviceLogDto.setUserId(deviceLog.getUser().getId());
		}
        if(deviceLog.getDevice() == null) {
        	deviceLogDto.setDeviceId("Device is deleted");        	
        }else {
        	deviceLogDto.setDeviceId(deviceLog.getDevice().getId());
		}
        deviceLogDto.setName(deviceLog.getName());
        deviceLogDto.setCardType(deviceLog.getType());
        deviceLogDto.setTimestamp(deviceLog.getTimestamp());
        deviceLogDto.setTtCode(deviceLog.getTtCode());        
        deviceLogDto.setLocation(deviceLog.getLocation());
        deviceLogDto.setTemperature(deviceLog.getTemperature());        
        
        return deviceLogDto;
    }  
   
      
    public EntryCsvDto entryCsvDto(DeviceLog deviceLog, String timezone) {
    	EntryCsvDto entryCsvDto = new EntryCsvDto();    	
    	if(deviceLog.getUser()== null) {
    		entryCsvDto.setUserId("null");
    	}else {
    		entryCsvDto.setUserId(deviceLog.getUser().getId());
		}
    	if(deviceLog.getDevice() == null) {
    		entryCsvDto.setDeviceId("null");
    	}else {
    		entryCsvDto.setDeviceId(deviceLog.getDevice().getId());
		}    	
    	entryCsvDto.setName(deviceLog.getName());
    	entryCsvDto.setType(deviceLog.getType());	
    	entryCsvDto.setLocation(deviceLog.getLocation());
    	entryCsvDto.setTemperature(deviceLog.getTemperature());    	
    	
    	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    	dateFormatter.setTimeZone(TimeZone.getTimeZone(timezone));
    	String Date = dateFormatter.format(new Date(deviceLog.getTimestamp()));    	
    	entryCsvDto.setDate(Date);
    	
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    	timeFormat.setTimeZone(TimeZone.getTimeZone(timezone));
    	String time =timeFormat.format(new Date(deviceLog.getTimestamp()));
    	
    	entryCsvDto.setTime(time);    	
    	entryCsvDto.setTTCode(deviceLog.getTtCode());
    	
    	return entryCsvDto;
    }

    public UserTemperature userTemperature(DeviceLog deviceLog){
        UserTemperature userTemperature = new UserTemperature();
        userTemperature.setUserId(deviceLog.getUser().getId());
        userTemperature.setName(deviceLog.getUser().getName());
        userTemperature.setTemperature(deviceLog.getTemperature());
        userTemperature.setTimestamp(deviceLog.getTimestamp());
        return userTemperature;
    }
    

    public UserDto userDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
    
    
}
