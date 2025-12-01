package com.projetofinalpoo.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.projetofinalpoo.dtos.RegistroDTO;
import com.projetofinalpoo.enums.Turno;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.ContatoInfo;
import com.projetofinalpoo.models.Vigilante;
import com.projetofinalpoo.services.CacheClienteService;
import com.projetofinalpoo.services.CacheVigilanteService;
import com.projetofinalpoo.services.HashMD5Service;

/**
 * Controlador responsável pelo registro de novos usuários no sistema, 
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class RegistroController {

    /** Serviço de cache para clientes, gerenciado pelo Spring. */
    private final CacheClienteService cacheClienteService;

    /** Serviço de cache para vigilantes, gerenciado pelo Spring. */
    private final CacheVigilanteService cacheVigilanteService;

    /**
     * Construtor do controller com injeção automática de dependências.
     *
     * @param cacheClienteService Serviço de cache de clientes.
     * @param cacheVigilanteService Serviço de cache de vigilantes.
     */
    @Autowired
    public RegistroController(CacheClienteService cacheClienteService,
                              CacheVigilanteService cacheVigilanteService) {
        this.cacheClienteService = cacheClienteService;
        this.cacheVigilanteService = cacheVigilanteService;
    }

    /**
     * Registra um novo usuário (cliente ou vigilante) no sistema.
     * @param dto Dados do usuário a serem cadastrados.
     * @return Retorna "OK" em caso de sucesso.
     */
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

            cacheClienteService.cadastrar(cliente);
            cacheClienteService.sincronizarComBanco();

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

            cacheVigilanteService.cadastrar(vigilante);
            cacheVigilanteService.sincronizarComBanco();
        }

        return "OK";
    }

    /**
     * Formata uma data no padrão "dd/MM/yyyy".
     *
     * @param dataISO Data no formato ISO (yyyy-MM-dd).
     * @return Data formatada como "dd/MM/yyyy".
     */
    private String formatarData(String dataISO) {
        LocalDate data = LocalDate.parse(dataISO);
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
