package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.projetofinalpoo.models.Gravacao;

public class GravacaoDAO {
    private Connection conn = new ConexaoDAO().conectar();

    public void cadastrar(Gravacao gravacao) {
        String sql = "INSERT INTO \"Gravacao\" (\"Data\", \"Duracao\", \"Arquivo\", \"Descricao\", \"IdProduto\") " +
                     "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(gravacao.getData()));
            stmt.setTime(2, Time.valueOf(gravacao.getDuracao()));
            stmt.setString(3, gravacao.getArquivo());
            stmt.setString(4, gravacao.getDescricao());
            stmt.setInt(5, gravacao.getIdProduto());

            stmt.executeUpdate();
            System.out.println("Gravacao cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar gravacao: " + e.getMessage());
        }
    }

    public ArrayList<Gravacao> buscarTodos() {
        String sql = "SELECT * FROM \"Gravacao\"";
        ArrayList<Gravacao> gravacoes = new ArrayList<>();
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Gravacao g = new Gravacao(
                    rs.getDate("Data").toLocalDate().format(dataFormatter),
                    rs.getTime("Duracao").toLocalTime().format(timeFormatter),
                    rs.getString("Arquivo"),
                    rs.getString("Descricao"),
                    rs.getInt("IdProduto")
                );
                gravacoes.add(g);
            }

            return gravacoes;
        } catch (Exception e) {
            System.out.println("Erro ao buscar gravacoes: " + e.getMessage());
            return null;
        }
    }

    public Gravacao buscarPorId(int id) {
        String sql = "SELECT * FROM \"Gravacao\" WHERE id = ?";
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Gravacao(
                    rs.getDate("Data").toLocalDate().format(dataFormatter),
                    rs.getTime("Duracao").toLocalTime().format(timeFormatter),
                    rs.getString("Arquivo"),
                    rs.getString("Descricao"),
                    rs.getInt("IdProduto")
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar gravacao por id: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(int id, Gravacao gravacao) {
        String sql = "UPDATE \"Gravacao\" SET " +
                     "\"Data\" = ?, \"Duracao\" = ?, \"Arquivo\" = ?, \"Descricao\" = ?, \"IdProduto\" = ? " +
                     "WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(gravacao.getData()));
            stmt.setTime(2, Time.valueOf(gravacao.getDuracao()));
            stmt.setString(3, gravacao.getArquivo());
            stmt.setString(4, gravacao.getDescricao());
            stmt.setInt(5, gravacao.getIdProduto());
            stmt.setInt(6, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Gravacao atualizada com sucesso!");
            } else {
                System.out.println("Gravacao nao encontrada para atualizacao.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar gravacao: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM \"Gravacao\" WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Gravacao deletada com sucesso!");
            } else {
                System.out.println("Gravacao nao encontrada para exclusao.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar gravacao: " + e.getMessage());
        }
    }
}
