package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.ContratoDAO;
import com.projetofinalpoo.models.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller REST responsável pelo gerenciamento de endpoints relacionados
 * à entidade {@link Contrato}. Permite listar contratos por cliente e visualizar
 * contratos específicos.
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/contrato")
public class ContratoController {

    /** DAO responsável pelo acesso aos dados de contratos. */
    @Autowired
    private ContratoDAO contratoDao;

    /**
     * Endpoint para listar todos os contratos de um cliente específico pelo seu ID.
     *
     * @param id ID do cliente.
     * @return Lista de contratos do cliente. Retorna uma lista vazia em caso de erro ou se nenhum contrato for encontrado.
     */
    @GetMapping("/cliente/id/{id}")
    public ArrayList<Contrato> listarPorIdCliente(@PathVariable int id) {
        System.out.println(">>> Controller: Requisição recebida para listar contratos do Cliente ID: " + id); 
        try {
            ArrayList<Contrato> contratos = contratoDao.buscarPorIdCliente(id);
            System.out.println(">>> Controller: Retornando " + contratos.size() + " contratos para o Cliente ID: " + id); 
            return contratos;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Endpoint para visualizar o conteúdo do contrato por ID.
     *
     * @param id ID do contrato.
     * @return O texto do contrato se encontrado; caso contrário, retorna "Contrato não encontrado".
     */
    @GetMapping("/visualizar/{id}")
    public String visualizar(@PathVariable int id) {
        Contrato contrato = contratoDao.buscarPeloID(id);
        if (contrato == null) {
            return "Contrato não encontrado";
        }
        return contrato.getContrato();
    }
}
