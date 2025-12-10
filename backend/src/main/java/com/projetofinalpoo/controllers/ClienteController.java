package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.ContatoInfo;
import com.projetofinalpoo.services.HashMD5Service;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para clientes.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteDAO dao = new ClienteDAO();

    /**
     * Lista todos os clientes cadastrados.
     *
     * @return lista de clientes
     */
    @GetMapping
    public List<Cliente> listarClientes() {
        return dao.buscarTodos();
    }

    /**
     * Retorna um cliente baseado no login.
     *
     * @param cpf cpf do cliente
     * @return Cliente correspondente
     */
    @GetMapping("/{login}")
    public Cliente buscarCliente(@PathVariable String cpf) {
        return dao.buscarPeloCpf(cpf);
    }

    /**
     * Salva ou atualiza um cliente.
     * Se o cliente já existir (pelo login ou CPF), atualiza os dados;
     * caso contrário, cadastra um novo cliente.
     *
     * @param cliente objeto Cliente enviado pelo front
     * @return Cliente cadastrado ou atualizado
     */
    @PostMapping("/salvar")
    public Cliente salvar(@RequestBody Cliente cliente) {
        Cliente existente = dao.buscarPeloCpf(cliente.getCpf());

        if (existente == null) {
            cliente.setSenha(HashMD5Service.gerarMD5(cliente.getSenha()));
            dao.cadastrar(cliente);
        } else {
            cliente.setSenha(HashMD5Service.gerarMD5(cliente.getSenha()));
            dao.atualizar(cliente);
        }

        return cliente;
    }

    /**
     * Exclui um cliente baseado no CPF.
     *
     * @param cpf CPF do cliente a ser excluído
     * @return mensagem de confirmação
     */
    @DeleteMapping("/excluir/{cpf}")
    public String excluirCliente(@PathVariable String cpf) {
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        dao.deletar(cliente);
        return "Cliente com CPF " + cpf + " excluído com sucesso!";
    }
}
