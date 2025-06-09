package com.projetofinalpoo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class UsuarioController {
	
	@RequestMapping("/login")
	public String formLogin() {
		return "login";
	}
	
}
