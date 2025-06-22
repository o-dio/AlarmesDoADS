package com.projetofinalpoo.projetofinalpoo;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.projetofinalpoo.dao.AdminDAO;
import com.projetofinalpoo.models.Admin;


@SpringBootTest
public class testeAdminDAO {
    private static final Logger logger = LoggerFactory.getLogger(AdminDAO.class);
    private AdminDAO adminDao = new AdminDAO();

    public void cadastrar() {
        logger.info("\n\n==== Inicio teste Cadastrar Admin ====\n");
        Admin admin = new Admin("teste2", "Senhaforte123456", "A");
        adminDao.cadastrar(admin);
        logger.info("\n\n==== Fim ====\n");
    }

    public void buscarTodos() {
        logger.info("\n\n==== Inicio teste buscar todos os Admins ====\n");
        AdminDAO adminDao = new AdminDAO();
        ArrayList<Admin> findAdmins = adminDao.buscarTodos();
		findAdmins.forEach(a -> System.out.println(a.getLogin())); 
        logger.info("\n\n==== Fim ====\n");
    }

    public void buscar() {
        logger.info("\n\n==== Inicio teste buscar Admin ====\n");
        Admin admin = new Admin("teste2", "Senhaforte123456", "A");
        Admin findAdmin = adminDao.buscar(admin);
        System.out.println(findAdmin.getLogin());
        logger.info("\n\n==== Fim ====\n");
    }

    public void atualizar() {
        logger.info("\n\n==== Inicio teste atualizar Admin ====\n");
        try {
            Admin oldAdmin = new Admin("teste2", "Senhaforte123456", "A");
            Admin newAdmin = new Admin("testeAdminAtualizado", "Senhaforte123456", "A");
            System.out.println("Antes da atualizacao: " + adminDao.buscar(oldAdmin).getLogin());
            adminDao.atualizar(oldAdmin, newAdmin);
            System.out.println("Apos atualizacao: " + adminDao.buscar(newAdmin).getLogin());
        } catch(Exception e){};
        logger.info("\n\n==== Fim ====\n");
    }

    @Test
    public void deletar() {
        logger.info("\n\n==== Inicio teste deletar Admin ====\n");
        try {
            Admin admin = new Admin("teste", "teste", "A");
            System.out.println("Admin encontrado antes de deletar: " + adminDao.buscar(admin).getLogin());
            adminDao.deletar(admin);
            adminDao.buscar(admin).getLogin();
        } catch(Exception e){};
        logger.info("\n\n==== Fim ====\n");
    }
}
