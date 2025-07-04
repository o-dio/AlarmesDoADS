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

/**
 * Controlador responsável por gerenciar o fluxo de login e logout dos usuários do sistema,
 * suportando diferentes tipos de usuários: Cliente, Vigilante e Admin.
 */
@Controller 
public class LoginController {
    /**
     * Método que exibe a página de login.
     * Se o usuário já estiver logado, redireciona para a página inicial.
     * 
     * @param session Sessão HTTP atual para verificar usuário logado.
     * @return Nome da view de login ou redirecionamento para a página principal.
     */
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String formLogin(HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "login";
        }
        return "redirect:/";
    }

    /**
     * Método que processa o formulário de login.
     * Verifica as credenciais e tipo de usuário (role), e realiza a autenticação
     * carregando os dados do banco via cache e validando a senha criptografada em MD5.
     * 
     * @param nome Nome de login fornecido pelo usuário.
     * @param senha Senha fornecida pelo usuário (em texto claro, será convertida para MD5).
     * @param role Tipo de usuário (cliente, vigilante ou admin).
     * @param session Sessão HTTP para armazenar dados do usuário logado.
     * @return Redirecionamento para a página principal em caso de sucesso ou para a página de login em caso de falha.
     */
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
        } else if(role.equals("admin")) {
            CacheAdminService adminCache = new CacheAdminService();
            adminCache.carregarDoBanco();
            Admin findAdmin = adminCache.buscarPeloLoginSenha(nome, HashMD5Service.gerarMD5(senha));
            if(findAdmin != null) {
                session.setAttribute("usuarioLogado", findAdmin);
                session.setAttribute("tipo", "admin");
                return "redirect:/";
            }
        }

        // Caso falhe a autenticação, retorna para a página de login
        return "login";
    }

    /**
     * Método que realiza o logout do usuário, invalidando a sessão HTTP.
     * 
     * @param session Sessão HTTP atual.
     * @return Redirecionamento para a página de login.
     */
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
