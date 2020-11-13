package com.example.demo.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {
	

	@RequestMapping("/admin/**")
	public String redirect() {
		return "forward:/index.html";
	}

}
