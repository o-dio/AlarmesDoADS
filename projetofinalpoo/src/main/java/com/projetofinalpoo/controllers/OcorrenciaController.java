package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.OcorrenciaDAO;
import com.projetofinalpoo.dao.ProdutoDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Ocorrencia;
import com.projetofinalpoo.models.Vigilante;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas às ocorrências,
 * incluindo visualização da tela de ocorrências e cadastro de novas ocorrências.
 */
@Controller
public class OcorrenciaController {

    /**
     * Exibe a página de ocorrências apenas para usuários do tipo "vigilante".
     * Carrega produtos monitorados, com informações do cliente e endereço, para exibição.
     * 
     * @param session Sessão HTTP para verificar tipo do usuário logado.
     * @param model   Modelo para adicionar atributos que serão acessíveis na view.
     * @return Nome da view de ocorrências ou redirecionamento para página inicial se não for vigilante.
     */
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

    /**
     * Cadastra uma nova ocorrência para um produto monitorado, associando ao vigilante logado.
     * 
     * @param idProduto  Identificador do produto monitorado relacionado à ocorrência.
     * @param duracaoStr Duração da ocorrência em formato de tempo (HH:mm:ss).
     * @param session    Sessão HTTP para obter o vigilante logado.
     * @return Redirecionamento para a tela de ocorrências ou para login se não estiver autenticado.
     */
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

    /**
     * Classe ViewModel usada para transportar dados de produtos monitorados,
     * incluindo informações do cliente e endereço completo para a camada de visualização.
     */
    public static class ProdutoMonitoradoViewModel {
        private int idProduto;
        private String nomeCliente;
        private String telefoneCliente;
        private String enderecoCompleto;

        /**
         * Construtor para inicializar o ViewModel com os dados do produto monitorado.
         * 
         * @param idProduto       ID do produto monitorado.
         * @param nomeCliente     Nome do cliente associado.
         * @param telefoneCliente Telefone do cliente.
         * @param enderecoCompleto Endereço completo do cliente.
         */
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
