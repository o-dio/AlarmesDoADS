package com.projetofinalpoo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.Vigilante;

@Controller 
public class LoginController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String formLogin() {
		return "login";
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


