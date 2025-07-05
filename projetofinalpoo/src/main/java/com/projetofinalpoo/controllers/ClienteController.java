package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.ContatoInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador para cliente
 */
@Controller
public class ClienteController {

    ClienteDAO dao = new ClienteDAO();

 

    /**
     * Cadastra Cliente
     * 
     * @param login Login do cliente a ser cadastrado
     * @param senha senha do cliente a ser cadastrado
     * @param cpf cpf do cliente a ser cadastrado
     * @param dataNasc data de nascimento do cliente a ser cadastrado
     * @param fone telefone do cliente a ser cadastrado
     * @param email email do cliente a ser cadastrado
     * @param foneContato contato do cliente a ser cadastrado
     * @return redirecionamento para pagina do cliente
     */
    @PostMapping("/cadastrarCliente")
    public String cadastrarCliente(
            @RequestParam String login,
            @RequestParam String senha,
            @RequestParam String cpf,
            @RequestParam String dataNasc,
            @RequestParam String fone,
            @RequestParam String email,
            @RequestParam String foneContato
    ) {
        Cliente cliente = new Cliente(login, senha, cpf, dataNasc, new ContatoInfo(fone, email, foneContato));
        dao.cadastrar(cliente);
        return "redirect:/clientes";
    }

    /**
     * Edita o cliente
     * 
     * @param login login do novo cliente
     * @param senha senha do novo cliente
     * @param cpf cpf base
     * @param dataNasc data de nascimento do novo cliente
     * @param fone telefone do novo cliente
     * @param email email do novo cliente
     * @param foneContato contato do novo cliente
     * @return redirecionamento para página do cliente
     */
    @PostMapping("/editarCliente")
    public String editarCliente(
            @RequestParam String login,
            @RequestParam(required = false) String senha,
            @RequestParam String cpf,
            @RequestParam String dataNasc,
            @RequestParam String fone,
            @RequestParam String email,
            @RequestParam String foneContato
    ) {
        Cliente cliente = new Cliente(login, senha, cpf, dataNasc, new ContatoInfo(fone, email, foneContato));
        dao.atualizar(cliente);
        return "redirect:/clientes";
    }

    /**
     * Excluir cliente baseado no CPF
     * 
     * @param cpf cpf do cliente a ser excluido
     * @return redirecionamento para clientes
     */
    @GetMapping("/excluirCliente/{cpf}")
    public String excluirCliente(@PathVariable String cpf) {
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        dao.deletar(cliente);
        return "redirect:/clientes";
    }
}
