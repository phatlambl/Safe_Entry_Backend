package com.example.demo.service.device;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hpsf.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.temperature.Email;
import com.example.demo.dto.temperature.EmailSettingDto;
import com.example.demo.dto.temperature.TemperatureConfigDto;
import com.example.demo.model.conf.EmailConfig;
import com.example.demo.model.conf.Temperature;
import com.example.demo.repository.conf.EmailConfigRepository;
import com.example.demo.repository.conf.TemperatureRepository;

@Service
public class TemperatureConfService {

	@Autowired
	private TemperatureRepository temperatureRepository;
	
	@Autowired
	private EmailConfigRepository emailConfigRepository;	
	
	/*
	 * function configure list email
	 * */
	public boolean Config(TemperatureConfigDto temperatureConfigDto) {
		if (temperatureConfigDto.getEmails() == null) {
			return false;
		}
		Temperature temperatureConfig = temperatureRepository.findById(1);		
		temperatureConfig.setTemperature(temperatureConfigDto.getTemperature());
		temperatureRepository.save(temperatureConfig);	

		// String[] emailArray = temperatureConfigDto.getEmails().split(";");
		// EmailConfigRepository.deleteAll();		
		
		for (Email email : temperatureConfigDto.getEmails()) {
			try {
				EmailConfig emailConfig = new EmailConfig();
				emailConfig.setTemperature(temperatureConfig);
				emailConfig.setEmail(email.getDisplayValue());
				emailConfigRepository.save(emailConfig);
			}catch (Exception e) {
				System.out.println(e);
			}			
		}
		return true;
	}
	
	/*
	 *function update, setup email 
	 * */
	public boolean update(EmailSettingDto emailSettingDto) {
		try {
			Temperature temp = temperatureRepository.findById(1);	
			
			EmailConfig emailConfig = new EmailConfig();
			emailConfig.setId(emailSettingDto.getId());
			emailConfig.setEmail(emailSettingDto.getEmail());
			emailConfig.setTemperature(temp);
			emailConfig.setFrequency(emailSettingDto.getFrequency());
			emailConfig.setAlert(emailSettingDto.isAlert());
			emailConfig.setReport(emailSettingDto.isReport());
			emailConfig.setTimeReport(emailSettingDto.getTimeReport());
			emailConfig.setTime(emailSettingDto.getTime());
			emailConfig.setTimezone(emailSettingDto.getTimezone());
			emailConfig.setDayOfWeek(emailSettingDto.getDayOfWeek());	
			
			emailConfigRepository.save(emailConfig);			
			
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}		
		return true;		
	}
	
	
	/*
	 * function delete email configure
	 * */
	public boolean delete(int id) {
		try {
			emailConfigRepository.deleteById(id);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	

	public Page<EmailConfig> getListConfigEmail(int page, int pageSize) {
		Pageable paging = PageRequest.of(page, pageSize);
		return emailConfigRepository.findAll(paging);
	}
	
	public PaginationDto getListSetting(String sortBy, String order, int page, int pageSize) {
		List<Object> listSetting = new ArrayList<>();
		Pageable paging;
		Page<EmailConfig> listEmail;
		
		if (order.equalsIgnoreCase("DESC")) {
			paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		} else {
			paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
		}
		
		listEmail = emailConfigRepository.findAll(paging);
		Long totalItems = listEmail.getTotalElements();
		
		for(EmailConfig email : listEmail) {
			listSetting.add(email);
		}
		
		PaginationDto pagination = new PaginationDto(totalItems, page, pageSize, listSetting);
		return pagination;
		
	}
}
