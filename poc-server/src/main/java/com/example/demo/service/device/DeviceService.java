package com.example.demo.service.device;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.device.DeviceDto;
import com.example.demo.dto.device.ListDeviceDto;
import com.example.demo.model.device.Device;
import com.example.demo.repository.device.DeviceRepository;

@Service
public class DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;
	
	public ListDeviceDto getListDevice(String sortBy, String order, int page, int pageSize) {
		List<Device> deviceList = new ArrayList<>();
		Pageable paging;
		

   	 if(order.equalsIgnoreCase("DESC"))
   	 {
   		 paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, sortBy) );     	
   	 }else {
   		 paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sortBy) );  				
   	 }
   	 
   	 
   	 Page<Device> listDevice = deviceRepository.findAll(paging);
   	 Long totalItems = listDevice.getTotalElements();
   	 for(Device device: listDevice) {
   		 deviceList.add(device);
   	 }
   	 
   	 ListDeviceDto listDeviceDto = new ListDeviceDto(totalItems, page, pageSize, deviceList);
   	 	
   	 return listDeviceDto;
	}
	
	
	public boolean saveDevice(DeviceDto deviceDto) {	
		
		try {
			Device device = new Device();
			device.setId(deviceDto.getId());
			device.setLocation(deviceDto.getLocation());	
			device.setFloor(deviceDto.getFloor());
			device.setRoom(deviceDto.getRoom());
			deviceRepository.save(device);
		} catch (Exception e) {
			return false;
		}		
		
		return true;
	}
	
	public boolean deleteDevice(String id) {
		try {
			deviceRepository.deleteDeviceById(id);
		} catch (Exception e) {
			return false;
		}
		return true;		
	}
	
}
