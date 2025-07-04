package com.projetofinalpoo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetofinalpoo.services.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class indexController {
	
	@Autowired
    private EmailService emailService;

	@RequestMapping("/")
	public String index(HttpSession session) {
		Object usuario = session.getAttribute("usuarioLogado");
		if (usuario == null) {
			return "redirect:/login";
		}
		return "index";
	}

	@Value("${spring.mail.username}")
    private String emailRemetente;

	@RequestMapping(value = "/enviarEmail", method=RequestMethod.POST)
	public String enviarEmail(HttpSession session,
							@RequestParam("nome") String nome,
							@RequestParam("email") String email,
							@RequestParam("mensagem") String mensagem) {
								
		Object usuario = session.getAttribute("usuarioLogado");
		if (usuario == null) {
			return "redirect:/login";
		}

		String assunto = "Mensagem de contato enviada por: " + nome;
		String corpo = "Nome: " + nome + "\n"
					+ "Email: " + email + "\n"
					+ "Mensagem:\n" + mensagem;

		emailService.enviarEmail(emailRemetente, assunto, corpo);

		return "redirect:/";
	}

}
