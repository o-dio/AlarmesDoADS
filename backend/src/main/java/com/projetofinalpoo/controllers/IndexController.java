package com.projetofinalpoo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetofinalpoo.services.EmailService;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador responsável por fornecer APIs para a página inicial e envio de emails.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class IndexController {
    
    @Autowired
    private EmailService emailService;

    /**
     * Endereço de email remetente configurado no application.properties.
     */
    @Value("${spring.mail.username}")
    private String emailRemetente;

    /**
     * Endpoint para enviar email via formulário de contato.
     * Verifica se o usuário está logado antes de enviar.
     * 
     * @param session Sessão HTTP atual.
     * @param nome Nome do remetente da mensagem.
     * @param email Email do remetente.
     * @param mensagem Conteúdo da mensagem enviada.
     * @return 200 OK se enviado, 401 se usuário não estiver logado.
     */
    @PostMapping("/enviarEmail")
    public ResponseEntity<?> enviarEmail(HttpSession session,
                                         @RequestParam("nome") String nome,
                                         @RequestParam("email") String email,
                                         @RequestParam("mensagem") String mensagem) {
        Object usuario = session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuário não logado");
        }

        String assunto = "Mensagem de contato enviada por: " + nome;
        String corpo = "Nome: " + nome + "\n"
                     + "Email: " + email + "\n"
                     + "Mensagem:\n" + mensagem;

        emailService.enviarEmail(emailRemetente, assunto, corpo);

        return ResponseEntity.ok("Email enviado com sucesso");
    }

    /**
     * Endpoint para verificar a sessão do usuário logado.
     * Retorna o tipo do usuário (cliente, vigilante, admin) ou null se não logado.
     * 
     * @param session Sessão HTTP atual.
     * @return Tipo do usuário logado, o objeto usuário ou null.
     */
    @GetMapping("/session")
    public ResponseEntity<?> getSession(HttpSession session) {
        Object tipo = session.getAttribute("tipo");
        Object usuario = session.getAttribute("usuarioLogado");
        
        if (tipo == null || usuario == null) {
            return ResponseEntity.ok(null);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("tipo", tipo);
        response.put("usuario", usuario);

        return ResponseEntity.ok(response);
    }

}
