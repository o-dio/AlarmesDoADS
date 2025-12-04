package com.projetofinalpoo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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

/**
 * Controlador REST responsável por fornecer os dados necessários
 * para o dashboard do administrador.
 * 
 * Este controlador substitui a versão antiga baseada em Thymeleaf.
 * Agora, todos os dados são devolvidos como JSON, para serem
 * consumidos pelo frontend em React.
 *
 * Endpoint principal:
 *   GET /api/dashboard
 *
 * Requisitos:
 *   - Usuário precisa estar autenticado na sessão como "admin".
 *
 * Retorno:
 *   - Objeto JSON contendo todas as listas e totais necessários
 *     para exibição no dashboard.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    /**
     * Retorna todos os dados necessários para exibição no dashboard do administrador.
     *
     * Verifica a sessão para garantir que o usuário logado é um administrador.
     * Caso não seja, retorna HTTP 401 (Unauthorized).
     *
     * @param session sessão HTTP contendo dados do usuário logado
     * @return ResponseEntity contendo JSON com listas e totais
     */
    @GetMapping
    public ResponseEntity<?> getDashboardData(HttpSession session) {
        // Verifica se usuário é Admin
        Admin admin = (Admin) session.getAttribute("usuarioLogado");
        String tipo = (String) session.getAttribute("tipo");

        if (admin == null || !"admin".equals(tipo)) {
            return ResponseEntity.status(401).body("Usuário não autorizado.");
        }

        // DAOs
        RotaDAO rotaDAO = new RotaDAO();
        OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        GravacaoDAO gravacaoDAO = new GravacaoDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        VigilanteDAO vigilanteDAO = new VigilanteDAO();

        // Carrega listas
        List<Rota> rondas = rotaDAO.buscarTodos();
        List<Ocorrencia> ocorrencias = ocorrenciaDAO.buscarTodos();
        List<Produto> alarmes = produtoDAO.buscarTodos();
        List<Gravacao> gravacoes = gravacaoDAO.buscarTodos();
        List<Cliente> clientes = clienteDAO.buscarTodos();
        List<Vigilante> vigilantes = vigilanteDAO.buscarTodos();

        // Monta resposta JSON
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("rondas", rondas);
        resposta.put("ocorrencias", ocorrencias);
        resposta.put("alarmes", alarmes);
        resposta.put("gravacoes", gravacoes);
        resposta.put("clientes", clientes);
        resposta.put("vigilantes", vigilantes);

        return ResponseEntity.ok(resposta);
    }
}
