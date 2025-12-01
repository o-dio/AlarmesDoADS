package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.TrajetoDAO;
import com.projetofinalpoo.dao.RotaDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Trajeto;
import com.projetofinalpoo.models.Vigilante;
import com.projetofinalpoo.models.Rota;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Controlador responsável por gerenciar as operações relacionadas às rondas,
 * incluindo check-in, check-out e listagem das rondas do vigilante logado.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/rondas")
public class RondaController {

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Realiza o check-in do vigilante em uma rota específica,
     * criando um novo trajeto com data inicial.
     *
     * @param idRota  Identificador da rota.
     * @param session Sessão HTTP para obter o vigilante logado.
     * @return JSON indicando sucesso ou erro.
     */
    @PostMapping("/checkin")
    public ResponseEntity<?> realizarCheckin(@RequestParam("idRota") int idRota, HttpSession session) {

        Vigilante vigilante = (Vigilante) session.getAttribute("usuarioLogado");
        if (vigilante == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Não autenticado"));
        }

        int idVigilante = new VigilanteDAO().buscarIdPorLogin(vigilante.getLogin());

        Trajeto novo = new Trajeto();
        novo.setIdRota(idRota);
        novo.setIdVigilante(idVigilante);
        novo.setDataIni(LocalDate.now());

        new TrajetoDAO().cadastrar(novo);

        return ResponseEntity.ok(Map.of("success", true));
    }

    /**
     * Realiza o check-out do vigilante em uma rota específica,
     * atualizando a data final do trajeto em andamento.
     *
     * @param idRota  Identificador da rota.
     * @param session Sessão HTTP para obter o vigilante logado.
     * @return JSON indicando sucesso ou erro.
     */
    @PostMapping("/checkout")
    public ResponseEntity<?> realizarCheckout(@RequestParam("idRota") int idRota, HttpSession session) {

        Vigilante vigilante = (Vigilante) session.getAttribute("usuarioLogado");
        if (vigilante == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Não autenticado"));
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

        return ResponseEntity.ok(Map.of("success", true));
    }

    /**
     * Exibe a lista de rondas do vigilante logado, indicando quais estão em
     * andamento e as rotas disponíveis para iniciar novas rondas.
     *
     * @param session Sessão HTTP para obter o vigilante logado.
     * @return JSON com todas as informações necessárias para o front React.
     */
    @GetMapping
    public ResponseEntity<?> exibirRondas(HttpSession session) {

        Vigilante vigilante = (Vigilante) session.getAttribute("usuarioLogado");
        if (vigilante == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Não autenticado"));
        }

        int idVigilante = new VigilanteDAO().buscarIdPorLogin(vigilante.getLogin());

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
                    t.getIdRota()
            ));
        }

        List<Rota> rotasDisponiveis = rotaDAO.buscarTodos();

        // Agora retornamos tudo em JSON para o React
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("rondas", rondas);
        resposta.put("emRonda", emRonda);
        resposta.put("enderecoRondaAtual", enderecoRondaAtual);
        resposta.put("rotasDisponiveis", rotasDisponiveis);

        return ResponseEntity.ok(resposta);
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

        public String getDataIni() { return dataIni; }
        public String getDataFim() { return dataFim; }
        public String getLocal() { return local; }
        public String getBairro() { return bairro; }
        public String getDescricao() { return descricao; }
        public String getStatus() { return status; }
        public String getEnderecoCompleto() { return enderecoCompleto; }
        public int getIdRota() { return idRota; }
    }
}
