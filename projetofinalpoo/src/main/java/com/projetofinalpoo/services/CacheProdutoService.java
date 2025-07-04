package com.projetofinalpoo.services;

import com.projetofinalpoo.dao.ProdutoDAO;
import com.projetofinalpoo.models.Produto;

import java.util.ArrayList;
import java.util.HashMap;

public class CacheProdutoService {
    private HashMap<Integer, Produto> cacheProdutos = new HashMap<>();
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public void carregarDoBanco() {
        ArrayList<Produto> produtosBanco = produtoDAO.buscarTodos();
        if (produtosBanco != null) {
            limparMemoria();
            for (Produto p : produtosBanco) {
                cacheProdutos.put(p.getId(), p);
            }
        }
        System.out.println("Produtos carregados do banco para a memoria.");
    }

    public void cadastrar(int id, Produto produto) {
        cacheProdutos.put(id, produto);
        System.out.println("Produto cadastrado na memoria com sucesso!");
    }

    public ArrayList<Produto> buscarTodos() {
        if (cacheProdutos.isEmpty()) return null;
        return new ArrayList<>(cacheProdutos.values());
    }

    public Produto buscarPorId(int id) {
        Produto produto = cacheProdutos.get(id);
        if (produto == null) {
            System.out.println("Produto nao encontrado com ID: " + id);
        }
        return produto;
    }

    public void atualizar(int id, Produto produto) {
        if (cacheProdutos.containsKey(id)) {
            cacheProdutos.put(id, produto);
            System.out.println("Produto atualizado na memoria com sucesso!");
        } else {
            System.out.println("Produto nao encontrado para atualizacao.");
        }
    }

    public void deletar(int id) {
        if (cacheProdutos.remove(id) != null) {
            System.out.println("Produto removido da memoria com sucesso!");
        } else {
            System.out.println("Produto nao encontrado para exclusao.");
        }
    }

    public void sincronizarComBanco() {
        for (Integer id : cacheProdutos.keySet()) {
            Produto produto = cacheProdutos.get(id);
            Produto existente = produtoDAO.buscarPorId(id);

            if (existente == null) {
                produtoDAO.cadastrar(produto);
            } else {
                produtoDAO.atualizar(id, produto);
            }
        }
        System.out.println("Sincronizacao de produtos com banco concluida.");
    }

    public void limparMemoria() {
        cacheProdutos.clear();
        System.out.println("Memoria de produtos limpa.");
    }
}
