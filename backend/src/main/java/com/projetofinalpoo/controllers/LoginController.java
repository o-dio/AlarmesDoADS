package com.projetofinalpoo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class LoginController {

    /**
     * Método que processa o formulário de login.
     * Verifica as credenciais e tipo de usuário (role), e realiza a autenticação
     * carregando os dados do banco via cache e validando a senha criptografada em MD5.
     * 
     * @param nome Nome de login fornecido pelo usuário.
     * @param senha Senha fornecida pelo usuário (em texto claro, será convertida para MD5).
     * @param role Tipo de usuário (cliente, vigilante ou admin).
     * @param session Sessão HTTP para armazenar dados do usuário logado.
     * @return 200 OK caso sucesso, 401 se falhar.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String nome, @RequestParam String senha, 
                                   @RequestParam String role, HttpSession session) {
        
        Object usuario = null;

        if(role.equals("cliente")) {
            CacheClienteService clienteCache = new CacheClienteService();
            clienteCache.carregarDoBanco();
            usuario = clienteCache.buscarPeloLoginSenha(nome, HashMD5Service.gerarMD5(senha));
            if (usuario != null) {
                session.setAttribute("tipo", "cliente");
            } 
        } else if(role.equals("vigilante")) {
            CacheVigilanteService vigilanteCache = new CacheVigilanteService();
            vigilanteCache.carregarDoBanco();
            usuario = vigilanteCache.buscarPeloLoginSenha(nome, HashMD5Service.gerarMD5(senha));
            if(usuario != null) {
                session.setAttribute("tipo", "vigilante");
            }
        } else if(role.equals("admin")) {
            CacheAdminService adminCache = new CacheAdminService();
            adminCache.carregarDoBanco();
            usuario = adminCache.buscarPeloLoginSenha(nome, HashMD5Service.gerarMD5(senha));
            if(usuario != null) {
                session.setAttribute("tipo", "admin");
            }
        }

        if (usuario == null) {
            return ResponseEntity.status(401).body("Credenciais invalidas");
        }

        session.setAttribute("usuarioLogado", usuario);

        return ResponseEntity.ok("Login efetuado com sucesso");
    }

    /**
     * Método que realiza o logout do usuário, invalidando a sessão HTTP.
     * 
     * @param session Sessão HTTP atual.
     * @return Resposta confirmando o logout.
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout efetuado");
    }
}
