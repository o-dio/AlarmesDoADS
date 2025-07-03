package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.TrajetoDAO;
import com.projetofinalpoo.dao.RotaDAO;
import com.projetofinalpoo.models.Trajeto;
import com.projetofinalpoo.models.Rota;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RondaController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @GetMapping("/rondas")
    public String exibirRondas(HttpSession session, Model model) {
        // pEGA LOGIN DO VIGILANTE
        String loginVigilante = (String) session.getAttribute("loginVigilante");

        // REDIRECIONA DE VOLTA
        if (loginVigilante == null) {
            return "redirect:/login";
        }

        TrajetoDAO trajetoDAO = new TrajetoDAO();
        RotaDAO rotaDAO = new RotaDAO();

        // Trajetos ligados ao vigilante logado
        ArrayList<Trajeto> trajetos = trajetoDAO.buscarPorLoginVigilante(loginVigilante);
        System.out.println("Trajetos encontrados: " + trajetos.size());

        List<RondaViewModel> rondas = new ArrayList<>();

        for (Trajeto t : trajetos) {
            Rota rota = rotaDAO.buscarPorId(t.getIdRota());
            if (rota != null) {
                String enderecoCompleto = rota.getNome() + ", " + rota.getBairro(); 
                rondas.add(new RondaViewModel(
                        t.getDataIni().format(formatter),
                        t.getDataFim() != null ? t.getDataFim().format(formatter) : null,
                        rota.getNome(),
                        rota.getBairro(),
                        rota.getDescricao(),
                        t.getDataFim() == null ? "Em andamento" : "Finalizada",
                        enderecoCompleto 
                ));

            } else {
                System.out.println("nao encontrada");
            }
        }
        model.addAttribute("rondas", rondas);
        return "rondas"; 
    }

    // Classe interna Ronnda view para uso do Thymeleaf, basicamente trajeto/rota
    public static class RondaViewModel {
        private String dataIni;
        private String dataFim;
        private String local;
        private String bairro;
        private String descricao;
        private String status;
        private String enderecoCompleto;

        public RondaViewModel(String dataIni, String dataFim, String local, String bairro, String descricao,
                String status, String enderecoCompleto) {
            this.dataIni = dataIni;
            this.dataFim = dataFim;
            this.local = local;
            this.bairro = bairro;
            this.descricao = descricao;
            this.status = status;
            this.enderecoCompleto = enderecoCompleto;
        }
        public String getDataIni() {
            return dataIni;
        }

        public String getDataFim() {
            return dataFim;
        }

        public String getLocal() {
            return local;
        }

        public String getBairro() {
            return bairro;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getStatus() {
            return status;
        }

        public String getEnderecoCompleto() {
            return enderecoCompleto;
        }
    }
}
