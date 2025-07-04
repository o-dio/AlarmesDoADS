package com.projetofinalpoo.services;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.models.Cliente;

import java.util.ArrayList;
import java.util.HashMap;

public class CacheClienteService {
    private HashMap<String, Cliente> cacheClientes = new HashMap<>();
    private ClienteDAO clienteDAO = new ClienteDAO();

    public void carregarDoBanco() {
        ArrayList<Cliente> clientesBanco = clienteDAO.buscarTodos();
        if (clientesBanco != null) {
            limparMemoria();
            for (Cliente c : clientesBanco) {
                cacheClientes.put(c.getCpf(), c);
            }
        }
        System.out.println("Clientes carregados do banco para a memoria.");
    }

    public void cadastrar(Cliente cliente) {
        cacheClientes.put(cliente.getCpf(), cliente);
        System.out.println("Cliente cadastrado na memoria com sucesso!");
    }

    public ArrayList<Cliente> buscarTodos() {
        if (cacheClientes.isEmpty()) return null;
        return new ArrayList<>(cacheClientes.values());
    }

    public Cliente buscar(Cliente cliente) {
        return cacheClientes.get(cliente.getCpf());
    }

    public Cliente buscarPeloCpf(String cpf) {
        Cliente cliente = cacheClientes.get(cpf);
        if (cliente == null) {
            System.out.println("Cliente nao encontrado com CPF: " + cpf);
        }
        return cliente;
    }

    public Cliente buscarPeloLoginSenha(String login, String senha) {
        for (Cliente c : cacheClientes.values()) {
            if (c.getLogin().equals(login) && c.getSenha().equals(senha)) {
                return c;
            }
        }
        System.out.println("Cliente nao encontrado");
        return null;
    }

    public void atualizar(Cliente cliente) {
        if (cacheClientes.containsKey(cliente.getCpf())) {
            cacheClientes.put(cliente.getCpf(), cliente);
            System.out.println("Cliente atualizado na memoria com sucesso!");
        } else {
            System.out.println("Cliente nao encontrado para atualizacao.");
        }
    }

    public void deletar(Cliente cliente) {
        if (cacheClientes.remove(cliente.getCpf()) != null) {
            System.out.println("Cliente removido da memoria com sucesso!");
        } else {
            System.out.println("Cliente nao encontrado para exclusao.");
        }
    }

    public void sincronizarComBanco() {
        for (Cliente cliente : cacheClientes.values()) {
            Cliente existenteNoBanco = clienteDAO.buscarPeloCpf(cliente.getCpf());

            if (existenteNoBanco == null) {
                clienteDAO.cadastrar(cliente);
            } else {
                clienteDAO.atualizar(cliente);
            }
        }
        System.out.println("Sincronizacao de clientes com banco concluida.");
    }

    public void limparMemoria() {
        cacheClientes.clear();
    }
}
