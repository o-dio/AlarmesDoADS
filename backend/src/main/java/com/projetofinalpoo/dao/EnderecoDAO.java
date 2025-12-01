package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import com.projetofinalpoo.models.Endereco;
import com.projetofinalpoo.models.Rota;

/**
 * Estabelece os parametros para comunicação entre
 * classe Endereço e tabela Endereço do banco de dados
 */
public class EnderecoDAO {

    private Connection conn;

    /**
     * Estabelece a conexão com o banco de dados
     */
    public EnderecoDAO() {
        this.conn = new ConexaoDAO().conectar();
    }

    /**
     * Busca por parametro identificador
     * @param id parametro identificador a ser buscado
     * @return Endereço encontrado.
     */
    public Endereco buscarPorId(int id) {
        Endereco e = null;
        String sql = "SELECT * FROM \"Endereco\" WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                e = new Endereco();
                e.setId(rs.getInt("id"));
                e.setRua(rs.getString("Rua"));
                e.setNumero(String.valueOf(rs.getInt("Numero")));
                e.setBairro(rs.getString("Bairro"));
                e.setCidade(rs.getString("Cidade"));
                e.setEstado(rs.getString("Estado"));

                // O banco NÃO possui CEP → não setamos CEP

                // Carrega as rotas associadas ao endereço
                e.getRotas().addAll(buscarRotasDoEndereco(id));
            }

        } catch (Exception ex) {
            System.out.println("Erro ao buscar endereço: " + ex.getMessage());
        }
        return e;
    }

    /**
     * Busca todas as rotas que usam este endereço (tabela Endereco_Rota)
     */
    private List<Rota> buscarRotasDoEndereco(int idEndereco) {
        List<Rota> rotas = new ArrayList<>();

        String sql = "SELECT r.* FROM \"Endereco_Rota\" er "
                   + "JOIN \"Rota\" r ON er.\"IdRota\" = r.id "
                   + "WHERE er.\"IdEndereco\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEndereco);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rota r = new Rota(
                    rs.getInt("id"),
                    rs.getString("Nome"),
                    rs.getString("Bairro"),
                    rs.getString("Descricao"),
                    rs.getString("Observacao")
                );
                rotas.add(r);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar rotas do endereço: " + e.getMessage());
        }

        return rotas;
    }
}
