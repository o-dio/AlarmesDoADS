package com.projetofinalpoo.services;

import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Vigilante;

import java.util.ArrayList;
import java.util.HashMap;

public class CacheVigilanteService {
    private HashMap<String, Vigilante> cacheVigilantes = new HashMap<>();
    private VigilanteDAO vigilanteDAO = new VigilanteDAO();

    public void carregarDoBanco() {
        ArrayList<Vigilante> lista = vigilanteDAO.buscarTodos();
        if (lista != null) {
            limparMemoria();
            for (Vigilante v : lista) {
                cacheVigilantes.put(v.getLogin(), v);
            }
        }
        System.out.println("Vigilantes carregados do banco para a memoria.");
    }

    public void cadastrar(Vigilante vigilante) {
        cacheVigilantes.put(vigilante.getLogin(), vigilante);
        System.out.println("Vigilante cadastrado na memoria.");
    }

    public ArrayList<Vigilante> buscarTodos() {
        return new ArrayList<>(cacheVigilantes.values());
    }

    public Vigilante buscar(Vigilante vigilante) {
        return cacheVigilantes.get(vigilante.getLogin());
    }

    public Vigilante buscarPeloLogin(String login) {
        return cacheVigilantes.get(login);
    }

    public Vigilante buscarPeloLoginSenha(String login, String senha) {
        for (Vigilante v : cacheVigilantes.values()) {
            if (v.getLogin().equals(login) && v.getSenha().equals(senha)) {
                return v;
            }
        }
        System.out.println("Vigilante nao encontrado.");
        return null;
    }

    public void atualizar(Vigilante antigo, Vigilante novo) {
        if (cacheVigilantes.containsKey(antigo.getLogin())) {
            cacheVigilantes.remove(antigo.getLogin());
            cacheVigilantes.put(novo.getLogin(), novo);
            System.out.println("Vigilante atualizado na memoria.");
        } else {
            System.out.println("Vigilante nao encontrado para atualizacao.");
        }
    }

    public void deletar(String login) {
        if (cacheVigilantes.remove(login) != null) {
            System.out.println("Vigilante removido da memoria.");
        } else {
            System.out.println("Vigilante nao encontrado para exclusao.");
        }
    }

    public void sincronizarComBanco() {
        for (Vigilante v : cacheVigilantes.values()) {
            Vigilante existente = vigilanteDAO.buscarPeloLogin(v.getLogin());

            if (existente == null) {
                vigilanteDAO.cadastrar(v);
            } else {
                vigilanteDAO.atualizar(existente, v);
            }
        }
        System.out.println("Sincronizacao com o banco concluida.");
    }

    public void limparMemoria() {
        cacheVigilantes.clear();
    }
}
