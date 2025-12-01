package com.projetofinalpoo.dtos;

/**
 * Data Transfer Object (DTO) usado para receber informações de registro de usuários.
 * Pode conter dados comuns a todos os tipos de usuário, bem como campos específicos
 * para clientes ou vigilantes.
 */
public class RegistroDTO {

    /** Login do usuário. */
    public String login;

    /** Senha do usuário. */
    public String senha;

    /** Email do usuário. */
    public String email;

    /** Telefone principal do usuário. */
    public String fone;

    /** Telefone de contato alternativo do usuário. */
    public String foneContato;

    /** Função ou perfil do usuário (ex: "CLIENTE", "VIGILANTE"). */
    public String role;

    // Campos específicos para clientes

    /** CPF do cliente. */
    public String cpf;

    /** Data de nascimento do cliente. */
    public String dataNasc;

    // Campos específicos para vigilantes

    /** Turno de trabalho do vigilante. */
    public String turno;

    /** Data de contratação do vigilante. */
    public String dataContratacao;
}
