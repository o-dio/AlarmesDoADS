package com.projetofinalpoo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetofinalpoo.dao.GravacaoDAO;
import com.projetofinalpoo.dao.ProdutoDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Endereco;
import com.projetofinalpoo.models.Gravacao;
import com.projetofinalpoo.models.Vigilante;

import jakarta.servlet.http.HttpSession;

/**
 * Controlador do monitoramento
 */
@Controller
public class MonitoramentoController {

    /**
     * Mostra o painel de monitoramente
     * 
     * @param session Verifica se o usuario tem permissão para acessar a tela.
     * @param model Objeto a ser usado para criar atributos necessários da sessão.
     * @param idEnderecoSelecionado Endereço a ser visto no painel de monitoramento
     * @return redireciona para página de login caso não haja autorização ou para o painel caso haja.
     */
    @GetMapping("/monitoramento")
    public String mostrarPainelMonitoramento(
            HttpSession session,
            Model model,
            @RequestParam(value = "idEndereco", required = false) Integer idEnderecoSelecionado) {

        Vigilante vigilante = (Vigilante) session.getAttribute("usuarioLogado");
        if (vigilante == null) {
            return "redirect:/login";
        }

        int idVigilante = new VigilanteDAO().buscarIdPorLogin(vigilante.getLogin());
        List<Endereco> enderecosMonitorados = new ProdutoDAO().buscarEnderecosDoVigilante(idVigilante);

        model.addAttribute("enderecos", enderecosMonitorados);
        model.addAttribute("vigilante", vigilante);

        Endereco enderecoSelecionado = null;

        if (idEnderecoSelecionado == null && !enderecosMonitorados.isEmpty()) {
            enderecoSelecionado = enderecosMonitorados.get(0);
        } else if (idEnderecoSelecionado != null) {
            for (Endereco e : enderecosMonitorados) {
                if (e.getId() == idEnderecoSelecionado) {
                    enderecoSelecionado = e;
                    break;
                }
            }
        }

        model.addAttribute("idEnderecoSelecionado", idEnderecoSelecionado);
        model.addAttribute("enderecoSelecionado", enderecoSelecionado);

        List<Gravacao> gravacoes = new ArrayList<>();
        if (enderecoSelecionado != null) {
            gravacoes = new GravacaoDAO().buscarPorEndereco(enderecoSelecionado.getId());
        }
        model.addAttribute("gravacoes", gravacoes);

        return "monitoramento";
    }
}
