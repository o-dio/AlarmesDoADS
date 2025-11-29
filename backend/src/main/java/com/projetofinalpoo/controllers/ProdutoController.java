package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.ProdutoDAO;
import com.projetofinalpoo.models.Produto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 * Controlador para produtos
 */
@Controller
public class ProdutoController {

    private ProdutoDAO produtoDAO = new ProdutoDAO();

    @PostMapping("/produto/salvar")
    public String salvar(@RequestParam(required = false) Integer id,
            @RequestParam String dataInst,
            @RequestParam String dataRet,
            @RequestParam String defeito,
            @RequestParam int idEndereco) {

        Produto produto = new Produto();
        if (id != null) {
            produto.setId(id);
        }

        produto.setDataInst(LocalDate.parse(dataInst));
        produto.setDataRet(LocalDate.parse(dataRet));
        produto.setDefeito(Boolean.parseBoolean(defeito));
        produto.setIdEndereco(idEndereco);

        System.out.println("Salvando produto: " + produto);

        if (id == null || id == 0) {
            produtoDAO.cadastrar(produto);
        } else {
            produtoDAO.atualizar(id, produto);
        }

        return "redirect:/dashboard";
    }

    /**
     * Excluir produto
     * 
     * @param id Identificador do produto a ser excluido.
     * @return redirecionamento para o dashboard.
     */
    @GetMapping("/produto/excluir/{id}")
    public String excluir(@PathVariable int id) {
        produtoDAO.deletar(id);
        return "redirect:/dashboard";
    }
}
