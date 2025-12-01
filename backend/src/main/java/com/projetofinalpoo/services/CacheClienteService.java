package com.projetofinalpoo.services;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.models.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Serviço de cache em memória para objetos {@link Cliente}, 
 * utilizado para reduzir acessos ao banco de dados e melhorar a performance.
 * 
 * Permite operações de CRUD em memória, além de sincronização com o banco.
 */
@Service
public class CacheClienteService {

    /** Armazena os clientes em memória, indexados pelo CPF. */
    private HashMap<String, Cliente> cacheClientes = new HashMap<>();

    /** DAO responsável pelas operações no banco de dados. */
    private final ClienteDAO clienteDAO;

    /**
     * Construtor do serviço, onde o Spring injeta automaticamente o ClienteDAO.
     *
     * @param clienteDAO DAO responsável pelas operações no banco.
     */
    @Autowired
    public CacheClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    /**
     * Carrega todos os clientes do banco de dados e os armazena no cache.
     * Substitui qualquer conteúdo anterior do cache.
     */
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

    /**
     * Adiciona um novo cliente ao cache.
     * 
     * @param cliente O {@link Cliente} a ser adicionado.
     */
    public void cadastrar(Cliente cliente) {
        cacheClientes.put(cliente.getCpf(), cliente);
        System.out.println("Cliente cadastrado na memoria com sucesso!");
    }

    /**
     * Retorna todos os clientes presentes no cache.
     * 
     * @return Lista de clientes em memória, ou {@code null} se o cache estiver vazio.
     */
    public ArrayList<Cliente> buscarTodos() {
        if (cacheClientes.isEmpty()) return null;
        return new ArrayList<>(cacheClientes.values());
    }

    /**
     * Busca um cliente no cache com base no CPF informado no objeto.
     * 
     * @param cliente O {@link Cliente} com CPF a ser buscado.
     * @return O cliente correspondente, ou {@code null} se não encontrado.
     */
    public Cliente buscar(Cliente cliente) {
        return cacheClientes.get(cliente.getCpf());
    }

    /**
     * Busca um cliente no cache com base no CPF.
     * 
     * @param cpf CPF do cliente a ser buscado.
     * @return O {@link Cliente} correspondente ou {@code null} se não encontrado.
     */
    public Cliente buscarPeloCpf(String cpf) {
        Cliente cliente = cacheClientes.get(cpf);
        if (cliente == null) {
            System.out.println("Cliente nao encontrado com CPF: " + cpf);
        }
        return cliente;
    }

    /**
     * Busca um cliente no cache com base no login e senha.
     * 
     * @param login Login do cliente.
     * @param senha Senha do cliente (criptografada).
     * @return O {@link Cliente} correspondente ou {@code null} se não encontrado.
     */
    public Cliente buscarPeloLoginSenha(String login, String senha) {
        for (Cliente c : cacheClientes.values()) {
            if (c.getLogin().equals(login) && c.getSenha().equals(senha)) {
                return c;
            }
        }
        System.out.println("Cliente nao encontrado");
        return null;
    }

    /**
     * Atualiza as informações de um cliente já existente no cache.
     * 
     * @param cliente O {@link Cliente} atualizado.
     */
    public void atualizar(Cliente cliente) {
        if (cacheClientes.containsKey(cliente.getCpf())) {
            cacheClientes.put(cliente.getCpf(), cliente);
            System.out.println("Cliente atualizado na memoria com sucesso!");
        } else {
            System.out.println("Cliente nao encontrado para atualizacao.");
        }
    }

    /**
     * Remove um cliente do cache.
     * 
     * @param cliente O {@link Cliente} a ser removido.
     */
    public void deletar(Cliente cliente) {
        if (cacheClientes.remove(cliente.getCpf()) != null) {
            System.out.println("Cliente removido da memoria com sucesso!");
        } else {
            System.out.println("Cliente nao encontrado para exclusao.");
        }
    }

    /**
     * Sincroniza os dados do cache com o banco de dados.
     * Cadastra novos clientes ou atualiza os já existentes no banco.
     */
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

    /**
     * Limpa completamente os dados armazenados em memória.
     */
    public void limparMemoria() {
        cacheClientes.clear();
    }
}
