package com.example.demo.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Map;
import com.example.demo.dto.user.ListUser;
import com.example.demo.dto.user.UserDto;
import com.example.demo.model.user.User;
import com.example.demo.repository.user.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
    private final Map map;
    public UserService(UserRepository userRepository, Map map) {
        this.userRepository = userRepository;
        this.map = map;
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
	 
	 
	 // importDataBy csv
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
    
}