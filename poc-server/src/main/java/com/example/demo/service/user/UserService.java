package com.example.demo.service.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Map;
import com.example.demo.dto.user.ListUser;
import com.example.demo.dto.user.RegisterDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.model.databaseupdate.DatabaseUpdate;
import com.example.demo.model.user.User;
import com.example.demo.repository.databaseupdate.DatabaseUpdateReposiroty;
import com.example.demo.repository.user.RoleRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.user.UserRoleRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
    private final Map map;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final DatabaseUpdateReposiroty databaseUpdateRepo;
    
    public UserService(UserRepository userRepository, Map map, RoleRepository roleRepository, UserRoleRepository userRoleRepository, DatabaseUpdateReposiroty databaseUpdateRepo) {
        this.userRepository = userRepository;
        this.map = map;
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.databaseUpdateRepo = databaseUpdateRepo;
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
	 
	 
	
	public boolean register(RegisterDto registerDto) {
		try {
			User user = new User();
			user.setId(registerDto.getId());
			user.setName(registerDto.getName());
			user.setEmail(registerDto.getEmail());
			userRepository.save(user);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			System.out.println("Timestamp" + timestamp.getTime());
			DatabaseUpdate dateModify = new DatabaseUpdate(1, timestamp.getTime());
			databaseUpdateRepo.save(dateModify);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	  
	public boolean delete(String id) {
		
		try {
			userRepository.deleteUserById(id);	
			
		}catch (Exception e) {
			return false;
		}
		return true;
	}

}