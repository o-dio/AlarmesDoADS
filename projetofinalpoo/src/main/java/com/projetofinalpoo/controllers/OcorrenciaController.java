package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.OcorrenciaDAO;
import com.projetofinalpoo.dao.ProdutoDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Ocorrencia;
import com.projetofinalpoo.models.Vigilante;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Controller
public class OcorrenciaController {

    @RequestMapping("/ocorrencia")
    public String telaOcorrencia(HttpSession session, Model model) {
        Object tipo = session.getAttribute("tipo");

        if (tipo != null && tipo.equals("vigilante")) {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            List<ProdutoMonitoradoViewModel> produtosMonitorados = produtoDAO.buscarProdutosComClienteEndereco();
            model.addAttribute("produtosMonitorados", produtosMonitorados);
            return "ocorrencia";
        }

        return "redirect:/";
    }

    @RequestMapping("/ocorrencia/cadastrar")
public String cadastrarOcorrencia(@RequestParam("idProduto") int idProduto,
                                  @RequestParam("duracao") String duracaoStr,
                                  HttpSession session) {

    Vigilante vigilante = (Vigilante) session.getAttribute("usuarioLogado");

    if (vigilante == null) {
        return "redirect:/login";
    }

    int idVigilante = new VigilanteDAO().buscarIdPorLogin(vigilante.getLogin());

    LocalDate dataAtual = LocalDate.now();
    LocalTime duracao = LocalTime.parse(duracaoStr); 

    Ocorrencia ocorrencia = new Ocorrencia(
        dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        duracao.toString(),
        idVigilante,
        idProduto
    );

    new OcorrenciaDAO().cadastrar(ocorrencia);

    return "redirect:/ocorrencia"; 
}

public static class ProdutoMonitoradoViewModel {
    private int idProduto;
    private String nomeCliente;
    private String telefoneCliente;
    private String enderecoCompleto;

    public ProdutoMonitoradoViewModel(int idProduto, String nomeCliente, String telefoneCliente, String enderecoCompleto) {
        this.idProduto = idProduto;
        this.nomeCliente = nomeCliente;
        this.telefoneCliente = telefoneCliente;
        this.enderecoCompleto = enderecoCompleto;
    }

    public int getIdProduto() { return idProduto; }
    public String getNomeCliente() { return nomeCliente; }
    public String getTelefoneCliente() { return telefoneCliente; }
    public String getEnderecoCompleto() { return enderecoCompleto; }
}
}
