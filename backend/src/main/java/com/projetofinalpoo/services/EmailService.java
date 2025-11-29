package com.projetofinalpoo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pelo envio de e-mails utilizando o {@link JavaMailSender}.
 *
 * Este serviço é configurado com os dados de envio definidos no application.properties,
 * como o e-mail remetente.
 */
@Service
public class EmailService {

    /** Componente do Spring responsável por enviar e-mails. */
    @Autowired
    private JavaMailSender mailSender;

    /** Endereço de e-mail utilizado como remetente. */
    @Value("${spring.mail.username}")
    private String emailRemetente;

    /**
     * Envia um e-mail com os dados especificados.
     *
     * @param para    Endereço de e-mail do destinatário.
     * @param assunto Assunto do e-mail.
     * @param corpo   Corpo do e-mail (texto simples).
     */
    public void enviarEmail(String para, String assunto, String corpo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(para);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);
        mensagem.setFrom(emailRemetente);
        mailSender.send(mensagem);
    }
}
