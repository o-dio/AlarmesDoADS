package com.projetofinalpoo.projetofinalpoo;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Vigilante;

public class testeVigilanteDAO {
    private static final Logger logger = LoggerFactory.getLogger(VigilanteDAO.class);
    private VigilanteDAO vigilanteDao = new VigilanteDAO();

    public void cadastrar() {
        logger.info("\n\n==== Inicio teste cadastrar vigilante ====\n");
        Vigilante vigilante = new Vigilante("fabio.silva", "senhaSegura123", "D", "08:00:00", 3500.0,
                                            "20/05/2020", "11988887777", "fabio@email.com", "11999996666", "V");
        vigilanteDao.cadastrar(vigilante);
        logger.info("\n\n==== Fim ====\n");
    }

    public void buscarTodos() {
        logger.info("\n\n==== Inicio teste buscar todos os vigilantes ====\n");
        ArrayList<Vigilante> findVigilantes = vigilanteDao.buscarTodos();
        findVigilantes.forEach(v -> System.out.println(v.getLogin()));
        logger.info("\n\n==== Fim ====\n");
    }

    public void buscar() {
        logger.info("\n\n==== Inicio teste buscar vigilante ====\n");
        Vigilante vigilante = vigilanteDao.buscarPeloLogin("fabio.silva");
        System.out.println(vigilante.getLogin());
        logger.info("\n\n==== Fim ====\n");
    }

    public void buscarPeloLoginSenha() {
        logger.info("\n\n==== Inicio teste buscar vigilante Pelo Login e Senha ====\n");
        Vigilante vigilante = vigilanteDao.buscarPeloLoginSenha("fabio.silva", "1234");
        System.out.println(vigilante.getLogin());
        logger.info("\n\n==== Fim ====\n");
    }

    public void atualizar() {
        logger.info("\n\n==== Inicio teste atualizar vigilante ====\n");
        try {
            Vigilante oldVigilante = vigilanteDao.buscarPeloLogin("fabio.silva");
            Vigilante newVigilante = vigilanteDao.buscarPeloLogin("fabio.silva");
            System.out.println("Antes da atualizacao: " + oldVigilante.getLogin());
            newVigilante.setLogin("paulao.silva");
            vigilanteDao.atualizar(oldVigilante, newVigilante);
            System.out.println("Apos atualizacao: " + vigilanteDao.buscar(newVigilante).getLogin());
        } catch(Exception e){};
        logger.info("\n\n==== Fim ====\n");
    }

    @Test
    public void deletarVigilante() {
        logger.info("\n\n==== Inicio teste deletar vigilante ====\n");
        try {
            Vigilante vigilante = vigilanteDao.buscarPeloLogin("paulao.silva");
            System.out.println("Vigilante encontrado antes de deletar: " + vigilante.getLogin());
            vigilanteDao.deletar(vigilante.getLogin());
            vigilanteDao.buscarPeloLogin(vigilante.getLogin());
        } catch (Exception e) {}
        logger.info("\n\n==== Fim ====\n");
    }
}
