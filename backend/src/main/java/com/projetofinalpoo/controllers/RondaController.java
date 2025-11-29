package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.TrajetoDAO;
import com.projetofinalpoo.dao.RotaDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Trajeto;
import com.projetofinalpoo.models.Vigilante;
import com.projetofinalpoo.models.Rota;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas às rondas,
 * incluindo check-in, check-out e listagem das rondas do vigilante logado.
 */
@Controller
public class RondaController {
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Realiza o check-in do vigilante em uma rota específica,
     * criando um novo trajeto com data inicial.
     *
     * @param idRota  Identificador da rota.
     * @param session Sessão HTTP para obter o vigilante logado.
     * @return Redireciona para a página de rondas ou login se não autenticado.
     */
    @RequestMapping("/rondas/checkin")
    public String realizarCheckin(@RequestParam("idRota") int idRota, HttpSession session) {
        Vigilante vigilante = (Vigilante) session.getAttribute("usuarioLogado");
        if (vigilante == null) {
            return "redirect:/login";
        }

        int idVigilante = new VigilanteDAO().buscarIdPorLogin(vigilante.getLogin());

        Trajeto novo = new Trajeto();
        novo.setIdRota(idRota);
        novo.setIdVigilante(idVigilante);
        novo.setDataIni(LocalDate.now());
        new TrajetoDAO().cadastrar(novo);
        return "redirect:/rondas";
    }

    /**
     * Realiza o check-out do vigilante em uma rota específica,
     * atualizando a data final do trajeto em andamento.
     *
     * @param idRota  Identificador da rota.
     * @param session Sessão HTTP para obter o vigilante logado.
     * @return Redireciona para a página de rondas ou login se não autenticado.
     */
    @RequestMapping("/rondas/checkout")
    public String realizarCheckout(@RequestParam("idRota") int idRota, HttpSession session) {
        Vigilante vigilante = (Vigilante) session.getAttribute("usuarioLogado");
        if (vigilante == null) {
            return "redirect:/login";
        }

        int idVigilante = new VigilanteDAO().buscarIdPorLogin(vigilante.getLogin());

        TrajetoDAO trajDao = new TrajetoDAO();

        Trajeto trajetoEmAndamento = trajDao.buscarTrajetoPorVigilanteERota(idVigilante, idRota);

        if (trajetoEmAndamento != null) {
            trajetoEmAndamento.setDataFim(LocalDate.now());
            trajDao.atualizarDataFim(trajetoEmAndamento);
        } else {
            System.out.println("Nenhum trajeto aberto encontrado para checkout.");
        }

        return "redirect:/rondas";
    }

    /**
     * Exibe a lista de rondas do vigilante logado, indicando quais estão em
     * andamento
     * e as rotas disponíveis para iniciar novas rondas.
     *
     * @param session Sessão HTTP para obter o vigilante logado.
     * @param model   Objeto Model para passagem de dados para a view.
     * @return Nome da view de rondas ou redireciona para login se não autenticado.
     */
    @RequestMapping("/rondas")
    public String exibirRondas(HttpSession session, Model model) {
        Vigilante vigilante = (Vigilante) session.getAttribute("usuarioLogado");
        if (vigilante == null) {
            return "redirect:/login";
        }

        int idVigilante = new VigilanteDAO()
                .buscarIdPorLogin(vigilante.getLogin());

        TrajetoDAO trajDao = new TrajetoDAO();
        ArrayList<Trajeto> trajetos = trajDao.buscarPorIdVigilante(idVigilante);

        List<RondaViewModel> rondas = new ArrayList<>();
        boolean emRonda = false;
        String enderecoRondaAtual = null;
        RotaDAO rotaDAO = new RotaDAO();

        for (Trajeto t : trajetos) {

            Rota r = rotaDAO.buscarPorId(t.getIdRota());
            if (r == null)
                continue;
            String endereco = r.getNome() + " – " + r.getBairro();

            if (t.getDataFim() == null && !emRonda) {
                emRonda = true;
                enderecoRondaAtual = endereco;
            }

            rondas.add(new RondaViewModel(
                    t.getDataIni().format(fmt),
                    t.getDataFim() != null ? t.getDataFim().format(fmt) : null,
                    r.getNome(),
                    r.getBairro(),
                    r.getDescricao(),
                    t.getDataFim() == null ? "Em andamento" : "Finalizada",
                    endereco,
                    t.getIdRota()));
        }
        List<Rota> rotasDisponiveis = rotaDAO.buscarTodos();
        model.addAttribute("rotasDisponiveis", rotasDisponiveis);

        model.addAttribute("rondas", rondas);
        model.addAttribute("emRonda", emRonda);
        model.addAttribute("enderecoRondaAtual", enderecoRondaAtual);

        return "rondas";
    }

    /**
     * ViewModel usada para encapsular dados da ronda que serão exibidos na
     * interface.
     */
    public static class RondaViewModel {
        private String dataIni, dataFim, local, bairro, descricao, status, enderecoCompleto;
        private int idRota;

        public RondaViewModel(String dataIni, String dataFim,
                String local, String bairro, String descricao,
                String status, String enderecoCompleto, int idRota) {
            this.dataIni = dataIni;
            this.dataFim = dataFim;
            this.local = local;
            this.bairro = bairro;
            this.descricao = descricao;
            this.status = status;
            this.enderecoCompleto = enderecoCompleto;
            this.idRota = idRota;
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

        public int getIdRota() {
            return idRota;
        }
    }
}
