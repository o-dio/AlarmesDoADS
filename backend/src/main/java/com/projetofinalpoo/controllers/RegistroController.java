package com.projetofinalpoo.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.*;

import com.projetofinalpoo.dtos.RegistroDTO;
import com.projetofinalpoo.enums.Turno;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.ContatoInfo;
import com.projetofinalpoo.models.Vigilante;
import com.projetofinalpoo.services.CacheClienteService;
import com.projetofinalpoo.services.CacheVigilanteService;
import com.projetofinalpoo.services.HashMD5Service;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api") // recomendado para evitar conflito com páginas reais
public class RegistroController {

    @PostMapping("/registrar")
    public String registrar(@RequestBody RegistroDTO dto) {

        if ("cliente".equalsIgnoreCase(dto.role)) {
            Cliente cliente = new Cliente(
                dto.login,
                HashMD5Service.gerarMD5(dto.senha),
                dto.cpf,
                formatarData(dto.dataNasc),
                new ContatoInfo(
                    dto.fone,
                    dto.email,
                    dto.foneContato
                )
            );

            System.out.println("Cliente registrado: " + cliente);

            CacheClienteService clienteCache = new CacheClienteService();
            clienteCache.cadastrar(cliente);
            clienteCache.sincronizarComBanco();

        } else if ("vigilante".equalsIgnoreCase(dto.role)) {
            Turno turno = "N".equalsIgnoreCase(dto.turno) ? Turno.N : Turno.D;

            Vigilante vigilante = new Vigilante(
                dto.login,
                HashMD5Service.gerarMD5(dto.senha),
                turno.name(),
                "00:00:00",
                0.0,
                formatarData(dto.dataContratacao),
                new ContatoInfo(
                    dto.fone,
                    dto.email,
                    dto.foneContato
                )
            );

            System.out.println("Vigilante registrado: " + vigilante);

            CacheVigilanteService vigilanteCache = new CacheVigilanteService();
            vigilanteCache.cadastrar(vigilante);
            vigilanteCache.sincronizarComBanco();
        }

        return "OK";
    }

    private String formatarData(String dataISO) {
        LocalDate data = LocalDate.parse(dataISO);
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
