package com.projetofinalpoo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projetofinalpoo.dao.GravacaoDAO;
import com.projetofinalpoo.dao.ProdutoDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Endereco;
import com.projetofinalpoo.models.Gravacao;
import com.projetofinalpoo.models.Vigilante;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador do monitoramento
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class MonitoramentoController {

    /**
     * Mostra o painel de monitoramente
     * 
     * @param session Verifica se o usuario tem permissão para acessar a tela.
     * @param idEnderecoSelecionado Endereço a ser visto no painel de monitoramento
     * @return redireciona para página de login caso não haja autorização ou para o painel caso haja.
     */
    @GetMapping("/monitoramento")
    public Object mostrarPainelMonitoramento(
            HttpSession session,
            @RequestParam(value = "idEndereco", required = false) Integer idEnderecoSelecionado) {

        Vigilante vigilante = (Vigilante) session.getAttribute("usuarioLogado");
        if (vigilante == null) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("error", "Usuário não autenticado");
            return erro;
        }

        int idVigilante = new VigilanteDAO().buscarIdPorLogin(vigilante.getLogin());
        List<Endereco> enderecosMonitorados = new ProdutoDAO().buscarEnderecosDoVigilante(idVigilante);

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

        List<Gravacao> gravacoes = new ArrayList<>();
        if (enderecoSelecionado != null) {
            gravacoes = new GravacaoDAO().buscarPorEndereco(enderecoSelecionado.getId());
        }

        //Construindo o JSON para React
        Map<String, Object> response = new HashMap<>();
        response.put("enderecos", enderecosMonitorados);
        response.put("vigilante", vigilante);
        response.put("idEnderecoSelecionado", idEnderecoSelecionado);
        response.put("enderecoSelecionado", enderecoSelecionado);
        response.put("gravacoes", gravacoes);

        return response;
    }
}
