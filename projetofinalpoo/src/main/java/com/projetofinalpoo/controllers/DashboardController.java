package com.projetofinalpoo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.dao.GravacaoDAO;
import com.projetofinalpoo.dao.OcorrenciaDAO;
import com.projetofinalpoo.dao.ProdutoDAO;
import com.projetofinalpoo.dao.RotaDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Admin;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.Gravacao;
import com.projetofinalpoo.models.Ocorrencia;
import com.projetofinalpoo.models.Produto;
import com.projetofinalpoo.models.Rota;
import com.projetofinalpoo.models.Vigilante;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

@GetMapping("/dashboard")
public String exibirDashboard(HttpSession session, Model model) {
    Admin admin = (Admin) session.getAttribute("usuarioLogado");
    String tipo = (String) session.getAttribute("tipo");
    if (admin == null || !"admin".equals(tipo)) {
        return "redirect:/login";
    }

    RotaDAO rotaDAO = new RotaDAO();
    List<Rota> rondasList = rotaDAO.buscarTodos();
    model.addAttribute("totalRondas", rondasList.size());
    model.addAttribute("rondas", rondasList);

    OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
    List<Ocorrencia> ocorrenciasList = ocorrenciaDAO.buscarTodos();
    model.addAttribute("totalOcorrencias", ocorrenciasList.size());
    model.addAttribute("ocorrencias", ocorrenciasList);

    ProdutoDAO produtoDAO = new ProdutoDAO();
    List<Produto> alarmesList = produtoDAO.buscarTodos();
    model.addAttribute("totalAlarmes", alarmesList.size());
    model.addAttribute("alarmes", alarmesList);

    GravacaoDAO gravacaoDao = new GravacaoDAO();
    List<Gravacao> gravacoes = gravacaoDao.buscarTodos();
        if (gravacoes == null) {
            gravacoes = new ArrayList<>();
        }
    model.addAttribute("totalRelatos", gravacoes.size());
    model.addAttribute("gravacoes", gravacoes);

    ClienteDAO clienteDAO = new ClienteDAO();
    List<Cliente> clienteList = clienteDAO.buscarTodos();
    model.addAttribute("totalClientes", clienteList.size());
    

    VigilanteDAO vigilanteDAO = new VigilanteDAO();
    List<Vigilante> vigilantesList = vigilanteDAO.buscarTodos();
    model.addAttribute("totalVigilantes", vigilantesList.size());
    model.addAttribute("vigilantes", vigilantesList);

    return "dashboardAdmin";
}

}