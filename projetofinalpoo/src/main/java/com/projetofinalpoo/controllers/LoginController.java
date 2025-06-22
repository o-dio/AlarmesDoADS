package com.projetofinalpoo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller 
public class LoginController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String formLogin() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String formLogin(Object user) {

		

		return "redirect:/login";
	}
}
