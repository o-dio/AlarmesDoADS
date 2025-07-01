package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;

import com.projetofinalpoo.models.Produto;

public class ProdutoDAO {
    private Connection conn = new ConexaoDAO().conectar();

    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO \"Produto\" (\"dataInst\", \"dataRet\", \"defeito\", \"idEndereco\") VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(produto.getDataInst()));
            stmt.setDate(2, Date.valueOf(produto.getDataRet()));
            stmt.setBoolean(3, produto.isDefeito());
            stmt.setInt(4, produto.getIdEndereco());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public ArrayList<Produto> buscarTodos() {
        String sql = "SELECT * FROM \"Produto\"";
        ArrayList<Produto> produtos = new ArrayList<>();
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto(
                    rs.getDate("dataInst").toLocalDate().format(dataFormatter),
                    rs.getDate("dataRet").toLocalDate().format(timeFormatter),
                    rs.getBoolean("defeito"),
                    rs.getInt("idEndereco")
                );
                produtos.add(p);
            }
            return produtos;
        } catch (Exception e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(Produto produto) {
        String sql = "UPDATE \"Produto\" SET \"dataInst\" = ?, \"dataRet\" = ?, \"defeito\" = ? WHERE \"idEndereco\" = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(produto.getDataInst()));
            stmt.setDate(2, Date.valueOf(produto.getDataRet()));
            stmt.setBoolean(3, produto.isDefeito());
            stmt.setInt(4, produto.getIdEndereco());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public void deletar(int idEndereco) {
        String sql = "DELETE FROM \"Produto\" WHERE \"idEndereco\" = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEndereco);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
    }
}
