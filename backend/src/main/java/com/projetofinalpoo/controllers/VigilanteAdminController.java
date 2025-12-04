package com.projetofinalpoo.controllers;

import org.springframework.web.bind.annotation.*; 

import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.ContatoInfo;
import com.projetofinalpoo.models.Vigilante;
import com.projetofinalpoo.services.HashMD5Service;

import java.util.List;

/**
 * Controlador REST para gerenciamento de vigilantes no dashboard do Admin.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/admin/vigilantes")
public class VigilanteAdminController {

    private final VigilanteDAO vigilanteDAO = new VigilanteDAO();

    /**
     * Lista todos os vigilantes cadastrados.
     *
     * @return lista de vigilantes em JSON
     */
    @GetMapping
    public List<Vigilante> listarVigilantes() {
        return vigilanteDAO.buscarTodos();
    }

    /**
     * Salva ou atualiza um vigilante.
     *
     * @param vigilante objeto Vigilante enviado via JSON no corpo da requisição
     * @return o vigilante salvo/atualizado
     */
    @PostMapping("/salvar")
    public Vigilante salvarVigilante(@RequestBody Vigilante vigilante) {
        // Se login for null ou vazio, cria novo vigilante
        if (vigilante.getLogin() == null || vigilante.getLogin().isEmpty()) {
            throw new IllegalArgumentException("O login do vigilante não pode ser vazio.");
        }

        // Verifica se já existe um vigilante com esse login
        Vigilante existing = vigilanteDAO.buscarPeloLogin(vigilante.getLogin());
        if (existing == null) {
            vigilante.setSenha(HashMD5Service.gerarMD5(vigilante.getSenha()));
            vigilanteDAO.cadastrar(vigilante);
        } else {
            vigilante.setSenha(HashMD5Service.gerarMD5(vigilante.getSenha()));
            vigilanteDAO.atualizar(existing, vigilante);
        }
        return vigilante;
    }

    /**
     * Exclui um vigilante pelo login.
     *
     * @param login login do vigilante a ser excluído
     */
    @DeleteMapping("/excluir/{login}")
    public void excluirVigilante(@PathVariable String login) {
        vigilanteDAO.deletar(login);
    }
}
