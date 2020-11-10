package com.example.demo.dto;

import com.example.demo.dto.device.DeviceLogDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.user.UserTemperature;
import com.example.demo.model.device.DeviceLog;
import com.example.demo.model.user.User;

import org.springframework.stereotype.Service;

@Service
public class Map {
    public DeviceLogDto deviceLogDto(DeviceLog deviceLog) {
        DeviceLogDto deviceLogDto = new DeviceLogDto();
        
        deviceLogDto.setUserId(deviceLog.getUser().getId());
        deviceLogDto.setName(deviceLog.getUser().getName());
        deviceLogDto.setCardType(deviceLog.getCardType());
        deviceLogDto.setTimestamp(deviceLog.getTimestamp());
        deviceLogDto.setDeviceId(deviceLog.getDevice().getId());
        deviceLogDto.setLocation(deviceLog.getDevice().getLocation());
        deviceLogDto.setTemperature(deviceLog.getTemperature());        
        
        return deviceLogDto;
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
