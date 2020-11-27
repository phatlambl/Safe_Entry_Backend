package com.example.demo.controller.databaseupdate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.databaseupdate.DatabaseUpdate;
import com.example.demo.repository.databaseupdate.DatabaseUpdateReposiroty;

/*
 * check modify update of database
 * 
 * */
@Controller
@RequestMapping(value="/rest/modify/")
public class DatabaseUpdateController {

	@Autowired
	DatabaseUpdateReposiroty dataRepo;
	
	@RequestMapping(value="users", method = RequestMethod.GET)
	@ResponseBody
	public Object checkModify(@RequestParam(value="dateModify", required = true) Long timestamp) {
		
		Optional<DatabaseUpdate> date = dataRepo.findById(1);
		if(date.isPresent()) {
			if(timestamp >= date.get().getLast_modify()) {
				return true;
			}	
			return false;
		}
		return false;		
	}
}
