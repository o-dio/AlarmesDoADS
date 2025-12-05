package com.projetofinalpoo.controllers;

import com.projetofinalpoo.dao.ContratoDao;
import com.projetofinalpoo.models.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/contrato")

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ContratoController {

    @Autowired
    private ContratoDao contratoDao;

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

    @GetMapping("/visualizar/{id}")
    public String visualizar(@PathVariable int id) {
        Contrato contrato = contratoDao.buscarPeloID(id);
        if (contrato == null) {
            return "Contrato não encontrado";
        }
        return contrato.getContrato();
    }
}