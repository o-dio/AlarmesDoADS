package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.OcorrenciaDAO;
import com.projetofinalpoo.dao.ProdutoDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Ocorrencia;
import com.projetofinalpoo.models.Vigilante;
import com.projetofinalpoo.viewmodels.ProdutoMonitoradoViewModel;
import com.projetofinalpoo.viewmodels.OcorrenciaCompletaViewModel;
import com.projetofinalpoo.dtos.OcorrenciaCadastroDTO;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Controlador responsável por fornecer endpoints REST para operações relacionadas às ocorrências,
 * permitindo que o frontend em React possa listar ocorrências, consultar produtos monitorados
 * e cadastrar novas ocorrências relacionadas a vigilantes e produtos.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaController {

    /**
     * Retorna a lista de produtos monitorados, incluindo informações do cliente e endereço completo,
     * permitindo que o vigilante selecione o produto antes de registrar uma ocorrência.
     *
     * @param session Sessão HTTP para verificar o tipo do usuário logado.
     * @return Lista de ProdutoMonitoradoViewModel contendo produto + cliente + endereço.
     */
    @GetMapping("/produtos-monitorados")
    public List<ProdutoMonitoradoViewModel> listarProdutosMonitorados(HttpSession session) {

        Object tipo = session.getAttribute("tipo");

        if (tipo == null || !tipo.equals("vigilante")) {
            return List.of();
        }

        ProdutoDAO produtoDAO = new ProdutoDAO();
        return produtoDAO.buscarProdutosComClienteEndereco();
    }

    /**
     * Lista todas as ocorrências registradas, retornando informações completas,
     * incluindo produto, cliente, endereço, duração, data e vigilante responsável.
     *
     * @param session Sessão HTTP para verificar autenticação.
     * @return Lista de OcorrenciaCompletaViewModel contendo todos os dados necessários para exibição.
     */
    @GetMapping
    public List<OcorrenciaCompletaViewModel> listarOcorrencias(HttpSession session) {

        Object tipo = session.getAttribute("tipo");

        if (tipo == null || !tipo.equals("vigilante")) {
            return List.of();
        }

        return new OcorrenciaDAO().buscarOcorrenciasCompletas();
    }

    /**
     * Cadastra uma nova ocorrência associada a um produto e ao vigilante logado no sistema.
     *
     * @param dto     DTO contendo o identificador do produto e a duração da ocorrência.
     * @param session Sessão HTTP contendo dados do vigilante autenticado.
     * @return String com status da operação.
     */
    @PostMapping("/cadastrar")
    public String cadastrarOcorrencia(@RequestBody OcorrenciaCadastroDTO dto,
                                      HttpSession session) {

        Vigilante vigilante = (Vigilante) session.getAttribute("usuarioLogado");

        if (vigilante == null) {
            return "erro: usuário não logado";
        }

        int idVigilante = new VigilanteDAO().buscarIdPorLogin(vigilante.getLogin());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = LocalDate.now().format(formatter);

        Ocorrencia ocorrencia = new Ocorrencia(
                dataFormatada,
                dto.getDuracao(),
                idVigilante,
                dto.getIdProduto()
        );

        new OcorrenciaDAO().cadastrar(ocorrencia);

        return "ok";
    }
}
