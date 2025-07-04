package com.projetofinalpoo.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashMD5Service {
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

    public static void main(String[] args) {
        System.out.println(HashMD5Service.gerarMD5("senha1"));
    }
}