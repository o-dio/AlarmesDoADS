package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.AdminDAO;
import com.projetofinalpoo.models.Admin;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controlador REST responsável pelas operações da entidade Admin.
 * Suporta buscar, salvar, atualizar e deletar administradores.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminDAO dao = new AdminDAO();

    @Autowired
    private HttpSession session;

    /**
     * Retorna um administrador baseado no login.
     *
     * @param login login do administrador
     * @return Admin correspondente ou null se não encontrado
     */
    @GetMapping("/{login}")
    public Admin buscarAdmin(@PathVariable String login) {
        Admin a = new Admin();
        a.setLogin(login);
        return dao.buscar(a);
    }

    /**
     * Salva ou atualiza um administrador.
     * Caso o login já exista, atualiza o administrador.
     * Caso contrário, cria um novo.
     *
     * @param admin objeto Admin enviado pelo front
     * @return Admin cadastrado ou atualizado
     */
    @PostMapping("/salvar")
    public Admin salvar(@RequestBody Admin admin) {
        //Pega dados da sessao
        Admin adminSessao = (Admin) session.getAttribute("usuarioLogado");

        // Verifica se já existe pelo login
        Admin existente = dao.buscar(adminSessao);

        if (existente == null) {
            dao.cadastrar(admin);
        } else {
            dao.atualizar(existente, admin);
        }

        return admin;
    }

    /**
     * Exclui um administrador baseado no login.
     *
     * @param login login do administrador
     * @return mensagem de confirmação
     */
    @DeleteMapping("/excluir/{login}")
    public String excluir(@PathVariable String login) {
        try {
            Admin admin = new Admin();
            admin.setLogin(login);
            dao.deletar(admin);
            return "Administrador com login " + login + " excluído com sucesso!";
        } catch (Exception e) {
            return "Erro ao excluir admin: " + e.getMessage();
        }
    }
}
