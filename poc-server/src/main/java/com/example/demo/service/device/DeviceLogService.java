package com.example.demo.service.device;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Map;
import com.example.demo.dto.device.DeviceLogDto;
import com.example.demo.dto.device.DeviceLogListDto;
import com.example.demo.dto.device.DeviceLogSubmitDto;
import com.example.demo.dto.device.EntryCsvDto;
import com.example.demo.dto.user.UserTemperature;
import com.example.demo.model.conf.EmailConfig;
import com.example.demo.model.device.Device;
import com.example.demo.model.device.DeviceLog;
import com.example.demo.model.user.User;
import com.example.demo.repository.card.UserCardRepository;
import com.example.demo.repository.conf.EmailConfigRepository;
import com.example.demo.repository.conf.TemperatureRepository;
import com.example.demo.repository.device.DeviceLogRepository;
import com.example.demo.repository.device.DeviceRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.auth.AuthenticationService;
import com.example.demo.service.message.Message;

@Service
public class DeviceLogService {
	private final DeviceRepository deviceRepository;
	private final UserRepository userRepository;
	private final DeviceLogRepository deviceLogRepository;
	private final Map map;
	private final TemperatureRepository temperatureRepository;
	private final EmailConfigRepository emailConfigRepository;
	private final JavaMailSender javaMailSender;

	@Autowired
	public DeviceLogService(DeviceRepository deviceRepository, AuthenticationService authenticationService,
			UserRepository userRepository, DeviceLogRepository deviceLogRepository, Map map,
			UserCardRepository userCardRepository, TemperatureRepository temperatureRepository,
			EmailConfigRepository emailConfigRepository, JavaMailSender javaMailSender) {
		this.deviceRepository = deviceRepository;
		this.userRepository = userRepository;
		this.deviceLogRepository = deviceLogRepository;
		this.map = map;
		this.temperatureRepository = temperatureRepository;
		this.emailConfigRepository = emailConfigRepository;
		this.javaMailSender = javaMailSender;
	}

	public boolean submitLog(DeviceLogSubmitDto deviceLogSubmitDto) {
		SimpleMailMessage msg = new SimpleMailMessage();
		float max_temperature = temperatureRepository.findById(1).getTemperature();
		if (deviceLogSubmitDto.getName() == null) {
			deviceLogSubmitDto.setName("client");
		}

		if (deviceRepository.findDeviceById(deviceLogSubmitDto.getDeviceId()) == null) {
			// add device_id moi vao he thong
			Device addDevice = new Device(deviceLogSubmitDto.getDeviceId(), deviceLogSubmitDto.getLocation(),
					deviceLogSubmitDto.getFloor(), deviceLogSubmitDto.getRoom());
			deviceRepository.save(addDevice);
		}
		DeviceLog deviceLog = new DeviceLog();
		// neu UUID not exist van save
		if (userRepository.findUserById(deviceLogSubmitDto.getUserId()) == null) {
			// add use_id moi vao he thong
			User addUser = new User(deviceLogSubmitDto.getUserId(), deviceLogSubmitDto.getName());
			userRepository.save(addUser);
		}
		User user = userRepository.findUserById(deviceLogSubmitDto.getUserId());
		// check client

//        if(!user.getName().contentEquals(deviceLogSubmitDto.getName())) {
//        	System.out.println("chay vao day");
//        	User addUser = new User(deviceLogSubmitDto.getUserId(), deviceLogSubmitDto.getName() );
//        	userRepository.save(addUser);
//        }

		deviceLog.setDevice(deviceRepository.findDeviceById(deviceLogSubmitDto.getDeviceId()));
		deviceLog.setTimestamp(deviceLogSubmitDto.getTimestamp());
		deviceLog.setTemperature(deviceLogSubmitDto.getTemperature());
		deviceLog.setUser(userRepository.findUserById(deviceLogSubmitDto.getUserId()));
		deviceLog.setCardType(deviceLogSubmitDto.getCardType());
		deviceLog.setName(user.getName());

		try {
			deviceLogRepository.save(deviceLog);
		} catch (Exception e) {
			return false;
		}
		if (deviceLogSubmitDto.getTemperature() > max_temperature) {
			List<EmailConfig> listEmail = emailConfigRepository.findAll();
			for (EmailConfig list : listEmail) {
				String email = list.getEmail();
				msg.setTo(email);
				msg.setSubject("Alert Temperature");
				msg.setText(userRepository.findUserById(deviceLogSubmitDto.getUserId()).getName()
						+ " have current temperature " + deviceLogSubmitDto.getTemperature() + Message.ALERT_TEMPERATURE
						+ " " + max_temperature);
				javaMailSender.send(msg);
			}
		}
		return true;
	}

	public List<DeviceLogDto> getList(int page, int pageSize) {
		List<DeviceLogDto> deviceLogDtoList = new ArrayList<>();
		Pageable paging = PageRequest.of(page, pageSize, Sort.by("timestamp").descending());
		Page<DeviceLog> deviceLogPage = deviceLogRepository.findAll(paging);
		for (DeviceLog deviceLog : deviceLogPage) {
			deviceLogDtoList.add(map.deviceLogDto(deviceLog));
		}
		return deviceLogDtoList;
	}

