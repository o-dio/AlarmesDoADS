package com.projetofinalpoo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.models.Cliente;

@Controller
@RequestMapping("/dashboard/clientes")
public class ClienteAdminController {

    private ClienteDAO clienteDAO = new ClienteDAO();

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteDAO.buscarTodos());
        return "clientes/listar"; // nome da view Thymeleaf
    }

    @PostMapping("/salvar")
    public String salvar(Cliente cliente) {
        if (cliente.getCpf() == null || cliente.getCpf().isEmpty() || clienteDAO.buscarPeloCpf(cliente.getCpf()) == null) {
            clienteDAO.cadastrar(cliente);
        } else {
            clienteDAO.atualizar(cliente);
        }
        return "redirect:/dashboard/clientes";
    }

    @GetMapping("/excluir/{cpf}")
    public String excluir(@PathVariable String cpf) {
        Cliente c = clienteDAO.buscarPeloCpf(cpf);
        if (c != null) {
            clienteDAO.deletar(c);
        }
        return "redirect:/dashboard/clientes";
    }
}
