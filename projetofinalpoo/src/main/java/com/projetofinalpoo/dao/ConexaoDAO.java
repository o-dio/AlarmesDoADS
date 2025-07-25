package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por realizar a conexão com o banco de dados PostgreSQL.
 */
public class ConexaoDAO {

    /**
     * Estabelece a conexão com o banco de dados.
     *
     * @return Objeto Connection ativo ou null em caso de falha.
     */
    private static final String URL = "jdbc:postgresql://localhost:5432/alarmeadsbd";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "gustavo";

    /**
     * Conecta!
     * @return retorna objeto da conexão.
     */
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