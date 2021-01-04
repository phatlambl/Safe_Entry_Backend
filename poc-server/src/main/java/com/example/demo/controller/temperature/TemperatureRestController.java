package com.example.demo.controller.temperature;


import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.temperature.EmailSettingDto;
import com.example.demo.dto.temperature.TemperatureConfigDto;
import com.example.demo.model.conf.EmailConfig;
import com.example.demo.service.device.TemperatureConfService;
import com.example.demo.service.message.Message;
import com.example.demo.service.message.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/*
 * Setting Controller
 * Configure temperature
 * */
@RestController
@RequestMapping(value = "/rest/temperature/")
public class TemperatureRestController {

	@Autowired
    private TemperatureConfService temperatureConfService;
	
    @RequestMapping(value = "config", method = RequestMethod.POST)
    public Object config(HttpServletResponse response,@Validated @RequestBody TemperatureConfigDto temperatureConfigDto, BindingResult result) {
    	if(result.hasErrors()) {    		
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.MISS_INFORMATION);
        	return reMessage;    		 
    	}	
    	
//    	for(String i : temperatureConfigDto.getEmails()) {
//    		System.out.println(i);
//    	}
    	
        if (temperatureConfService.Config(temperatureConfigDto)) {        	
        	ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.SUCCESS_CONFIG);
        	return reMessage;           
        }
        ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.FAIL_CONFIG);
        return reMessage;
    }
    
    
    /*
     * function update, setup email
     * */
    @RequestMapping(value="update", method = RequestMethod.PUT)
    public Object update(@Validated @RequestBody EmailSettingDto emailSetting, BindingResult result) {
    	if(result.hasErrors()) {
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST,
					Message.MISS_INFORMATION);
			return reMessage;
    	}
    	if(temperatureConfService.update(emailSetting)) {
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.UPDATE_SUCCESS);
			return reMessage;
    	}    	
    	
    	ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.UPDATE_FAILURE);
		return reMessage;
    }
    
	@RequestMapping(value = "config/list", method = RequestMethod.GET)
	public PaginationDto listSetting(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "email") String sortBy,
			@RequestParam(name = "order", required = false, defaultValue = "DESC") String order) {

		return temperatureConfService.getListSetting(sortBy, order, page, pageSize);
	}
    
    /*
     * function delete email configure
     * */
    @RequestMapping(value="delete/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable(value="id") int id) {
    	if(temperatureConfService.delete(id)) {
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.DELETE_SUCCESS);
			return reMessage;
    	}else {
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.DELETE_FAILURE);
			return reMessage;
		}
    }
       
    
    
    
    
    
    
}
