package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.projetofinalpoo.models.Ocorrencia;

import com.projetofinalpoo.viewmodels.OcorrenciaCompletaViewModel;

/**
 * Classe responsável por realizar operações de acesso a dados (DAO) da entidade
 * Ocorrencia.
 */
public class OcorrenciaDAO {
    private Connection conn = new ConexaoDAO().conectar();

    /**
     * Cadastra uma nova ocorrência no banco de dados.
     *
     * @param ocorrencia Objeto Ocorrencia a ser cadastrado.
     */
    public void cadastrar(Ocorrencia ocorrencia) {
        String sql = "INSERT INTO \"Ocorrencia\" (\"Data\", \"Duracao\", \"IdVigilante\", \"IdProduto\") " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
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

    /**
     * Retorna uma lista com todas as ocorrências cadastradas.
     *
     * @return Lista de objetos Ocorrencia ou null em caso de erro.
     */
    public ArrayList<Ocorrencia> buscarTodos() {
        String sql = "SELECT * FROM \"Ocorrencia\"";
        ArrayList<Ocorrencia> ocorrencias = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("Data")));
                Ocorrencia o = new Ocorrencia(
                        rs.getInt("id"),
                        new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("Data")),
                        rs.getTime("Duracao").toLocalTime().format(timeFormatter),
                        rs.getInt("IdVigilante"),
                        rs.getInt("IdProduto"));
                ocorrencias.add(o);
            }

            return ocorrencias;
        } catch (Exception e) {
            System.out.println("Erro ao buscar ocorrencias: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca uma ocorrência pelo ID.
     *
     * @param id Identificador da ocorrência.
     * @return Objeto Ocorrencia correspondente ou null se não encontrado.
     */
    public Ocorrencia buscarPorId(int id) {
        String sql = "SELECT * FROM \"Ocorrencia\" WHERE id = ?";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Ocorrencia(
                        rs.getInt("id"),
                        new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("Data")),
                        rs.getTime("Duracao").toLocalTime().format(timeFormatter),
                        rs.getInt("IdVigilante"),
                        rs.getInt("IdProduto"));
            } else {
                System.out.println("Ocorrencia nao encontrada com ID: " + id);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar ocorrencia por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca ocorrências completas contendo produto, cliente, endereço e vigilante.
     *
     * @return Lista de OcorrenciaCompletaViewModel com dados agregados.
     */
    public List<OcorrenciaCompletaViewModel> buscarOcorrenciasCompletas() {
        List<OcorrenciaCompletaViewModel> lista = new ArrayList<>();

        String sql =
            "SELECT o.id AS ocorrencia_id, o.\"Duracao\", o.\"Data\", " +
            "p.id AS produto_id, " +
            "c.\"Login\" AS cliente_nome, c.\"Fone\" AS cliente_telefone, " +
            "e.\"Rua\", e.\"Numero\", e.\"Bairro\", e.\"Cidade\", e.\"Estado\", " +
            "v.\"Login\" AS vigilante_nome " +
            "FROM \"Ocorrencia\" o " +
            "JOIN \"Produto\" p ON p.id = o.\"IdProduto\" " +
            "JOIN \"Endereco\" e ON e.id = p.\"IdEndereco\" " +
            "JOIN \"Contrato_Endereco\" ce ON ce.\"IdEndereco\" = e.id " +
            "JOIN \"Contrato\" ct ON ct.id = ce.\"IdContrato\" " +
            "JOIN \"Cliente\" c ON c.id = ct.\"IdCliente\" " +
            "LEFT JOIN \"Vigilante\" v ON v.id = o.\"IdVigilante\"";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String endereco = String.format(
                    "%s, %s - %s, %s - %s",
                    rs.getString("Rua"),
                    rs.getString("Numero"),
                    rs.getString("Bairro"),
                    rs.getString("Cidade"),
                    rs.getString("Estado")
                );

                OcorrenciaCompletaViewModel oc = new OcorrenciaCompletaViewModel(
                        rs.getInt("ocorrencia_id"),
                        rs.getInt("produto_id"),
                        rs.getString("cliente_nome"),
                        rs.getString("cliente_telefone"),
                        endereco,
                        rs.getString("Duracao"),
                        rs.getDate("Data").toString(),
                        rs.getString("vigilante_nome")
                );

                lista.add(oc);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar ocorrências completas: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Atualiza os dados de uma ocorrência existente.
     *
     * @param id         ID da ocorrência a ser atualizada.
     * @param ocorrencia Objeto Ocorrencia com os novos dados.
     */
    public void atualizar(int id, Ocorrencia ocorrencia) {
        String sql = "UPDATE \"Ocorrencia\" SET \"Data\" = ?, \"Duracao\" = ?, \"IdVigilante\" = ?, \"IdProduto\" = ? "
                +
                "WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setDate(1, Date.valueOf(ocorrencia.getData()));
            stmt.setTime(2, Time.valueOf(ocorrencia.getDuracao()));
            stmt.setInt(3, ocorrencia.getIdVigilante());
            stmt.setInt(4, ocorrencia.getIdProduto());
            stmt.setInt(5, id);

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

    /**
     * Remove uma ocorrência do banco de dados.
     *
     * @param id ID da ocorrência a ser removida.
     */
    public void deletar(int id) {
        String sql = "DELETE FROM \"Ocorrencia\" WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);

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
