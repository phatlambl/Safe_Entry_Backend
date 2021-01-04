package com.example.demo.controller.device;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.demo.dto.device.DeviceAccessFaceIdDto;
import com.example.demo.dto.device.DeviceDto;
import com.example.demo.dto.device.DeviceLogExcelExport;
import com.example.demo.dto.device.DeviceLogListDto;
import com.example.demo.dto.device.DeviceLogSubmitDto;
import com.example.demo.dto.device.EntryCsvDto;
import com.example.demo.dto.device.ListDeviceDto;
import com.example.demo.dto.user.UserTemperature;
import com.example.demo.dto.user.faceIdResponse;
import com.example.demo.model.device.DeviceLog;
import com.example.demo.model.user.User;
import com.example.demo.repository.device.DeviceLogRepository;
import com.example.demo.repository.device.DeviceRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.device.DeviceLogService;
import com.example.demo.service.device.DeviceService;
import com.example.demo.service.message.Message;
import com.example.demo.service.message.ResponseMessage;

@RestController
@RequestMapping("/rest/device/")
public class DeviceRestController {

	@Autowired
	private DeviceLogService deviceLogService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private DeviceLogRepository deviceLogRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	/*
	 * Device submit data to server 
	 */
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public Object submit(HttpServletResponse response, @Validated @RequestBody DeviceLogSubmitDto deviceLogSubmitDto,
			BindingResult result) {
		if (result.hasErrors()) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST,
					Message.MISS_INFORMATION);
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

	
	/*
	 * Device access by faceId *
	 */
	@RequestMapping(value = "access", method = RequestMethod.POST)
	public Object deviceAccess(@Validated @RequestBody DeviceAccessFaceIdDto deviceAccessFaceIdDto,HttpServletResponse resp,
			BindingResult result) {	
		if (result.hasErrors()) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST,
					Message.MISS_INFORMATION);
			return reMessage;
		}		
		//call to server face recognize
		
