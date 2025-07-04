package com.projetofinalpoo.services;

import com.projetofinalpoo.dao.GravacaoDAO;
import com.projetofinalpoo.models.Gravacao;

import java.util.ArrayList;
import java.util.HashMap;

public class CacheGravacaoService {
    private HashMap<Integer, Gravacao> cacheGravacoes = new HashMap<>();
    private GravacaoDAO gravacaoDAO = new GravacaoDAO();

    public void carregarDoBanco() {
        ArrayList<Gravacao> gravacoesBanco = gravacaoDAO.buscarTodos();
        if (gravacoesBanco != null) {
            limparMemoria();
            for (Gravacao g : gravacoesBanco) {
                cacheGravacoes.put(g.getId(), g);
            }
        }
        System.out.println("Gravacoes carregadas do banco para a memoria.");
    }

    public void cadastrar(Gravacao gravacao) {
        gravacaoDAO.cadastrar(gravacao);
        carregarDoBanco();
        System.out.println("Gravacao cadastrada com sucesso!");
    }

    public ArrayList<Gravacao> buscarTodos() {
        if (cacheGravacoes.isEmpty()) return null;
        return new ArrayList<>(cacheGravacoes.values());
    }

    public Gravacao buscarPorId(int id) {
        Gravacao gravacao = cacheGravacoes.get(id);
        if (gravacao == null) {
            System.out.println("Gravacao nao encontrada com ID: " + id);
        }
        return gravacao;
    }

    public void atualizar(int id, Gravacao gravacao) {
        if (cacheGravacoes.containsKey(id)) {
            gravacaoDAO.atualizar(id, gravacao);
            cacheGravacoes.put(id, gravacao);
            System.out.println("Gravacao atualizada com sucesso!");
        } else {
            System.out.println("Gravacao nao encontrada para atualizacao.");
        }
    }

    public void deletar(int id) {
        if (cacheGravacoes.containsKey(id)) {
            gravacaoDAO.deletar(id);
            cacheGravacoes.remove(id);
            System.out.println("Gravacao removida com sucesso!");
        } else {
            System.out.println("Gravacao nao encontrada para exclusao.");
        }
    }

    public void sincronizarComBanco() {
        for (Integer id : cacheGravacoes.keySet()) {
            Gravacao gravacao = cacheGravacoes.get(id);
            Gravacao existente = gravacaoDAO.buscarPorId(id);

            if (existente == null) {
                gravacaoDAO.cadastrar(gravacao);
            } else {
                gravacaoDAO.atualizar(id, gravacao);
            }
        }
        System.out.println("Sincronizacao de gravacoes com banco concluida.");
    }

    public void limparMemoria() {
        cacheGravacoes.clear();
        System.out.println("Memoria de gravacoes limpa.");
    }
}
