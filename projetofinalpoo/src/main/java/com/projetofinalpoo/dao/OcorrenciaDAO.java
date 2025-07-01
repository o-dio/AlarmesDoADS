package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;

import com.projetofinalpoo.models.Ocorrencia;

public class OcorrenciaDAO {
    private Connection conn = new ConexaoDAO().conectar();

    public void cadastrar(Ocorrencia ocorrencia) {
        String sql = "INSERT INTO \"Ocorrencia\" (\"Data\", \"Duracao\", \"IdVigilante\", \"IdProduto\") " +
                     "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(ocorrencia.getData()));
            stmt.setTime(2, Time.valueOf(ocorrencia.getDuracao()));
            stmt.setInt(3, ocorrencia.getIdVigilante());
            stmt.setInt(4, ocorrencia.getIdProduto());

            stmt.executeUpdate();
            System.out.println("Ocorrencia cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar ocorrencia: " + e.getMessage());
        }
    }

    public ArrayList<Ocorrencia> buscarTodos() {
        String sql = "SELECT * FROM \"Ocorrencia\"";
        ArrayList<Ocorrencia> ocorrencias = new ArrayList<>();
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ocorrencia o = new Ocorrencia(
                    rs.getDate("Data").toLocalDate().format(dataFormatter),
                    rs.getTime("Duracao").toLocalTime().format(timeFormatter),
                    rs.getInt("IdVigilante"),
                    rs.getInt("IdProduto")
                );
                ocorrencias.add(o);
            }

            return ocorrencias;
        } catch (Exception e) {
            System.out.println("Erro ao buscar ocorrencias: " + e.getMessage());
            return null;
        }
    }

    public Ocorrencia buscar(Ocorrencia ocorrencia) {
        String sql = "SELECT * FROM \"Ocorrencia\" WHERE \"Data\" = ? AND \"Duracao\" = ? AND \"IdVigilante\" = ? AND \"IdProduto\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(ocorrencia.getData()));
            stmt.setTime(2, Time.valueOf(ocorrencia.getDuracao()));
            stmt.setInt(3, ocorrencia.getIdVigilante());
            stmt.setInt(4, ocorrencia.getIdProduto());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Ocorrencia(
                    rs.getDate("Data").toLocalDate().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    rs.getTime("Duracao").toLocalTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")),
                    rs.getInt("IdVigilante"),
                    rs.getInt("IdProduto")
                );
            } else {
                System.out.println("Ocorrencia nao encontrada.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar ocorrencia: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(Ocorrencia oldOcorrencia, Ocorrencia newOcorrencia) {
        String sql = "UPDATE \"Ocorrencia\" SET \"Data\" = ?, \"Duracao\" = ?, \"IdVigilante\" = ?, \"IdProduto\" = ? " +
                     "WHERE \"Data\" = ? AND \"Duracao\" = ? AND \"IdVigilante\" = ? AND \"IdProduto\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(newOcorrencia.getData()));
            stmt.setTime(2, Time.valueOf(newOcorrencia.getDuracao()));
            stmt.setInt(3, newOcorrencia.getIdVigilante());
            stmt.setInt(4, newOcorrencia.getIdProduto());      
            stmt.setDate(5, Date.valueOf(oldOcorrencia.getData()));
            stmt.setTime(6, Time.valueOf(oldOcorrencia.getDuracao()));
            stmt.setInt(7, oldOcorrencia.getIdVigilante());
            stmt.setInt(8, oldOcorrencia.getIdProduto());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Ocorrencia atualizada com sucesso!");
            } else {
                System.out.println("Ocorrencia nao encontrada para atualizacao.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar ocorrencia: " + e.getMessage());
        }
    }

    public void deletar(Ocorrencia ocorrencia) {
        String sql = "DELETE FROM \"Ocorrencia\" WHERE \"Data\" = ? AND \"Duracao\" = ? AND \"IdVigilante\" = ? AND \"IdProduto\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(ocorrencia.getData()));
            stmt.setTime(2, Time.valueOf(ocorrencia.getDuracao()));
            stmt.setInt(3, ocorrencia.getIdVigilante());
            stmt.setInt(4, ocorrencia.getIdProduto());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Ocorrencia deletada com sucesso!");
            } else {
                System.out.println("Ocorrencia nao encontrada para exclusao.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar ocorrencia: " + e.getMessage());
        }
    }
}
