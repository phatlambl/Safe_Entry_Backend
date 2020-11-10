package com.example.demo.controller.temperature;


import com.example.demo.dto.temperature.TemperatureConfigDto;
import com.example.demo.model.conf.EmailConfig;
import com.example.demo.service.device.TemperatureConfService;
import com.example.demo.service.message.Message;
import com.example.demo.service.message.ResponseMessage;

import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/rest/temperature/")
public class TemperatureRestController {

    private final TemperatureConfService temperatureConfService;

    public TemperatureRestController(TemperatureConfService temperatureConfService) {
        this.temperatureConfService = temperatureConfService;
    }

    @RequestMapping(value = "config", method = RequestMethod.POST)
    public Object config(HttpServletResponse response,@Validated @RequestBody TemperatureConfigDto temperatureConfigDto, BindingResult result) {
    	if(result.hasErrors()) {
    		
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.MISS_INFORMATION);
        	return reMessage;    		 
    	}	
    	
        if (temperatureConfService.Config(temperatureConfigDto)) {
        	
        	ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.SUCCESS_CONFIG);
        	return reMessage;           
        }
        ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.FAIL_CONFIG);
        return reMessage;
    }
    
    @RequestMapping(value = "config/list", method = RequestMethod.GET)
    public Page<EmailConfig> getList(@RequestParam(name = "page", required = false, defaultValue = "0") int pageIndex,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return temperatureConfService.getListConfigEmail(pageIndex,pageSize);
    }
}
