package com.projetofinalpoo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetofinalpoo.services.RelatorioGeralService;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/relatorio/geral")
public class RelatorioGeralController {

    @Autowired
    private RelatorioGeralService relatorioService;

    @GetMapping("/ver")
    public ResponseEntity<byte[]> visualizar() {
        byte[] pdf = relatorioService.gerarRelatorio();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("relatorio_geral.pdf").build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @GetMapping("/baixar")
    public ResponseEntity<byte[]> baixar() {
        byte[] pdf = relatorioService.gerarRelatorio();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("relatorio_geral.pdf").build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
