package com.projetofinalpoo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetofinalpoo.models.Admin;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.Vigilante;
import com.projetofinalpoo.services.CacheAdminService;
import com.projetofinalpoo.services.CacheClienteService;
import com.projetofinalpoo.services.CacheVigilanteService;
import com.projetofinalpoo.services.HashMD5Service;

import jakarta.servlet.http.HttpSession; 

@Controller 
public class LoginController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String formLogin(HttpSession session) {
		Object usuario = session.getAttribute("usuarioLogado");
		if(usuario == null) {
			return "login";
		}

		return "redirect:/";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String formLogin(@RequestParam String nome, 
							@RequestParam String senha, 
							@RequestParam String role,
							HttpSession session) {	
		System.out.println(role);
		if(role.equals("cliente")) {
			CacheClienteService clienteCache = new CacheClienteService();
			clienteCache.carregarDoBanco();
			Cliente findCliente = clienteCache.buscarPeloLoginSenha(nome, HashMD5Service.gerarMD5(senha));
			if(findCliente != null) {
				session.setAttribute("usuarioLogado", findCliente);
				session.setAttribute("tipo", "cliente");
				return "redirect:/";
			}
		} else if(role.equals("vigilante")) {
			CacheVigilanteService vigilanteCache = new CacheVigilanteService();
			vigilanteCache.carregarDoBanco();
			Vigilante findVigilante = vigilanteCache.buscarPeloLoginSenha(nome, HashMD5Service.gerarMD5(senha));
			if(findVigilante != null) {
				session.setAttribute("usuarioLogado", findVigilante);
				session.setAttribute("tipo", "vigilante");
				return "redirect:/";
			}
		} else if(role == "admin") {
			CacheAdminService adminCache = new CacheAdminService();
			adminCache.carregarDoBanco();
			Admin findAdmin = adminCache.buscarPeloLoginSenha(nome, HashMD5Service.gerarMD5(senha));
			if(findAdmin != null) {
				session.setAttribute("usuarioLogado", findAdmin);
				session.setAttribute("tipo", "vigilante");
				return "redirect:/";
			}
		}
		
		return "login";
    }

	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}


