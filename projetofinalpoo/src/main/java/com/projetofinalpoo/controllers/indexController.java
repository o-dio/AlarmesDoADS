package com.projetofinalpoo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class indexController {
	
	@RequestMapping("/")
	public String index(HttpSession session) {
		Object usuario = session.getAttribute("usuarioLogado");
		if (usuario == null) {
			return "redirect:/login";
		}
		return "index";
	}
}
