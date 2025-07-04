package com.projetofinalpoo.services;

import com.projetofinalpoo.dao.AdminDAO;
import com.projetofinalpoo.models.Admin;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Serviço de cache para objetos {@link Admin}, armazenando-os em memória 
 * para melhorar a performance e reduzir acessos ao banco de dados.
 * 
 * Permite operações de CRUD em memória, bem como sincronização com o banco.
 */
public class CacheAdminService {

    /** Armazena os admins em memória, indexados pelo login. */
    private final HashMap<String, Admin> cacheAdmins = new HashMap<>();

    /** DAO responsável pelas operações com o banco de dados. */
    private final AdminDAO adminDAO = new AdminDAO();

    /**
     * Carrega todos os administradores do banco de dados para o cache em memória.
     * Substitui o conteúdo atual do cache.
     */
    public void carregarDoBanco() {
        ArrayList<Admin> lista = adminDAO.buscarTodos();
        if (lista != null) {
            limparMemoria();
            for (Admin a : lista) {
                cacheAdmins.put(a.getLogin(), a);
            }
        }
        System.out.println("Admins carregados do banco para a memoria.");
    }

    /**
     * Adiciona um novo administrador ao cache (não persiste no banco).
     * 
     * @param admin O objeto {@link Admin} a ser adicionado.
     */
    public void cadastrar(Admin admin) {
        cacheAdmins.put(admin.getLogin(), admin);
        System.out.println("Admin cadastrado na memoria.");
    }

    /**
     * Retorna todos os administradores atualmente armazenados no cache.
     * 
     * @return Uma lista com todos os admins em memória.
     */
    public ArrayList<Admin> buscarTodos() {
        return new ArrayList<>(cacheAdmins.values());
    }

    /**
     * Busca um administrador no cache com base no login.
     * 
     * @param admin O admin com login a ser pesquisado.
     * @return O {@link Admin} correspondente ou {@code null} se não encontrado.
     */
    public Admin buscar(Admin admin) {
        return cacheAdmins.get(admin.getLogin());
    }

    /**
     * Busca um administrador no cache usando login e senha.
     * 
     * @param login Login do administrador.
     * @param senha Senha do administrador (criptografada).
     * @return O {@link Admin} correspondente ou {@code null} se não encontrado ou senha incorreta.
     */
    public Admin buscarPeloLoginSenha(String login, String senha) {
        Admin admin = cacheAdmins.get(login);
        if (admin != null && admin.getSenha().equals(senha)) {
            return admin;
        }
        return null;
    }

    /**
     * Atualiza um administrador no cache, substituindo o antigo pelo novo.
     * 
     * @param antigo O admin antigo (a ser substituído).
     * @param novo O novo objeto {@link Admin}.
     */
    public void atualizar(Admin antigo, Admin novo) {
        if (cacheAdmins.containsKey(antigo.getLogin())) {
            cacheAdmins.remove(antigo.getLogin());
            cacheAdmins.put(novo.getLogin(), novo);
            System.out.println("Admin atualizado na memoria.");
        } else {
            System.out.println("Admin nao encontrado para atualizacao.");
        }
    }

    /**
     * Remove um administrador do cache.
     * 
     * @param admin O {@link Admin} a ser removido.
     */
    public void deletar(Admin admin) {
        if (cacheAdmins.remove(admin.getLogin()) != null) {
            System.out.println("Admin removido da memoria.");
        } else {
            System.out.println("Admin nao encontrado para exclusao.");
        }
    }

    /**
     * Sincroniza o conteúdo do cache com o banco de dados.
     * Cadastra novos admins ou atualiza os existentes.
     */
    public void sincronizarComBanco() {
        for (Admin a : cacheAdmins.values()) {
            Admin existente = adminDAO.buscar(a);
            if (existente == null) {
                adminDAO.cadastrar(a);
            } else {
                adminDAO.atualizar(existente, a);
            }
        }
        System.out.println("Sincronizacao de Admins com o banco concluida.");
    }

    /**
     * Limpa completamente o cache em memória.
     */
    public void limparMemoria() {
        cacheAdmins.clear();
    }
}
