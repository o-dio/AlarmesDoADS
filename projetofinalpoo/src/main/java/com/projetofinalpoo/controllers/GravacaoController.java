package com.projetofinalpoo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.projetofinalpoo.dao.GravacaoDAO;
import com.projetofinalpoo.models.Gravacao;

@Controller
public class GravacaoController {

    private GravacaoDAO gravacaoDAO = new GravacaoDAO();

    @GetMapping("/dashboardAdmin")
    public String dashboardAdmin(Model model) {
        List<Gravacao> gravacoes = gravacaoDAO.buscarTodos();
        model.addAttribute("gravacoes", gravacoes);
        return "dashboardAdmin";
    }
}
