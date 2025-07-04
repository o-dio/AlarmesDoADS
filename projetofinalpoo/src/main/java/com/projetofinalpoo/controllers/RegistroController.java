package com.projetofinalpoo.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.Vigilante;
import com.projetofinalpoo.services.CacheClienteService;
import com.projetofinalpoo.services.CacheVigilanteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RegistroController {
    
    @RequestMapping(value="/registro", method=RequestMethod.GET)
    public String registro() {
        return "registro";
    }

    @RequestMapping(value="/registrar", method=RequestMethod.POST)
    public String registrar(HttpServletRequest request) {
        String role = request.getParameter("role");

        if ("cliente".equalsIgnoreCase(role)) {
            Cliente cliente = new Cliente(
                    request.getParameter("login"),
                    request.getParameter("senha"),
                    request.getParameter("cpf"),
                    formatarData(request.getParameter("dataNasc")),
                    request.getParameter("fone"),
                    request.getParameter("email"),
                    request.getParameter("foneContato")
            );
            System.out.println("Cliente registrado: " + cliente);
            CacheClienteService clienteCache = new CacheClienteService();
            clienteCache.cadastrar(cliente);
            clienteCache.sincronizarComBanco();

        } else if ("vigilante".equalsIgnoreCase(role)) {
            Vigilante vigilante = new Vigilante(
                    request.getParameter("login"),
                    request.getParameter("senha"),
                    request.getParameter("turno"),
                    "00:00:00",
                    0.0,
                    formatarData(request.getParameter("dataContratacao")),
                    request.getParameter("fone"),
                    request.getParameter("email"),
                    request.getParameter("foneContato")
            );
            System.out.println("Vigilante registrado: " + vigilante);
            CacheVigilanteService vigilanteCache = new CacheVigilanteService();
            vigilanteCache.cadastrar(vigilante);
            vigilanteCache.sincronizarComBanco();
        }

        return "redirect:/login";
    }

    private String formatarData(String dataISO) {
        LocalDate data = LocalDate.parse(dataISO);
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
