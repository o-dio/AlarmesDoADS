package com.projetofinalpoo.controllers; 

import com.projetofinalpoo.dao.GravacaoDAO;
import com.projetofinalpoo.models.Gravacao;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador REST para Gravações.
 * Compatível com frontend React.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/gravacoes")
public class GravacaoController {

    private GravacaoDAO gravacaoDAO = new GravacaoDAO();

    /**
     * Lista todas as gravações existentes.
     */
    @GetMapping
    public List<Gravacao> listar() {
        return gravacaoDAO.buscarTodos();
    }

    /**
     * Salva ou atualiza uma gravação.
     */
    @PostMapping("/salvar")
    public Gravacao salvar(@RequestBody Gravacao gravacao) {

        if (gravacao.getId() == 0) {
            gravacaoDAO.cadastrar(gravacao);
        } else {
            gravacaoDAO.atualizar(gravacao.getId(), gravacao);
        }

        return gravacao;
    }

    /**
     * Exclui uma gravação pelo ID.
     */
    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable int id) {
        gravacaoDAO.deletar(id);
    }
}
