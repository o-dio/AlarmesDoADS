package com.projetofinalpoo.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

/**
 * Serviço utilitário {@link Service} responsável por gerar hashes MD5 a partir de strings de entrada.
 * 
 * <p>Este serviço é geralmente utilizado para criptografar senhas ou outros dados sensíveis
 * antes de armazená-los, embora o MD5 não seja recomendado para segurança criptográfica moderna
 * devido à sua vulnerabilidade a colisões.</p>
 */
@Service
public class HashMD5Service {

    /**
     * Gera o hash MD5 correspondente à string fornecida.
     *
     * @param input A string de entrada a ser convertida para hash MD5.
     * @return A representação hexadecimal do hash MD5.
     * @throws RuntimeException Caso o algoritmo MD5 não seja suportado na JVM.
     */
    public static String gerarMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashEmBytes = md.digest(input.getBytes());

            // Converter os bytes para hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashEmBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash MD5", e);
        }
    }

    /**
     * Método principal para testes manuais.
     * Exibe no console o hash MD5 da string "senha1".
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        System.out.println(HashMD5Service.gerarMD5("senha1"));
    }
}
