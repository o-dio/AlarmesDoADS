package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.projetofinalpoo.models.Trajeto;

public class TrajetoDAO {
    private Connection conn = new ConexaoDAO().conectar();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void cadastrar(Trajeto trajeto) {
        String sql = "INSERT INTO \"Trajeto\" (\"dataIni\", \"dataFim\", \"idVigilante\", \"idRota\") VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, trajeto.getDataIni().format(formatter));
            stmt.setString(2, trajeto.getDataFim().format(formatter));
            stmt.setInt(3, trajeto.getIdVigilante());
            stmt.setInt(4, trajeto.getIdRota());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar trajeto: " + e.getMessage());
        }
    }

    public ArrayList<Trajeto> buscarTodos() {
        String sql = "SELECT * FROM \"Trajeto\"";
        ArrayList<Trajeto> trajetos = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Trajeto t = new Trajeto(
                    rs.getDate("dataIni").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    rs.getDate("dataFim").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    rs.getInt("idVigilante"),
                    rs.getInt("idRota")
                );
                trajetos.add(t);
            }
            return trajetos;
        } catch (Exception e) {
            System.out.println("Erro ao buscar trajetos: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(Trajeto trajeto, String dataIniOld, String dataFimOld, int idVigilanteOld, int idRotaOld) {
        String sql = "UPDATE \"Trajeto\" SET \"dataIni\" = ?, \"dataFim\" = ?, \"idVigilante\" = ?, \"idRota\" = ? " +
                     "WHERE \"dataIni\" = ? AND \"dataFim\" = ? AND \"idVigilante\" = ? AND \"idRota\" = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, trajeto.getDataIni().format(formatter));
            stmt.setString(2, trajeto.getDataFim().format(formatter));
            stmt.setInt(3, trajeto.getIdVigilante());
            stmt.setInt(4, trajeto.getIdRota());
            stmt.setString(5, LocalDate.parse(dataIniOld, DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(formatter));
            stmt.setString(6, LocalDate.parse(dataFimOld, DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(formatter));
            stmt.setInt(7, idVigilanteOld);
            stmt.setInt(8, idRotaOld);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar trajeto: " + e.getMessage());
        }
    }

    public void deletar(String dataIni, String dataFim, int idVigilante, int idRota) {
        String sql = "DELETE FROM \"Trajeto\" WHERE \"dataIni\" = ? AND \"dataFim\" = ? AND \"idVigilante\" = ? AND \"idRota\" = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, LocalDate.parse(dataIni, DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(formatter));
            stmt.setString(2, LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(formatter));
            stmt.setInt(3, idVigilante);
            stmt.setInt(4, idRota);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao deletar trajeto: " + e.getMessage());
        }
    }
}
