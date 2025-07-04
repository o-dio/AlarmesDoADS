package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
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
            System.out.println("Produto cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public ArrayList<Produto> buscarTodos() {
        String sql = "SELECT * FROM \"Produto\"";
        ArrayList<Produto> produtos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getInt("id"),
                    rs.getDate("dataInst").toLocalDate().format(formatter),
                    rs.getDate("dataRet").toLocalDate().format(formatter),
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

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM \"Produto\" WHERE \"id\" = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Produto p = new Produto(
                    rs.getInt("id"),
                    rs.getDate("dataInst").toLocalDate().format(formatter),
                    rs.getDate("dataRet").toLocalDate().format(formatter),
                    rs.getBoolean("defeito"),
                    rs.getInt("idEndereco")
                );
                return p;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar produto por id: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(int id, Produto produto) {
        String sql = "UPDATE \"Produto\" SET \"dataInst\" = ?, \"dataRet\" = ?, \"defeito\" = ?, \"idEndereco\" = ? WHERE \"id\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(produto.getDataInst()));
            stmt.setDate(2, Date.valueOf(produto.getDataRet()));
            stmt.setBoolean(3, produto.isDefeito());
            stmt.setInt(4, produto.getIdEndereco());
            stmt.setInt(5, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Produto nao encontrado para atualizacao.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM \"Produto\" WHERE \"id\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Produto deletado com sucesso!");
            } else {
                System.out.println("Produto nao encontrado para exclusao.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
    }
}
