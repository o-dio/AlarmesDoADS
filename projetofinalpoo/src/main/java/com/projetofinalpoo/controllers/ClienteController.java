package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.ContatoInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class ClienteController {

    ClienteDAO dao = new ClienteDAO();

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        ArrayList<Cliente> clientes = dao.buscarTodos();
        model.addAttribute("clientes", clientes);
        return "clientes";
    }

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

    @GetMapping("/excluirCliente/{cpf}")
    public String excluirCliente(@PathVariable String cpf) {
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        dao.deletar(cliente);
        return "redirect:/clientes";
    }
}