	public Long pastSixMonths() {
		LocalDate currentDate = LocalDate.now();
		LocalDate result = currentDate.minus(6, ChronoUnit.MONTHS);
		Timestamp ts = new Timestamp(convertToDateViaInstant(result).getTime());
		return ts.getTime();
	}

	public Date convertToDateViaInstant(LocalDate dateToConvert) {
		return Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public List<UserTemperature> getListByTimeInterval(String userId, Long fromTimestamp, Long toTimestamp, int page,
			int pageSize) {
		List<UserTemperature> userTemperatureList = new ArrayList<>();
		Pageable paging = PageRequest.of(page, pageSize, Sort.by("timestamp").descending());
		Page<DeviceLog> deviceLogList = deviceLogRepository
				.findByUserIdAndTimestampGreaterThanEqualAndTimestampLessThanEqual(userId, fromTimestamp, toTimestamp,
						paging);
		for (DeviceLog deviceLog : deviceLogList) {
			userTemperatureList.add(map.userTemperature(deviceLog));
		}
		return userTemperatureList;
	}

	public Page<Device> getListDevice(int page, int pageSize) {
		Pageable paging = PageRequest.of(page, pageSize);
		return deviceRepository.findAll(paging);
	}

	// get all list device_log by User
	public List<DeviceLog> getListByTime(String userId, long fromTimestamp, long toTimestamp) {
		return deviceLogRepository.getListByTime(userId, fromTimestamp, toTimestamp);
	}

	// get all list device, no page
	public List<DeviceLogDto> getListDeviceLog() {

		List<DeviceLogDto> listDeviceLogDto = new ArrayList<DeviceLogDto>();
		List<DeviceLog> listDeviceLog = deviceLogRepository.findAll();
		for (DeviceLog deviceLog : listDeviceLog) {
			listDeviceLogDto.add(map.deviceLogDto(deviceLog));
		}
		return listDeviceLogDto;

	}

	/**
	 * function: get List by filter return :
	 * 
	 * DeviceLogListDto { totalItems; currentPage; pageSize; List<DeviceLogDto>
	 * data; }
	 */
	public DeviceLogListDto getListByFilter(String name, String deviceId, Long fromTimestamp, Long toTimestamp,
			String sortBy, String order, int page, int pageSize) {
		List<DeviceLogDto> deviceLogDtoList = new ArrayList<>();
		String nameFilter = "%" + name + "%";
		String deviceIdFilter = "%" + deviceId + "%";
		Long toTimestampFilter;
		Long fromTimestampFiler;
		Pageable paging;

		if (order.equalsIgnoreCase("DESC")) {
			paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		} else {
			paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
		}

		if (toTimestamp != null) {
			toTimestampFilter = toTimestamp;
		} else {
			Date now = new Date();
			toTimestampFilter = now.getTime();
		}
		if (fromTimestamp != null) {

			fromTimestampFiler = fromTimestamp;
		} else {
			fromTimestampFiler = (long) 0;
		}

		Page<DeviceLog> deviceLogList = deviceLogRepository.findByFilter(nameFilter, deviceIdFilter, fromTimestampFiler,
				toTimestampFilter, paging);

		Long totalItems = deviceLogList.getTotalElements();

		for (DeviceLog deviceLog : deviceLogList) {
			deviceLogDtoList.add(map.deviceLogDto(deviceLog));
		}

		DeviceLogListDto deviceLogListDto = new DeviceLogListDto(totalItems, page, pageSize, deviceLogDtoList);
		return deviceLogListDto;

	}

	/**
	 * function: download csv by filter, convert timestamp to date
	 *
	 */

	public List<EntryCsvDto> getListByFilter(String name, String deviceId, Long fromTimestamp, Long toTimestamp,
			String sortBy, String order) {
		List<EntryCsvDto> EntryCsvDto = new ArrayList<>();
		String nameFilter = "%" + name + "%";
		String deviceIdFilter = "%" + deviceId + "%";
		Long toTimestampFilter;
		Long fromTimestampFiler;
		Pageable paging;

		if (order.equalsIgnoreCase("DESC")) {
			paging = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, sortBy));
		} else {
			paging = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, sortBy));
		}

		if (toTimestamp != null) {
			toTimestampFilter = toTimestamp;
		} else {
			Date now = new Date();
			toTimestampFilter = now.getTime();
		}
		if (fromTimestamp != null) {

			fromTimestampFiler = fromTimestamp;
		} else {
			fromTimestampFiler = (long) 0;
		}

		Page<DeviceLog> deviceLogList = deviceLogRepository.findByFilter(nameFilter, deviceIdFilter, fromTimestampFiler,
				toTimestampFilter, paging);

		for (DeviceLog i : deviceLogList) {
			EntryCsvDto.add(map.entryCsvDto(i));
		}

		return EntryCsvDto;

	}

}
