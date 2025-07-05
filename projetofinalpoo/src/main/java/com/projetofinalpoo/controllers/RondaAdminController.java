package com.projetofinalpoo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.projetofinalpoo.dao.RotaDAO;
import com.projetofinalpoo.models.Rota;

import jakarta.servlet.http.HttpSession;
import com.projetofinalpoo.models.Admin;

@Controller
public class RondaAdminController {

    private RotaDAO rotaDAO = new RotaDAO();

@PostMapping("/salvar")
public String salvar(Rota rota) {
   if (rota.getId() == null){
        rotaDAO.cadastrar(rota);
    } else {
        rotaDAO.atualizar(rota);
    }
    return "redirect:/dashboard";
}


    @GetMapping("/excluir/{nome}")
    public String excluir(@PathVariable String nome) {
        rotaDAO.deletar(nome);
        return "redirect:/dashboard";
    }
}

