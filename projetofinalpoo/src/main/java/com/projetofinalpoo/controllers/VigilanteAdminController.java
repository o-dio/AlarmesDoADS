package com.projetofinalpoo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.ContatoInfo;
import com.projetofinalpoo.models.Vigilante;

@Controller
@RequestMapping("/dashboard/vigilantes")
public class VigilanteAdminController {

    private final VigilanteDAO vigilanteDAO = new VigilanteDAO();

    

    @PostMapping("/salvar")
    public String salvar(@RequestParam(required = false) String loginAntigo,
                         @RequestParam String login,
                         @RequestParam String senha,
                         @RequestParam String turno,
                         @RequestParam String cargaHoraria,
                         @RequestParam double remuneracao,
                         @RequestParam String dataContratacao,
                         @RequestParam String fone,
                         @RequestParam String email,
                         @RequestParam String foneContato) {

        Vigilante vigilante = new Vigilante(login, senha, turno, cargaHoraria, remuneracao, dataContratacao, new ContatoInfo(fone, email, foneContato));

        if (loginAntigo == null || loginAntigo.isEmpty()) {
            vigilanteDAO.cadastrar(vigilante);
        } else {
            Vigilante oldVigilante = vigilanteDAO.buscarPeloLogin(loginAntigo);
            vigilanteDAO.atualizar(oldVigilante, vigilante);
        }
        return "redirect:/dashboard"; 
    }

    @GetMapping("/excluir/{login}")
    public String excluir(@PathVariable String login) {
        vigilanteDAO.deletar(login);
        return "redirect:/dashboard";
    }  
}
