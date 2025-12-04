package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.projetofinalpoo.models.Gravacao;

/**
 * Classe responsável por realizar operações de acesso a dados (DAO) da entidade
 * Gravacao.
 */
public class GravacaoDAO {
    private Connection conn = new ConexaoDAO().conectar();

    /**
     * Cadastra uma nova gravação no banco de dados.
     *
     * @param gravacao Objeto Gravacao a ser cadastrado.
     */
    public void cadastrar(Gravacao gravacao) {
        String sql = "INSERT INTO \"Gravacao\" (\"Data\", \"Duracao\", \"Arquivo\", \"Descricao\", \"IdProduto\") " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
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

    /**
     * Retorna uma lista com todas as gravações cadastradas.
     *
     * @return Lista de objetos Gravacao ou null em caso de erro.
     */

    public List<Gravacao> buscarTodos() {
        String sql = "SELECT * FROM \"Gravacao\"";
        List<Gravacao> lista = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
                
            while (rs.next()) {
                Gravacao g = new Gravacao(
                        rs.getInt("id"),
                        rs.getDate("Data").toLocalDate(),
                        rs.getTime("Duracao").toLocalTime(),
                        rs.getString("Arquivo"),
                        rs.getString("Descricao"),
                        rs.getInt("IdProduto"));
                lista.add(g);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar gravações: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Busca uma gravação pelo ID.
     *
     * @param id Identificador da gravação.
     * @return Objeto Gravacao correspondente ou null se não encontrado.
     */
    public Gravacao buscarPorId(int id) {
        String sql = "SELECT * FROM \"Gravacao\" WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Gravacao(
                        rs.getInt("id"),
                        rs.getDate("Data").toLocalDate(),
                        rs.getTime("Duracao").toLocalTime(),
                        rs.getString("Arquivo"),
                        rs.getString("Descricao"),
                        rs.getInt("IdProduto"));
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar gravacao por id: " + e.getMessage());
            return null;
        }
    }

    /**
     * Atualiza os dados de uma gravação existente.
     *
     * @param id       ID da gravação a ser atualizada.
     * @param gravacao Objeto Gravacao com os novos dados.
     */
    public void atualizar(int id, Gravacao gravacao) {
        String sql = "UPDATE \"Gravacao\" SET " +
                "\"Data\" = ?, \"Duracao\" = ?, \"Arquivo\" = ?, \"Descricao\" = ?, \"IdProduto\" = ? " +
                "WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
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

    /**
     * Remove uma gravação do banco de dados.
     *
     * @param id ID da gravação a ser removida.
     */
    public void deletar(int id) {
        String sql = "DELETE FROM \"Gravacao\" WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
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

    public List<Gravacao> buscarPorEndereco(int idEndereco) {
        List<Gravacao> lista = new ArrayList<>();

        String sql = """
                    SELECT g.* FROM "Gravacao" g
                    JOIN "Produto" p ON g."IdProduto" = p.id
                    WHERE p."IdEndereco" = ?
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEndereco);
            ResultSet rs = stmt.executeQuery();

            DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");

            while (rs.next()) {
                Gravacao g = new Gravacao(
                        rs.getInt("id"),
                        rs.getDate("Data").toLocalDate(),
                        rs.getTime("Duracao").toLocalTime(),
                        rs.getString("Arquivo"),
                        rs.getString("Descricao"),
                        rs.getInt("IdProduto"));

                lista.add(g);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar gravações: " + e.getMessage());
        }

        return lista;
    }

}
