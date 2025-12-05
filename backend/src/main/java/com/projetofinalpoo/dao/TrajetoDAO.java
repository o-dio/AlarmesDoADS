package com.projetofinalpoo.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import com.projetofinalpoo.models.Trajeto;

/**
 * Classe responsável pelo acesso aos dados da entidade Trajeto.
 * Realiza operações de CRUD e consultas específicas sobre trajetos.
 */
@Repository
public class TrajetoDAO {
    private Connection conn = new ConexaoDAO().conectar();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Cadastra um novo trajeto no banco de dados.
     *
     * @param trajeto Objeto Trajeto a ser cadastrado.
     */
    public void cadastrar(Trajeto trajeto) {
        String sql = "INSERT INTO \"Trajeto\" (\"DataIni\", \"DataFim\", \"IdVigilante\", \"IdRota\") VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setDate(1, java.sql.Date.valueOf(trajeto.getDataIni()));

            if (trajeto.getDataFim() != null) {
                stmt.setDate(2, java.sql.Date.valueOf(trajeto.getDataFim()));
            } else {
                stmt.setNull(2, java.sql.Types.DATE);
            }

            stmt.setInt(3, trajeto.getIdVigilante());
            stmt.setInt(4, trajeto.getIdRota());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar trajeto: " + e.getMessage());
        }
    }

    /**
     * Retorna todos os trajetos cadastrados.
     *
     * @return Lista de objetos Trajeto.
     */
    public ArrayList<Trajeto> buscarTodos() {
        String sql = "SELECT * FROM \"Trajeto\"";
        ArrayList<Trajeto> trajetos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Date dataFimSql = rs.getDate("DataFim");
                trajetos.add(new Trajeto(
                    rs.getDate("DataIni").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    dataFimSql != null ? dataFimSql.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null,
                    rs.getInt("IdVigilante"),
                    rs.getInt("IdRota")
                ));
            }
            return trajetos;
        } catch (Exception e) {
            System.out.println("Erro ao buscar trajetos: " + e.getMessage());
            return null;
        }
    }

    /**
     * Atualiza um trajeto existente com base nos dados antigos.
     *
     * @param trajeto Novo trajeto com os dados atualizados.
     * @param dataIniOld Data inicial original no formato dd/MM/yyyy.
     * @param dataFimOld Data final original no formato dd/MM/yyyy.
     * @param idVigilanteOld ID original do vigilante.
     * @param idRotaOld ID original da rota.
     */
    public void atualizar(Trajeto trajeto, String dataIniOld, String dataFimOld, int idVigilanteOld, int idRotaOld) {
        String sql = "UPDATE \"Trajeto\" SET \"DataIni\" = ?, \"DataFim\" = ?, \"IdVigilante\" = ?, \"IdRota\" = ? " +
                "WHERE \"DataIni\" = ? AND \"DataFim\" = ? AND \"IdVigilante\" = ? AND \"IdRota\" = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
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

    /**
     * Deleta um trajeto com base nos dados fornecidos.
     *
     * @param dataIni Data inicial no formato dd/MM/yyyy.
     * @param dataFim Data final no formato dd/MM/yyyy.
     * @param idVigilante ID do vigilante.
     * @param idRota ID da rota.
     */
    public void deletar(String dataIni, String dataFim, int idVigilante, int idRota) {
        String sql = "DELETE FROM \"Trajeto\" WHERE \"DataIni\" = ? AND \"DataFim\" = ? AND \"IdVigilante\" = ? AND \"IdRota\" = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, LocalDate.parse(dataIni, DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(formatter));
            stmt.setString(2, LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(formatter));
            stmt.setInt(3, idVigilante);
            stmt.setInt(4, idRota);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao deletar trajeto: " + e.getMessage());
        }
    }

    /**
     * Retorna todos os trajetos de um vigilante a partir do login.
     *
     * @param login Login do vigilante.
     * @return Lista de trajetos.
     */
    public ArrayList<Trajeto> buscarPorLoginVigilante(String login) {
        ArrayList<Trajeto> trajetos = new ArrayList<>();
        String sql = "SELECT t.* FROM \"Trajeto\" t JOIN \"Vigilante\" v " +
                     "ON t.\"IdVigilante\" = v.\"Id\" " +
                     "WHERE v.\"Login\" = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Trajeto t = new Trajeto();
                t.setId(rs.getInt("Id"));
                t.setDataIni(rs.getDate("DataIni").toLocalDate());
                Date dataFimSql = rs.getDate("DataFim");
                t.setDataFim(dataFimSql != null ? dataFimSql.toLocalDate() : null);
                t.setIdVigilante(rs.getInt("IdVigilante"));
                t.setIdRota(rs.getInt("IdRota"));
                trajetos.add(t);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscarPorLoginVigilante: " + e.getMessage());
        }
        return trajetos;
    }

    /**
     * Busca um trajeto aberto (sem DataFim) por vigilante e rota.
     *
     * @param idVigilante ID do vigilante.
     * @param idRota ID da rota.
     * @return Trajeto correspondente ou null se não encontrado.
     */
    public Trajeto buscarTrajetoPorVigilanteERota(int idVigilante, int idRota) {
        String sql = "SELECT * FROM \"Trajeto\" WHERE \"IdVigilante\" = ? AND \"IdRota\" = ? AND \"DataFim\" IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, idVigilante);
            stmt.setInt(2, idRota);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Trajeto trajeto = new Trajeto();
                trajeto.setId(rs.getInt("Id"));
                trajeto.setDataIni(rs.getDate("DataIni").toLocalDate());
                trajeto.setDataFim(null);
                trajeto.setIdVigilante(rs.getInt("IdVigilante"));
                trajeto.setIdRota(rs.getInt("IdRota"));
                return trajeto;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar trajeto aberto: " + e.getMessage());
        }
        return null;
    }

    /**
     * Atualiza a DataFim de um trajeto aberto.
     *
     * @param trajeto Objeto Trajeto contendo a nova data de fim.
     */
    public void atualizarDataFim(Trajeto trajeto) {
        String sql = "UPDATE \"Trajeto\" SET \"DataFim\" = ? WHERE \"IdVigilante\" = ? AND \"IdRota\" = ? AND \"DataFim\" IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setDate(1, java.sql.Date.valueOf(trajeto.getDataFim()));
            stmt.setInt(2, trajeto.getIdVigilante());
            stmt.setInt(3, trajeto.getIdRota());
            int linhas = stmt.executeUpdate();
            System.out.println("Linhas atualizadas no checkout: " + linhas);
        } catch (Exception e) {
            System.out.println("Erro ao atualizar dataFim: " + e.getMessage());
        }
    }

    /**
     * Busca todos os trajetos realizados por um vigilante com base no ID.
     *
     * @param idVigilante ID do vigilante.
     * @return Lista de trajetos.
     */
    public ArrayList<Trajeto> buscarPorIdVigilante(int idVigilante) {
        ArrayList<Trajeto> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"Trajeto\" WHERE \"IdVigilante\" = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, idVigilante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Trajeto t = new Trajeto();
                t.setId(rs.getInt("Id"));
                t.setDataIni(rs.getDate("DataIni").toLocalDate());
                Date dataFimSql = rs.getDate("DataFim");
                t.setDataFim(dataFimSql != null ? dataFimSql.toLocalDate() : null);
                t.setIdVigilante(rs.getInt("IdVigilante"));
                t.setIdRota(rs.getInt("IdRota"));
                lista.add(t);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscarPorIdVigilante: " + e.getMessage());
        }
        return lista;
    }
}
