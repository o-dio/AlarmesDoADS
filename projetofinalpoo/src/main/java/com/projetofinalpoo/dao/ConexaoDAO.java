package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/alarmeadsbd";
    private static final String USUARIO = "postgres";
//SENHA DIFERENTE NAO DAR COMMIT
    private static final String SENHA = "gustavo";


    public Connection conectar() {
        try {
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexao com o banco estabelecida com sucesso.");
            return conexao;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }
}