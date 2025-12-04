package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.OcorrenciaDAO;
import com.projetofinalpoo.models.Ocorrencia;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Controlador REST para gerenciar Ocorrências.
 * Fornece endpoints para listar, criar, atualizar e deletar ocorrências.
 * Adequado para consumo por frontend React via JSON.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/admin/ocorrencias")
public class OcorrenciaAdminController {

    private final OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();

    /**
     * Retorna todas as ocorrências cadastradas.
     *
     * @return lista de ocorrências
     */
    @GetMapping
    public List<Ocorrencia> listarOcorrencias() {
        return ocorrenciaDAO.buscarTodos();
    }

    /**
     * Salva uma nova ocorrência ou atualiza uma existente.
     *
     * @param ocorrencia objeto Ocorrencia enviado pelo frontend
     * @return ocorrência salva ou atualizada
     */
    @PostMapping("/salvar")
    public Ocorrencia salvarOcorrencia(@RequestBody Ocorrencia ocorrencia) {
        if (ocorrencia.getId() == 0) {
            ocorrenciaDAO.cadastrar(ocorrencia);
        } else {
            ocorrenciaDAO.atualizar(ocorrencia.getId(), ocorrencia);
        }
        return ocorrencia;
    }

    /**
     * Exclui uma ocorrência pelo seu ID.
     *
     * @param id ID da ocorrência a ser excluída
     */
    @DeleteMapping("/excluir/{id}")
    public void excluirOcorrencia(@PathVariable int id) {
        ocorrenciaDAO.deletar(id);
    }
}
