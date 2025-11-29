package com.projetofinalpoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal responsável por inicializar a aplicação Spring Boot.
 *
 * <p>Ao executar este método, o Spring Boot configura automaticamente
 * todo o ambiente da aplicação, como os beans, controllers, serviços,
 * e recursos definidos com anotações Spring.</p>
 *
 * <p>Este é o ponto de entrada da aplicação.</p>
 */
@SpringBootApplication
public class ProjetofinalpooApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     *
     * @param args Argumentos de linha de comando (opcionais).
     */
    public static void main(String[] args) {
        SpringApplication.run(ProjetofinalpooApplication.class, args);
    }
}
