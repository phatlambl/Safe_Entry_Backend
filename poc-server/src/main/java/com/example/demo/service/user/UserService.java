package com.example.demo.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Map;
import com.example.demo.dto.user.ListUser;
import com.example.demo.dto.user.RegisterDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserRole;
import com.example.demo.repository.user.RoleRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.user.UserRoleRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
    private final Map map;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    
    public UserService(UserRepository userRepository, Map map, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.map = map;
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
    }

    public List<UserDto> getListUser(int page, int pageSize){
        List<UserDto> userList = new ArrayList<>();
        Pageable paging = PageRequest.of(page, pageSize);
        Page<User> users = userRepository.findAll(paging);
        for (User user :
                users) {
            userList.add(map.userDto(user));
        }
        return userList;
    }
    
	 public ListUser getListByFilter(String name, String userId, String sortBy, String order, int page, int pageSize){
    	 List<UserDto> userDto = new ArrayList<>();
    	 String nameFilter = "%" + name + "%";
    	 String userIdFilter = "%" + userId +"%";    	   	    	 
    	 Pageable paging; 	 	 

    	 if(order.equalsIgnoreCase("DESC"))
    	 {
    		 paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, sortBy) );     	
    	 }else {
    		 paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sortBy) );  				
    	 }
    	 	
    	 Page<User> listUser = userRepository.findByFilter(nameFilter, userIdFilter, paging);	
    	 
    	 Long totalItems = listUser.getTotalElements();
    	
		for (User user :
			listUser) {
			userDto.add(map.userDto(user));
	     }
		
		 ListUser list = new ListUser(totalItems, page, pageSize, userDto);
	     return list; 
    	
    }    
	 
	 
	
	 public boolean register(RegisterDto registerDto){
       if (userRepository.findUserById(registerDto.getId()) != null){
    	   
    	   System.out.println("loi o day");
           return false;
       }
       User user = new User();
       user.setId(registerDto.getId());            
       user.setName(registerDto.getName());
       userRepository.save(user);
       
       return true;

    
   }
	  

}