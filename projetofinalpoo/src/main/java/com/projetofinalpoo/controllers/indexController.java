package com.projetofinalpoo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetofinalpoo.services.EmailService;

import jakarta.servlet.http.HttpSession;

/**
 * Controlador responsável por gerenciar a página inicial e o envio de emails de contato.
 */
@Controller
public class indexController {
    
    @Autowired
    private EmailService emailService;

    /**
     * Endereço de email remetente configurado no application.properties.
     */
    @Value("${spring.mail.username}")
    private String emailRemetente;

    /**
     * Método que direciona para a página principal (index) se o usuário estiver logado.
     * Caso contrário, redireciona para a página de login.
     * 
     * @param session Sessão HTTP atual para verificação do usuário logado.
     * @return Nome da view ou redirecionamento para login.
     */
    @RequestMapping("/")
    public String index(HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "redirect:/login";
        }
        return "index";
    }

    /**
     * Método para envio de email via formulário de contato.
     * Verifica se o usuário está logado antes de enviar o email.
     * 
     * @param session Sessão HTTP atual para verificação do usuário logado.
     * @param nome Nome do remetente da mensagem.
     * @param email Email do remetente.
     * @param mensagem Conteúdo da mensagem enviada.
     * @return Redirecionamento para a página principal ou login.
     */
    @RequestMapping(value = "/enviarEmail", method = RequestMethod.POST)
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