//		 final String uri = "https://uat4.styl.solutions/demo/rest/user/login";		 
//		    
//		    RestTemplate restTemplate = new RestTemplate();
//		 
//		    LoginDto login = new LoginDto("admin", "admin");
//		    Object rs = restTemplate.postForObject(uri, login, Object.class);
//		    System.out.println(rs);
//		    return rs;
	
		
		Optional<User> user = userRepository.findByFaceId(deviceAccessFaceIdDto.getFaceId());
		if (user.isPresent()) {
			faceIdResponse res = new faceIdResponse(user.get(), HttpServletResponse.SC_OK, Message.LOGIN_SUCCESS);
			return res;
		}
		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.NOT_RECOGNIZE);
		return reMessage;
	}

	/*
	 * Api return to list user access data *
	 */
	@RequestMapping(value = "list/log", method = RequestMethod.GET)
	public DeviceLogListDto listDeviceLog(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "deviceId", required = false, defaultValue = "") String deviceId,
			@RequestParam(name = "fromTimestamp", required = false) Long fromTimestamp,
			@RequestParam(name = "toTimestamp", required = false) Long toTimestamp,
			@RequestParam(name = "sortBy", required = false, defaultValue = "timestamp") String sortBy,
			@RequestParam(name = "order", required = false, defaultValue = "DESC") String order) {

		return deviceLogService.getListByFilter(name, deviceId, fromTimestamp, toTimestamp, sortBy, order, page,
				pageSize);
	}

	@RequestMapping(value = "list/user/temperature", method = RequestMethod.GET)
	public List<UserTemperature> listTemperatureOfUser(
			@RequestParam(name = "page", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "fromTimestamp", required = false) Long fromTimestamp,
			@RequestParam(name = "toTimestamp", required = false) Long toTimestamp,
			@RequestParam(name = "userId", required = false) String userId,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "deviceId", required = false) String deviceId) {

		return deviceLogService.getListByTimeInterval(userId, fromTimestamp, toTimestamp, pageIndex, pageSize);
	}

	// get all list device_log by User (draw chart) no page
	@RequestMapping(value = "list/user", method = RequestMethod.GET)
	public List<DeviceLog> listDeviceLogByUser(@RequestParam(name = "fromTimestamp") Long fromTimestamp,
										       @RequestParam(name = "toTimestamp") Long toTimestamp, 
											   @RequestParam(name = "userId") String userId,
											   @RequestParam(name = "name") String name) {
		return deviceLogService.getListByTime(userId, name, fromTimestamp, toTimestamp);
	}

	// get list device by page
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ListDeviceDto listDevice(@RequestParam(name = "page", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "location") String sortBy,
			@RequestParam(name = "order", required = false, defaultValue = "ASC") String order) {
		return deviceService.getListDevice(sortBy, order, pageIndex, pageSize);
	}

	// add device
	@PostMapping(value = "add")
	public Object addDevice(@Validated @RequestBody DeviceDto deviceDto, BindingResult result) {

		if (result.hasErrors()) {

			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST,
					Message.MISS_INFORMATION);
			return reMessage;
		}
		if (deviceRepository.findDeviceById(deviceDto.getId()) != null) {
			if (deviceService.saveDevice(deviceDto)) {

				ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.UPDATE_SUCCESS);
				return reMessage;
			}
		}
		if (deviceService.saveDevice(deviceDto)) {

			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.ADD_SUCCESS);
			return reMessage;

		}

		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.ADD_FAILURE);
		return reMessage;
	}

	// update device
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public Object updateDevice(@Validated @RequestBody DeviceDto deviceDto, BindingResult result) {
		if (result.hasErrors()) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST,
					Message.MISS_INFORMATION);
			return reMessage;
		}
		if (deviceService.saveDevice(deviceDto)) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.UPDATE_SUCCESS);
			return reMessage;
		}
		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.UPDATE_FAILURE);
		return reMessage;
	}

	// delete device
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public Object deleteDevice(@PathVariable(value = "id") String id) {
		if (deviceService.deleteDevice(id)) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.DELETE_SUCCESS);
			return reMessage;
		} else {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.DELETE_FAILURE);
			return reMessage;
		}
	}

	// export device_log
	@GetMapping(value = "/log/download")
	public void exportCsv(HttpServletResponse response,
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "deviceId", required = false, defaultValue = "") String deviceId,
			@RequestParam(name = "fromTimestamp", required = false) Long fromTimestamp,
			@RequestParam(name = "toTimestamp", required = false) Long toTimestamp,
			@RequestParam(name = "sortBy", required = false, defaultValue = "timestamp") String sortBy,
			@RequestParam(name = "order", required = false, defaultValue = "DESC") String order,
			@RequestParam(name="timezone", required = false, defaultValue = "GMT+8") String timezone) throws IOException {

		List<EntryCsvDto> listDeviceLogDto = new ArrayList<EntryCsvDto>();
		listDeviceLogDto = deviceLogService.getListByFilter(name, deviceId, fromTimestamp, toTimestamp, sortBy, order, timezone);

		response.setContentType("text/csv");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		dateFormatter.setTimeZone(TimeZone.getTimeZone(timezone));
		String currentDateTime = dateFormatter.format(new Date());
		String fileName = "SafeEntry ";
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName + currentDateTime + ".csv";

		response.setHeader(headerKey, headerValue);

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		String[] csvHeader = { "UUID", "Name", "Type", "Temperature", "Date", "Time", "TTCode", "deviceId",
				"Location"};
		String[] nameMapping = { "userId", "name", "type", "temperature", "date", "time", "ttCode", "deviceId",
				"location"};

		csvWriter.writeHeader(csvHeader);
		for (EntryCsvDto entryCsvDto : listDeviceLogDto) {
			csvWriter.write(entryCsvDto, nameMapping);
		}
		
		csvWriter.close();
	}
	
	
	/*
	 * Feature: export deviceLog(userLogin) to file excel
	 * 
	 */
	public void exportToExcel(HttpServletResponse response,
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "deviceId", required = false, defaultValue = "") String deviceId,
			@RequestParam(name = "fromTimestamp", required = false) Long fromTimestamp,
			@RequestParam(name = "toTimestamp", required = false) Long toTimestamp,
			@RequestParam(name = "sortBy", required = false, defaultValue = "timestamp") String sortBy,
			@RequestParam(name = "order", required = false, defaultValue = "DESC") String order) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String fileName = "UserLog" + currentDateTime + ".xlsx";
		String headerValue = "attachement;filename=" + fileName;

		response.setHeader(headerKey, headerValue);
		List<DeviceLog> listDeviceLog = new ArrayList<DeviceLog>();
		listDeviceLog = deviceLogRepository.findAll();

		DeviceLogExcelExport excelExport = new DeviceLogExcelExport(listDeviceLog);
		excelExport.export(response);

	}

}
