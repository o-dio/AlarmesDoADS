package com.projetofinalpoo.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetofinalpoo.dao.GravacaoDAO;
import com.projetofinalpoo.models.Gravacao;

@Controller
public class GravacaoController {

    private GravacaoDAO gravacaoDAO = new GravacaoDAO();

    @GetMapping("/dashboardAdmin")
    public String dashboardAdmin(Model model) {
        model.addAttribute("gravacoes", gravacaoDAO.buscarTodos());
        return "dashboardAdmin";
    }

    @PostMapping("/gravacao/salvar")
    public String salvar(@RequestParam(required = false) Integer id,
            @RequestParam String data,
            @RequestParam String duracao,
            @RequestParam String arquivo,
            @RequestParam String descricao,
            @RequestParam int idProduto) {
        // Formatos esperados: data "yyyy-MM-dd" (HTML input date), duracao "HH:mm:ss"
        // (input time)
        DateTimeFormatter dataFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // "yyyy-MM-dd"
        DateTimeFormatter horaFormatter = DateTimeFormatter.ISO_LOCAL_TIME; // "HH:mm:ss"

        LocalDate dataParse = LocalDate.parse(data, dataFormatter);
        LocalTime duracaoParse = LocalTime.parse(duracao, horaFormatter);

        Gravacao gravacao = new Gravacao();
        if (id != null && id != 0) {
            gravacao.setId(id);
        }
        gravacao.setData(dataParse);
        gravacao.setDuracao(duracaoParse);
        gravacao.setArquivo(arquivo);
        gravacao.setDescricao(descricao);
        gravacao.setIdProduto(idProduto);

        if (id == null || id == 0) {
            gravacaoDAO.cadastrar(gravacao);
        } else {
            gravacaoDAO.atualizar(id, gravacao);
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/gravacao/excluir/{id}")
    public String excluir(@PathVariable int id) {
        gravacaoDAO.deletar(id);
        return "redirect:/dashboard";
    }
}
