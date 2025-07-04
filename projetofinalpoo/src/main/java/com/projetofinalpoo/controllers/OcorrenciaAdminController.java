package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.OcorrenciaDAO;
import com.projetofinalpoo.models.Ocorrencia;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/dashboard/ocorrencias")
public class OcorrenciaAdminController {

    private final OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();

    @GetMapping
    public String listarOcorrencias(Model model) {
        List<Ocorrencia> ocorrencias = ocorrenciaDAO.buscarTodos();
        model.addAttribute("ocorrencias", ocorrencias);
        return "dashboardAdmin";
    }

    @PostMapping("/salvar")
    public String salvar(@RequestParam(required = false) Integer id,
                         @RequestParam String data,
                         @RequestParam String duracao,
                         @RequestParam int idVigilante,
                         @RequestParam int idProduto) {

        
        Ocorrencia ocorrencia = new Ocorrencia();
        if (id != null) {
    ocorrencia.setId(id);
}
        ocorrencia.setData(LocalDate.parse(data));
        ocorrencia.setDuracao(LocalTime.parse(duracao));
        ocorrencia.setIdVigilante(idVigilante);
        ocorrencia.setIdProduto(idProduto);

        if (id == null || id == 0) {
            ocorrenciaDAO.cadastrar(ocorrencia);
        } else {
            ocorrencia.setId(id);
            ocorrenciaDAO.atualizar(id, ocorrencia);
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable int id) {
        ocorrenciaDAO.deletar(id);
        return "redirect:/dashboard";
    }
}
