package com.projetofinalpoo.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projetofinalpoo.enums.Turno;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.ContatoInfo;
import com.projetofinalpoo.models.Vigilante;
import com.projetofinalpoo.services.CacheClienteService;
import com.projetofinalpoo.services.CacheVigilanteService;
import com.projetofinalpoo.services.HashMD5Service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controlador responsável pelo registro de novos usuários no sistema,
 * incluindo clientes e vigilantes.
 */
@Controller
public class RegistroController {
    
    /**
     * Exibe a página de registro para o usuário escolher seu tipo (cliente ou vigilante).
     * 
     * @return Nome da view de registro.
     */
    @RequestMapping(value="/registro", method=RequestMethod.GET)
    public String registro() {
        return "registro";
    }

    /**
     * Processa o formulário de registro submetido via POST.
     * Cria um novo cliente ou vigilante conforme o parâmetro 'role' recebido.
     * 
     * @param request Objeto HttpServletRequest para acessar parâmetros do formulário.
     * @return Redirecionamento para a página de login após o registro.
     */
    @RequestMapping(value="/registrar", method=RequestMethod.POST)
    public String registrar(HttpServletRequest request) {
        String role = request.getParameter("role");

        if ("cliente".equalsIgnoreCase(role)) {
            Cliente cliente = new Cliente(
                    request.getParameter("login"),
                    HashMD5Service.gerarMD5(request.getParameter("senha")),
                    request.getParameter("cpf"),
                    formatarData(request.getParameter("dataNasc")),
                    new ContatoInfo(
                        request.getParameter("fone"),
                        request.getParameter("email"),
                        request.getParameter("foneContato")
                    )
            );
            System.out.println("Cliente registrado: " + cliente);
            CacheClienteService clienteCache = new CacheClienteService();
            clienteCache.cadastrar(cliente);
            clienteCache.sincronizarComBanco();

        } else if ("vigilante".equalsIgnoreCase(role)) {
            Turno turno;
            try {
                String turnoParam = request.getParameter("turno");
                if (turnoParam == null) {
                    throw new IllegalArgumentException("Parametro 'turno' nao informado.");
                }
                turno = Turno.valueOf(turnoParam.toUpperCase());
            } catch (IllegalArgumentException e) {
                turno = Turno.D;
                System.out.println("Valor invalido para turno recebido: " + e.getMessage());
            }

            Vigilante vigilante = new Vigilante(
                    request.getParameter("login"),
                    HashMD5Service.gerarMD5(request.getParameter("senha")),
                    turno.name(),
                    "00:00:00",
                    0.0,
                    formatarData(request.getParameter("dataContratacao")),
                    new ContatoInfo(
                        request.getParameter("fone"),
                        request.getParameter("email"),
                        request.getParameter("foneContato")
                    )
            );
            System.out.println("Vigilante registrado: " + vigilante);
            CacheVigilanteService vigilanteCache = new CacheVigilanteService();
            vigilanteCache.cadastrar(vigilante);
            vigilanteCache.sincronizarComBanco();
        }

        return "redirect:/login";
    }

    /**
     * Converte uma data em formato ISO (yyyy-MM-dd) para o formato dd/MM/yyyy.
     * 
     * @param dataISO Data em formato ISO.
     * @return Data formatada no padrão dd/MM/yyyy.
     */
    private String formatarData(String dataISO) {
        LocalDate data = LocalDate.parse(dataISO);
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
