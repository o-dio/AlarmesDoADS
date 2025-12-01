package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.projetofinalpoo.models.Endereco;
import com.projetofinalpoo.models.Rota;

/**
 * Classe responsável pelas operações de acesso a dados (DAO) da entidade Rota.
 */
public class RotaDAO {
    private Connection conn = new ConexaoDAO().conectar();

    /**
     * Cadastra uma nova rota no banco de dados.
     *
     * @param rota Objeto Rota a ser inserido.
     */
    public void cadastrar(Rota rota) {
        String sql = "INSERT INTO \"Rota\" (\"Nome\", \"Bairro\", \"Descricao\", \"Observacao\") VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, rota.getNome());
            stmt.setString(2, rota.getBairro());
            stmt.setString(3, rota.getDescricao());
            stmt.setString(4, rota.getObservacao());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar rota: " + e.getMessage());
        }
    }

    /**
     * Busca todos os endereços associados a uma rota (via tabela Endereco_Rota)
     *
     * @param idRota ID da rota
     * @return lista de endereços pertencentes à rota
     */
    private ArrayList<Endereco> buscarEnderecosDaRota(int idRota) {

        ArrayList<Endereco> enderecos = new ArrayList<>();

        String sql = """
            SELECT e.*
            FROM "Endereco" e
            JOIN "Endereco_Rota" er ON er."IdEndereco" = e.id
            WHERE er."IdRota" = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRota);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Endereco e = new Endereco(
                    rs.getInt("id"),
                    rs.getString("Rua"),
                    rs.getString("Numero"),
                    rs.getString("Bairro"),
                    rs.getString("Cidade"),
                    rs.getString("Estado")
                );

                enderecos.add(e);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar endereços da rota: " + e.getMessage());
        }

        return enderecos;
    }

    /**
     * Retorna uma lista com todas as rotas cadastradas.
     *
     * @return Lista de objetos Rota.
     */
    public ArrayList<Rota> buscarTodos() {
        String sql = "SELECT * FROM \"Rota\"";
        ArrayList<Rota> rotas = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("Nome");
                String bairro = rs.getString("Bairro");
                String desc = rs.getString("Descricao");
                String obs = rs.getString("Observacao");

                Rota r = new Rota(id, nome, bairro, desc, obs);

                // carrega os endereços da rota (N:N)
                r.getEnderecos().addAll(buscarEnderecosDaRota(id));

                rotas.add(r);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar rotas: " + e.getMessage());
        }

        return rotas;
    }

    /**
     * Busca uma rota específica pelo ID.
     *
     * @param id Identificador da rota.
     * @return Objeto Rota correspondente, ou null se não encontrado.
     */
    public Rota buscarPorId(int id) {

        String sql = "SELECT * FROM \"Rota\" WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Rota r = new Rota(
                        rs.getInt("id"),
                        rs.getString("Nome"),
                        rs.getString("Bairro"),
                        rs.getString("Descricao"),
                        rs.getString("Observacao")
                );

                // carrega os endereços da rota
                r.getEnderecos().addAll(buscarEnderecosDaRota(id));

                return r;
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar rota por id: " + e.getMessage());
        }

        return null;
    }

    /**
     * Atualiza os dados de uma rota existente.
     *
     * @param rota Objeto Rota com os dados atualizados.
     */
    public void atualizar(Rota rota) {
        String sql = "UPDATE \"Rota\" SET \"Nome\" = ?, \"Bairro\" = ?, \"Descricao\" = ?, \"Observacao\" = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, rota.getNome());
            stmt.setString(2, rota.getBairro());
            stmt.setString(3, rota.getDescricao());
            stmt.setString(4, rota.getObservacao());
            stmt.setInt(5, rota.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar rota: " + e.getMessage());
        }
    }

    /**
     * Deleta uma rota com base no nome.
     *
     * @param nome Nome da rota a ser deletada.
     */
    public void deletar(String nome) {
        String sql = "DELETE FROM \"Rota\" WHERE \"Nome\" = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao deletar rota: " + e.getMessage());
        }
    }
}
