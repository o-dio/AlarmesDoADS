package com.projetofinalpoo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetofinalpoo.dao.RotaDAO;
import com.projetofinalpoo.models.Rota;

/**
 * Controlador REST responsável pelas operações de administração das rondas.
 * Permite criar, atualizar e excluir rondas via API JSON.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/admin/rondas")
public class RondaAdminController {

    private final RotaDAO rotaDAO = new RotaDAO();

    /**
     * Salva uma nova ronda ou atualiza uma existente.
     * Recebe um objeto Rota em formato JSON.
     *
     * @param rota objeto Rota recebido via JSON
     * @return ResponseEntity com status 200 em caso de sucesso
     */
    @PostMapping("/salvar")
    public ResponseEntity<?> salvarRonda(@RequestBody Rota rota) {
        if (rota.getId() == null) {
            rotaDAO.cadastrar(rota);
        } else {
            rotaDAO.atualizar(rota);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Exclui uma ronda pelo nome.
     *
     * @param nome nome da ronda a ser excluída
     * @return ResponseEntity com status 200 em caso de sucesso
     */
    @DeleteMapping("/excluir/{nome}")
    public ResponseEntity<?> excluirRonda(@PathVariable String nome) {
        rotaDAO.deletar(nome);
        return ResponseEntity.ok().build();
    }
}
