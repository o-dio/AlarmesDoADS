package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.ProdutoDAO;
import com.projetofinalpoo.models.Produto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para Produtos.
 * Agora adequado ao frontend em React.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutoDAO produtoDAO = new ProdutoDAO();

    /**
     * Lista todos os produtos existentes.
     * @return lista de produtos em JSON
     */
    @GetMapping
    public List<Produto> listar() {
        return produtoDAO.buscarTodos();
    }

    /**
     * Cadastra ou atualiza um produto.
     */
    @PostMapping("/salvar")
    public Produto salvar(@RequestBody Produto produto) {

        if (produto.getId() == 0) {
            produtoDAO.cadastrar(produto);
        } else {
            produtoDAO.atualizar(produto.getId(), produto);
        }

        return produto;
    }

    /**
     * Exclui um produto pelo ID.
     */
    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable int id) {
        produtoDAO.deletar(id);
    }
}
