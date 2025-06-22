package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.projetofinalpoo.models.Vigilante;

public class VigilanteDAO {
    private Connection conn = new ConexaoDAO().conectar();

    public void cadastrar(Vigilante vigilante) {
        String sql = "INSERT INTO \"Vigilante\" " +
                "(\"Login\", \"Senha\", \"Turno\", \"CargaHoraria\", \"Remuneracao\", " +
                "\"DataContratacao\", \"Fone\", \"Email\", \"FoneContato\", \"Role\") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, vigilante.getLogin());
            stmt.setString(2, vigilante.getSenha());
            stmt.setString(3, vigilante.getTurno());
            stmt.setTime(4, Time.valueOf(vigilante.getCargaHoraria()));
            stmt.setDouble(5, vigilante.getRemuneracao());
            stmt.setDate(6, Date.valueOf(vigilante.getDataContratacao()));
            stmt.setString(7, vigilante.getFone());
            stmt.setString(8, vigilante.getEmail());
            stmt.setString(9, vigilante.getFoneContato());
            stmt.setString(10, vigilante.getRole());

            stmt.executeUpdate();
            System.out.println("Vigilante cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar vigilante: " + e.getMessage());
        }
    }

    public ArrayList<Vigilante> buscarTodos() {
        String sql = "SELECT * FROM \"Vigilante\"";
        ArrayList<Vigilante> vigilantes = new ArrayList<Vigilante>();
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Vigilante v = new Vigilante(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("Turno"),
                    rs.getTime("CargaHoraria").toLocalTime().format(timeFormatter),
                    rs.getDouble("Remuneracao"),
                    rs.getDate("DataContratacao").toLocalDate().format(dataFormatter),
                    rs.getString("Fone"),
                    rs.getString("Email"),
                    rs.getString("FoneContato"),
                    rs.getString("Role")
                );
                vigilantes.add(v);
            }
    
            return vigilantes;
        } catch (Exception e) {
            System.out.println("Erro ao buscar vigilantes: " + e.getMessage());
            return null;
        }
    }

    public Vigilante buscar(Vigilante vigilante) {
        String sql = "SELECT * FROM \"Vigilante\" WHERE \"Login\" = ?";
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, vigilante.getLogin());
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                Vigilante v = new Vigilante(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("Turno"),
                    rs.getTime("CargaHoraria").toLocalTime().format(timeFormatter),
                    rs.getDouble("Remuneracao"),
                    rs.getDate("DataContratacao").toLocalDate().format(dataFormatter),
                    rs.getString("Fone"),
                    rs.getString("Email"),
                    rs.getString("FoneContato"),
                    rs.getString("Role")
                );
                return v;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar vigilante: " + e.getMessage());
            return null;
        }
    }

    public Vigilante buscarPeloLogin(String login) {
        String sql = "SELECT * FROM \"Vigilante\" WHERE \"Login\" = ?";
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Vigilante(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("Turno"),
                    rs.getTime("CargaHoraria").toLocalTime().format(timeFormatter),
                    rs.getDouble("Remuneracao"),
                    rs.getDate("DataContratacao").toLocalDate().format(dataFormatter),
                    rs.getString("Fone"),
                    rs.getString("Email"),
                    rs.getString("FoneContato"),
                    rs.getString("Role")
                );
            }

            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar vigilante: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(Vigilante oldVigilante, Vigilante newVigilante) {
        String sql = "UPDATE \"Vigilante\" SET " +
                "\"Login\" = ?, \"Senha\" = ?, \"Turno\" = ?, \"CargaHoraria\" = ?, \"Remuneracao\" = ?, " +
                "\"DataContratacao\" = ?, \"Fone\" = ?, \"Email\" = ?, \"FoneContato\" = ?, \"Role\" = ? " +
                "WHERE \"Login\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newVigilante.getLogin());
            stmt.setString(2, newVigilante.getSenha());
            stmt.setString(3, newVigilante.getTurno());
            stmt.setTime(4, Time.valueOf(newVigilante.getCargaHoraria()));
            stmt.setDouble(5, newVigilante.getRemuneracao());
            stmt.setDate(6, Date.valueOf(newVigilante.getDataContratacao()));
            stmt.setString(7, newVigilante.getFone());
            stmt.setString(8, newVigilante.getEmail());
            stmt.setString(9, newVigilante.getFoneContato());
            stmt.setString(10, newVigilante.getRole());
            stmt.setString(11, oldVigilante.getLogin());

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Vigilante atualizado com sucesso!");
            } else {
                System.out.println("Vigilante nao encontrado para atualizacao.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar vigilante: " + e.getMessage());
        }
    }

    public void deletar(String login) {
        String sql = "DELETE FROM \"Vigilante\" WHERE \"Login\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Vigilante deletado com sucesso!");
            } else {
                System.out.println("Vigilante nao encontrado para exclusao.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar vigilante: " + e.getMessage());
        }
    }
}
