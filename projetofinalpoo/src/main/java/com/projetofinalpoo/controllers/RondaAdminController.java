package com.projetofinalpoo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.projetofinalpoo.dao.RotaDAO;
import com.projetofinalpoo.models.Rota;

/**
 * Controlador da ronda
 */
@Controller
public class RondaAdminController {

    private RotaDAO rotaDAO = new RotaDAO();

    /**
     * Salva a ronda
     * 
     * @param rota Rota da ronda a ser salva
     * @return redirecionamento para dashboard
     */
    @PostMapping("/salvar")
    public String salvar(Rota rota) {
        if (rota.getId() == null) {
            rotaDAO.cadastrar(rota);
        } else {
            rotaDAO.atualizar(rota);
        }
        return "redirect:/dashboard";
    }

    /**
     * Exclui uma ronda
     * 
     * @param nome nome da ronda a ser excluida
     * @return redirecionamento para dashboard
     */
    @GetMapping("/excluir/{nome}")
    public String excluir(@PathVariable String nome) {
        rotaDAO.deletar(nome);
        return "redirect:/dashboard";
    }
}
