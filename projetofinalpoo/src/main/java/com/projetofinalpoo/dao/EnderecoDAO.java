package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.projetofinalpoo.models.Endereco;
public class EnderecoDAO {
    private Connection conn;

    public EnderecoDAO() {
        this.conn = new ConexaoDAO().conectar();
    }

    public Endereco buscarPorId(int id) {
        Endereco e = null;
        String sql = "SELECT * FROM \"Endereco\" WHERE \"id\" = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                e = new Endereco();
                e.setId(rs.getInt("id"));
                e.setRua(rs.getString("rua"));
                e.setNumero(rs.getString("numero"));
                e.setBairro(rs.getString("bairro"));
                e.setCidade(rs.getString("cidade"));
                e.setEstado(rs.getString("estado"));
                e.setCep(rs.getString("cep"));
            }
        } catch (Exception ex) {
            System.out.println("Erro ao buscar endereço: " + ex.getMessage());
        }
        return e;
    }
}
