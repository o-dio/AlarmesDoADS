package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.projetofinalpoo.models.Rota;

public class RotaDAO {
    private Connection conn = new ConexaoDAO().conectar();

    public void cadastrar(Rota rota) {
        String sql = "INSERT INTO \"Rota\" (\"nome\", \"bairro\", \"descricao\", \"observacao\") VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, rota.getNome());
            stmt.setString(2, rota.getBairro());
            stmt.setString(3, rota.getDescricao());
            stmt.setString(4, rota.getObservacao());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar rota: " + e.getMessage());
        }
    }

    public ArrayList<Rota> buscarTodos() {
        String sql = "SELECT * FROM \"Rota\"";
        ArrayList<Rota> rotas = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Rota r = new Rota(
                    rs.getString("nome"),
                    rs.getString("bairro"),
                    rs.getString("descricao"),
                    rs.getString("observacao")
                );
                rotas.add(r);
            }
            return rotas;
        } catch (Exception e) {
            System.out.println("Erro ao buscar rotas: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(Rota rota) {
        String sql = "UPDATE \"Rota\" SET \"bairro\" = ?, \"descricao\" = ?, \"observacao\" = ? WHERE \"nome\" = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, rota.getBairro());
            stmt.setString(2, rota.getDescricao());
            stmt.setString(3, rota.getObservacao());
            stmt.setString(4, rota.getNome());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar rota: " + e.getMessage());
        }
    }

    public void deletar(String nome) {
        String sql = "DELETE FROM \"Rota\" WHERE \"nome\" = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao deletar rota: " + e.getMessage());
        }
    }
}
