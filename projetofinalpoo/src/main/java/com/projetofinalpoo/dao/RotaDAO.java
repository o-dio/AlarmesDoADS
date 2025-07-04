package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.projetofinalpoo.models.Rota;

public class RotaDAO {
    private Connection conn = new ConexaoDAO().conectar();

    public void cadastrar(Rota rota) {
        String sql = "INSERT INTO \"Rota\" (\"Nome\", \"Bairro\", \"Descricao\", \"Observacao\") VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id        = rs.getInt("id");
                String nome   = rs.getString("Nome");
                String bairro = rs.getString("Bairro");
                String desc   = rs.getString("Descricao");
                String obs    = rs.getString("Observacao");
                rotas.add(new Rota(id, nome, bairro, desc, obs));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar rotas: " + e.getMessage());
        }
        return rotas;
    }

    // Busca um id específico, ao invés de todos
    public Rota buscarPorId(int id) {
        String sql = "SELECT * FROM \"Rota\" WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Rota(
                    rs.getInt("id"),
                    rs.getString("Nome"),
                    rs.getString("Bairro"),
                    rs.getString("Descricao"),
                    rs.getString("Observacao")
                );
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar rota por id: " + e.getMessage());
        }
        return null;
    }

    public void atualizar(Rota rota) {
        String sql = "UPDATE \"Rota\" SET \"Bairro\" = ?, \"Descricao\" = ?, \"Observacao\" = ? WHERE \"Nome\" = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        String sql = "DELETE FROM \"Rota\" WHERE \"Nome\" = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao deletar rota: " + e.getMessage());
        }
    }
}
