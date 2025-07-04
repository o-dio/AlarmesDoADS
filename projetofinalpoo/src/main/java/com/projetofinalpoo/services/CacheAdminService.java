package com.projetofinalpoo.services;

import com.projetofinalpoo.dao.AdminDAO;
import com.projetofinalpoo.models.Admin;

import java.util.ArrayList;
import java.util.HashMap;

public class CacheAdminService {
    private final HashMap<String, Admin> cacheAdmins = new HashMap<>();
    private final AdminDAO adminDAO = new AdminDAO();

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

    public void cadastrar(Admin admin) {
        cacheAdmins.put(admin.getLogin(), admin);
        System.out.println("Admin cadastrado na memoria.");
    }

    public ArrayList<Admin> buscarTodos() {
        return new ArrayList<>(cacheAdmins.values());
    }

    public Admin buscar(Admin admin) {
        return cacheAdmins.get(admin.getLogin());
    }

    public Admin buscarPeloLoginSenha(String login, String senha) {
        Admin admin = cacheAdmins.get(login);
        if (admin != null && admin.getSenha().equals(senha)) {
            return admin;
        }
        return null;
    }

    public void atualizar(Admin antigo, Admin novo) {
        if (cacheAdmins.containsKey(antigo.getLogin())) {
            cacheAdmins.remove(antigo.getLogin());
            cacheAdmins.put(novo.getLogin(), novo);
            System.out.println("Admin atualizado na memoria.");
        } else {
            System.out.println("Admin nao encontrado para atualizacao.");
        }
    }

    public void deletar(Admin admin) {
        if (cacheAdmins.remove(admin.getLogin()) != null) {
            System.out.println("Admin removido da memoria.");
        } else {
            System.out.println("Admin nao encontrado para exclusao.");
        }
    }

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

    public void limparMemoria() {
        cacheAdmins.clear();
    }
}
