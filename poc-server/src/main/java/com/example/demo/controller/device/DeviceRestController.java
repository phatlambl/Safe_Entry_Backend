package com.example.demo.controller.device;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.demo.dto.device.DeviceDto;
import com.example.demo.dto.device.DeviceLogDto;
import com.example.demo.dto.device.DeviceLogListDto;
import com.example.demo.dto.device.DeviceLogSubmitDto;
import com.example.demo.dto.device.ListDeviceDto;
import com.example.demo.dto.user.UserTemperature;
import com.example.demo.model.device.DeviceLog;
import com.example.demo.model.user.User;
import com.example.demo.repository.device.DeviceLogRepository;
import com.example.demo.repository.device.DeviceRepository;
import com.example.demo.service.device.DeviceLogService;
import com.example.demo.service.device.DeviceService;
import com.example.demo.service.message.Message;
import com.example.demo.service.message.ResponseMessage;


@RestController
@RequestMapping("/rest/device/")
public class DeviceRestController {

    private final DeviceLogService deviceLogService;
    private final DeviceService deviceService;
    private final DeviceLogRepository deviceLogRepository;
    private final DeviceRepository deviceRepository;
    

    public DeviceRestController(DeviceLogService deviceLogService, DeviceService deviceService, DeviceLogRepository deviceLogRepository, DeviceRepository deviceRepository) {
        this.deviceLogService = deviceLogService;
        this.deviceService = deviceService;
		this.deviceLogRepository = deviceLogRepository;
		this.deviceRepository = deviceRepository;       
    }

    @RequestMapping(value = "submit", method = RequestMethod.POST)
    public Object submit(HttpServletResponse response,@Validated @RequestBody DeviceLogSubmitDto deviceLogSubmitDto, BindingResult result) {
    	if(result.hasErrors()) {    	
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.MISS_INFORMATION);
    		return reMessage;
    	}
    	
        if (deviceLogService.submitLog(deviceLogSubmitDto)) {
        	response.setStatus(HttpServletResponse.SC_OK);
          	
        	ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.SUCCESS);
        	return reMessage;
        }
       
        ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.SUBMIT_FAILURE);
        return reMessage;        
    }

   

    @RequestMapping(value = "list/log", method = RequestMethod.GET)
    public DeviceLogListDto listDeviceLog(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                          @RequestParam(name ="name", required = false, defaultValue = "") String name,
                                          @RequestParam(name ="deviceId", required = false, defaultValue = "") String deviceId,
                                          @RequestParam(name="fromTimestamp", required = false) Long fromTimestamp,
                                          @RequestParam(name="toTimestamp", required = false) Long toTimestamp,
                                          @RequestParam(name="sortBy", required = false, defaultValue = "timestamp") String sortBy,
                                          @RequestParam(name="order", required = false, defaultValue = "DESC") String order) {
	
		
        return deviceLogService.getListByFilter(name, deviceId, fromTimestamp, toTimestamp, sortBy, order, page, pageSize);
    }

    @RequestMapping(value = "list/user/temperature", method = RequestMethod.GET)
    public List<UserTemperature> listTemperatureOfUser(@RequestParam(name = "page", required = false, defaultValue = "0") int pageIndex,
                                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                       @RequestParam(name = "fromTimestamp", required = false) Long fromTimestamp,
                                                       @RequestParam(name = "toTimestamp", required = false) Long toTimestamp,
                                                       @RequestParam(name = "userId", required = false) String userId,
                                                       @RequestParam(name = "name", required = false) String name,
                                                       @RequestParam(name="deviceId", required = false) String deviceId) {    	
    	   	
        return deviceLogService.getListByTimeInterval(userId, fromTimestamp, toTimestamp, pageIndex, pageSize);
    }
    
    
    //get all list device_log by User (draw chart) no page
    @RequestMapping(value="list/user" , method = RequestMethod.GET)
    public List<DeviceLog> listDeviceLogByUser(@RequestParam(name = "fromTimestamp") Long fromTimestamp,
									           @RequestParam(name = "toTimestamp") Long toTimestamp,
									           @RequestParam(name = "userId") String userId){
    	
    	return deviceLogService.getListByTime(userId, fromTimestamp, toTimestamp);
    }
    
    //get list device by page
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ListDeviceDto listDevice(@RequestParam(name = "page", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   @RequestParam(name="sortBy", required = false, defaultValue = "location") String sortBy,
                                   @RequestParam(name="order", required = false, defaultValue = "ASC") String order) {
        return deviceService.getListDevice(sortBy, order, pageIndex, pageSize);
    }
    
    
    
    //add device
    @PostMapping(value="add")
    public Object addDevice(@Validated @RequestBody DeviceDto deviceDto, BindingResult result ) {
    	
    	if(result.hasErrors()) {
    		
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.MISS_INFORMATION);
    		return reMessage;
    	}
    	if(deviceRepository.findDeviceById(deviceDto.getId()) != null) {
    		if(deviceService.saveDevice(deviceDto)) {
        		
        		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.UPDATE_SUCCESS);
            	return reMessage;
        	}    
		}
    	if(deviceService.saveDevice(deviceDto)) {
    		
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.ADD_SUCCESS);
        	return reMessage;

    	}    	
    	
    	ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.ADD_FAILURE);
        return reMessage;        
    }
    
    
    
    //export device_log
    @GetMapping(value="/log/download")
    public void exportCsv(HttpServletResponse response) throws IOException{   
 	
 	   List<DeviceLogDto> listDeviceLogDto = new ArrayList<DeviceLogDto>();
 	   listDeviceLogDto = deviceLogService.getListDeviceLog();
 	  	 

 	   response.setContentType("text/csv");
 	   String fileName = "overview.csv";
 	   String headerKey = "Content-Disposition";
 	   String headerValue = "attachment; filename=" + fileName;
 	   
 	   response.setHeader(headerKey, headerValue);
 	   
 	   ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
 	   
 	   String[] csvHeader = {"UUID", "Name","CardType", "Temperature", "Timestamp", "deviceId", "Location" };
 	   String[] nameMapping = {"userId", "name", "cardType", "temperature", "timestamp" , "deviceId", "location"};
 	   
 	   csvWriter.writeHeader(csvHeader);
 	   for(DeviceLogDto deviceLog : listDeviceLogDto) { 		 
 		   csvWriter.write(deviceLog, nameMapping);
 	   }
 	   
 	   csvWriter.close();	
    }
    
    

    
}
