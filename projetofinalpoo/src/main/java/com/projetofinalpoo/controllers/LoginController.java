package com.projetofinalpoo.controllers;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.Vigilante;

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
	public String formLogin(@RequestParam String nome, @RequestParam String senha) {
		ClienteDAO clienteDao = new ClienteDAO();
		Cliente findCliente = clienteDao.buscarPeloLoginSenha(nome, senha);

		if(findCliente != null) {
            return "redirect:/";
        } else {
			VigilanteDAO vigilanteDao = new VigilanteDAO();
			Vigilante findVigilante = vigilanteDao.buscarPeloLoginSenha(nome, senha);

			if(findVigilante != null) {
				return "redirect:/";
			} else {
				return "login";
			}
        }
    }
}


