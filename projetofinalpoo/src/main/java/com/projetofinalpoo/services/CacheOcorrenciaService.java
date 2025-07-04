package com.projetofinalpoo.services;

import com.projetofinalpoo.dao.OcorrenciaDAO;
import com.projetofinalpoo.models.Ocorrencia;

import java.util.ArrayList;
import java.util.HashMap;

public class CacheOcorrenciaService {
    private HashMap<Integer, Ocorrencia> cacheOcorrencias = new HashMap<>();
    private OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();

    public void carregarDoBanco() {
        ArrayList<Ocorrencia> lista = ocorrenciaDAO.buscarTodos();
        if (lista != null) {
            limparMemoria();
            for (Ocorrencia o : lista) {
                cacheOcorrencias.put(o.getId(), o);
            }
        }
        System.out.println("Ocorrencias carregadas do banco para a memoria.");
    }

    public void cadastrar(Ocorrencia ocorrencia) {
        ocorrenciaDAO.cadastrar(ocorrencia);
        carregarDoBanco();
    }

    public ArrayList<Ocorrencia> buscarTodos() {
        if (cacheOcorrencias.isEmpty()) return null;
        return new ArrayList<>(cacheOcorrencias.values());
    }

    public Ocorrencia buscarPorId(int id) {
        Ocorrencia ocorrencia = cacheOcorrencias.get(id);
        if (ocorrencia == null) {
            System.out.println("Ocorrencia nao encontrada com ID: " + id);
        }
        return ocorrencia;
    }

    public void atualizar(int id, Ocorrencia novaOcorrencia) {
        if (cacheOcorrencias.containsKey(id)) {
            ocorrenciaDAO.atualizar(id, novaOcorrencia);
            cacheOcorrencias.put(id, novaOcorrencia);
            System.out.println("Ocorrencia atualizada na memoria com sucesso!");
        } else {
            System.out.println("Ocorrencia nao encontrada para atualizacao.");
        }
    }

    public void deletar(int id) {
        if (cacheOcorrencias.containsKey(id)) {
            ocorrenciaDAO.deletar(id);
            cacheOcorrencias.remove(id);
            System.out.println("Ocorrencia removida da memoria com sucesso!");
        } else {
            System.out.println("Ocorrencia nao encontrada para exclusao.");
        }
    }

    public void sincronizarComBanco() {
        for (Integer id : cacheOcorrencias.keySet()) {
            Ocorrencia ocorrencia = cacheOcorrencias.get(id);
            Ocorrencia existente = ocorrenciaDAO.buscarPorId(id);

            if (existente == null) {
                ocorrenciaDAO.cadastrar(ocorrencia);
            } else {
                ocorrenciaDAO.atualizar(id, ocorrencia);
            }
        }
        System.out.println("Sincronizacao de ocorrencias com banco concluida.");
    }

    public void limparMemoria() {
        cacheOcorrencias.clear();
        System.out.println("Memoria de ocorrencias limpa.");
    }
}
