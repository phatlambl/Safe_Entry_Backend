package com.example.demo.controller.user;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.demo.dto.user.ListUser;
import com.example.demo.dto.user.LoginDto;
import com.example.demo.dto.user.RegisterDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.model.user.User;
import com.example.demo.repository.device.DeviceLogRepository;
import com.example.demo.repository.device.DeviceRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.auth.AuthenticationService;
import com.example.demo.service.device.DeviceLogService;
import com.example.demo.service.message.Message;
import com.example.demo.service.message.ResponseMessage;
import com.example.demo.service.user.UserService;



@RestController
@RequestMapping("/rest/user/")
public class UserRestController {
	
	private final AuthenticationService authenticationService;
	private final UserService userService;
	private final UserRepository userRepository;

	@Autowired
	public UserRestController(UserRepository userRepository, DeviceRepository deviceRepository,
			DeviceLogRepository deviceLogRepository, AuthenticationService authenticationService,
			DeviceLogService deviceService, UserService userService) {
		this.authenticationService = authenticationService;
		this.userService = userService;
		this.userRepository = userRepository;
	}

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Object login(HttpSession session, @RequestBody LoginDto loginDto) {
        if (authenticationService.login(loginDto, session)) {
        	ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.LOGIN_SUCCESS);
        	return reMessage;
        }
        ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.LOGIN_FAILURE);
    	return reMessage;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Object register(@Validated @RequestBody RegisterDto registerDto, BindingResult result) {
    	if(result.hasErrors()) {
    		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_GATEWAY, Message.MISS_INFORMATION);
        	return reMessage;
    		
    	}
        if (authenticationService.register(registerDto)) {
        	ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.REGISTER_SUCCESS);
        	return reMessage;
        }
        ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.REGISTER_FAILURE);
    	return reMessage;
    }
    
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ListUser getList(@RequestParam(name = "page", required = false, defaultValue = "0") int pageIndex,
                                 @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                 @RequestParam(name = "userId", required = false,  defaultValue = "") String userId,
                                 @RequestParam(name = "name", required = false, defaultValue = "") String name,
                                 @RequestParam(name="email", required = false, defaultValue = "") String email,
                                 @RequestParam(name="sortBy", required = false, defaultValue = "id") String sortBy,
                                 @RequestParam(name="order", required = false, defaultValue = "ASC") String order) {
    	
    	
    	
    	return userService.getListByFilter(name, userId, sortBy, order, pageIndex, pageSize);
    }
    
    
//    @GetMapping(value="delete/{id}")
//    public Object delete(@PathVariable String id) {
//    	
//    	userRepository.deleteById(id);
//    	
//    	
//    	
//    	ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.DELETE_SUCCESS);
//		return reMessage;
//    }
    
    
   @GetMapping(value="download")
   public void exportCsv(HttpServletResponse response) throws IOException{
	   
	   List<User> listUser = new ArrayList<User>();
	   
	   listUser = userRepository.findAll();
	   response.setContentType("text/csv");
	   String fileName = "users.csv";
	   String headerKey = "Content-Disposition";
	   String headerValue = "attachment; filename=" + fileName;
	   
	   response.setHeader(headerKey, headerValue);
	   
	   ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	   
	   String[] csvHeader = {"UserId", "Name"};
	   String[] nameMapping = {"id", "name"};
	   
	   csvWriter.writeHeader(csvHeader);
	   for(User user : listUser) {
		   csvWriter.write(user, nameMapping);
	   }
	   
	   csvWriter.close();	
   }
   
   
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public Object importUserCsv(@RequestParam(value="myFile") MultipartFile files) throws Exception {

		ICsvBeanReader beanReader = null;


		// read csv and save database
		try {				
			
			beanReader = new CsvBeanReader(new InputStreamReader(files.getInputStream()), CsvPreference.STANDARD_PREFERENCE);
			


			//  final CellProcessor[] processors = getProcessors();
			String[] nameMapping = { "id", "name"};

			UserDto userDto;

			while ((userDto = beanReader.read(UserDto.class, nameMapping)) != null) {
				
				System.out.println("name: " + userDto.getName());
				User user = new User(userDto.getId(), userDto.getName());
				userRepository.save(user);
			}

		} catch (Exception e) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST,
					Message.IMPORT_NOT_CORRECT);
			return reMessage;
		} finally {

			if (beanReader != null) {
				beanReader.close();
			}

		}

		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.IMPORT_SUCCESS);
		return reMessage;

	}
	

	// validate
	private CellProcessor[] getProcessors() {

		final CellProcessor[] processors = new CellProcessor[] { 
				new UniqueHashCode(), // UUID (must be unique)
				new NotNull(), // Name
		};

		return processors;
	}   
   

   
   
   
}
