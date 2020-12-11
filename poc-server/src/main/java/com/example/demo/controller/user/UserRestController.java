package com.example.demo.controller.user;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import com.example.demo.dto.user.JwtResponse;
import com.example.demo.dto.user.ListUser;
import com.example.demo.dto.user.LoginDto;
import com.example.demo.dto.user.RegisterDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.model.user.User;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.message.Message;
import com.example.demo.service.message.ResponseMessage;
import com.example.demo.service.user.MyUserDetailsService;
import com.example.demo.service.user.UserService;
import com.example.demo.util.JwtUtil;

@RestController
@RequestMapping("/rest/user/")
public class UserRestController {	

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MyUserDetailsService myUserDetailService;
	

	/*
	 * Login account( used id and password)
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object createToken(@Validated @RequestBody LoginDto loginDto, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST,
					Message.MISS_INFORMATION);
			return reMessage;
		}
		try {
			authenticate(loginDto.getUsername(), loginDto.getPassword());

			final UserDetails userDetails = myUserDetailService.loadUserByUsername(loginDto.getUsername());

			final String token = jwtUtil.generateToken(userDetails);

			return new JwtResponse(token, HttpServletResponse.SC_OK, Message.LOGIN_SUCCESS);

		} catch (Exception e) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST,
					Message.INFORMATION_LOGIN_INCORRECT);
			return reMessage;
		}
	}

	/*
	 * add user(id, name, email, password, faceId)
	 * */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Object register(@Validated @RequestBody RegisterDto registerDto, BindingResult result) {
		if (result.hasErrors()) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST,
					Message.MISS_INFORMATION);
			return reMessage;
		}
		if (userRepository.findUserById(registerDto.getId()) != null) {
			if (userService.register(registerDto)) {
				ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.UPDATE_SUCCESS);
				return reMessage;
			}
		}
		if (userService.register(registerDto)) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.REGISTER_SUCCESS);
			return reMessage;
		}
		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.REGISTER_FAILURE);
		return reMessage;
	}

	// update user
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public Object updateUser(@Validated @RequestBody RegisterDto registerDto, BindingResult result) {

		if (result.hasErrors()) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST,
					Message.MISS_INFORMATION);
			return reMessage;

		}
		if (userService.register(registerDto)) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.UPDATE_SUCCESS);
			return reMessage;
		}

		ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.UPDATE_FAILURE);
		return reMessage;
	}

	// delete user
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public Object deleteUser(@PathVariable(value = "id") String id) {		
		if (userService.delete(id)) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.DELETE_SUCCESS);
			return reMessage;
		} else {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.DELETE_FAILURE);
			return reMessage;
		}
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ListUser getList(@RequestParam(name = "page", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(name = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "email", required = false, defaultValue = "") String email,
			@RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
			@RequestParam(name = "order", required = false, defaultValue = "ASC") String order) {

		return userService.getListByFilter(name, userId, sortBy, order, pageIndex, pageSize);
	}


	/*
	 * Download list user to file csv
	 * include: id, name, email, faceId
	 * */
	@GetMapping(value = "download")
	public void exportCsv(HttpServletResponse response) throws IOException {

		List<User> listUser = new ArrayList<User>();

		listUser = userRepository.findAll();
		response.setContentType("text/csv");
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//		String dateDownload = sdf.format(new Date());  
//		System.out.println(dateDownload);

		String fileName = "employee.csv";
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		
		response.setHeader(headerKey, headerValue);
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		String[] csvHeader = { "UserId", "Name", "Email", "FaceId" };
		String[] nameMapping = { "id", "name", "email", "faceId" };

		csvWriter.writeHeader(csvHeader);
		for (User user : listUser) {
			csvWriter.write(user, nameMapping);
		}

		csvWriter.close();
	}

	
	/*
	 * 
	 * Import user by file csv
	 * include the fields are in the correct order: id, name, email, faceid)
	 * */
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public Object importUserCsv(@RequestParam(value = "myFile") MultipartFile files) throws Exception {

		try {
			ICsvBeanReader beanReader = null;

			// read csv and save database
			try {

				beanReader = new CsvBeanReader(new InputStreamReader(files.getInputStream()),
						CsvPreference.STANDARD_PREFERENCE);

				// final CellProcessor[] processors = getProcessors();
				String[] nameMapping = { "id", "name", "email", "faceId" };

				UserDto userDto;

				while ((userDto = beanReader.read(UserDto.class, nameMapping)) != null) {

//					System.out.println("id: " + userDto.getId());
//					System.out.println("name: " + userDto.getName());
//					System.out.println("email: " + userDto.getEmail());
//					System.out.println("faceId: " + userDto.getFaceId());

					User user = new User(userDto.getId(), userDto.getName(), userDto.getEmail(), userDto.getFaceId());
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

		} catch (Exception e) {
			ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, Message.IMPORT_FAILURE);
			return reMessage;
		}

	}

	// validate file csv
	private CellProcessor[] getProcessors() {

		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // UUID (must be unique)
				new NotNull(), // Name
		};

		return processors;
	}

	//authenticate user login
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLE", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

	}

}
