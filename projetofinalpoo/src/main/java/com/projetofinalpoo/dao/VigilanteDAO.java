package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.projetofinalpoo.models.ContatoInfo;
import com.projetofinalpoo.models.Vigilante;

/**
 * Classe responsável por realizar operações de banco de dados relacionadas à
 * entidade Vigilante.
 * Fornece métodos para criar, ler, atualizar e deletar vigilantes, além de
 * buscas específicas.
 */
public class VigilanteDAO {
    private Connection conn = new ConexaoDAO().conectar();

    /**
     * Cadastra um novo vigilante no banco de dados.
     * 
     * @param vigilante objeto Vigilante com os dados a serem inseridos.
     */
    public void cadastrar(Vigilante vigilante) {
        String sql = "INSERT INTO \"Vigilante\" " +
                "(\"Login\", \"Senha\", \"Turno\", \"CargaHoraria\", \"Remuneracao\", " +
                "\"DataContratacao\", \"Fone\", \"Email\", \"FoneContato\") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, vigilante.getLogin());
            stmt.setString(2, vigilante.getSenha());
            stmt.setString(3, vigilante.getTurno());
            stmt.setTime(4, Time.valueOf(vigilante.getCargaHoraria()));
            stmt.setDouble(5, vigilante.getRemuneracao());
            stmt.setDate(6, Date.valueOf(vigilante.getDataContratacao()));
            stmt.setString(7, vigilante.getContatoInfo().getFone());
            stmt.setString(8, vigilante.getContatoInfo().getEmail());
            stmt.setString(9, vigilante.getContatoInfo().getFoneContato());

            stmt.executeUpdate();
            System.out.println("Vigilante cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar vigilante: " + e.getMessage());
        }
    }

    /**
     * Busca todos os vigilantes cadastrados no banco.
     * 
     * @return uma lista contendo todos os vigilantes encontrados. Retorna null em
     *         caso de erro.
     */
    public ArrayList<Vigilante> buscarTodos() {
        String sql = "SELECT * FROM \"Vigilante\"";
        ArrayList<Vigilante> vigilantes = new ArrayList<Vigilante>();
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vigilante v = new Vigilante(
                        rs.getString("Login"),
                        rs.getString("Senha"),
                        rs.getString("Turno"),
                        rs.getTime("CargaHoraria").toLocalTime().format(timeFormatter),
                        rs.getDouble("Remuneracao"),
                        rs.getDate("DataContratacao").toLocalDate().format(dataFormatter),
                        new ContatoInfo(
                                rs.getString("Fone"),
                                rs.getString("Email"),
                                rs.getString("FoneContato")));
                vigilantes.add(v);
            }

            return vigilantes;
        } catch (Exception e) {
            System.out.println("Erro ao buscar vigilantes: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca um vigilante no banco pelo login do vigilante.
     * 
     * @param vigilante objeto Vigilante contendo o login para busca.
     * @return um objeto Vigilante com os dados encontrados ou null se não
     *         encontrado.
     */
    public Vigilante buscar(Vigilante vigilante) {
        String sql = "SELECT * FROM \"Vigilante\" WHERE \"Login\" = ?";
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, vigilante.getLogin());
            ResultSet rs = stmt.executeQuery();
            
            Time cargaHorariaTime = rs.getTime("CargaHoraria");
            String cargaHorariaStr = null;
            if (cargaHorariaTime != null) {
                cargaHorariaStr = cargaHorariaTime.toLocalTime().format(timeFormatter);
            }
            if (rs.next()) {
                Vigilante v = new Vigilante(
                        rs.getString("Login"),
                        rs.getString("Senha"),
                        rs.getString("Turno"),
                        cargaHorariaStr,
                        rs.getDouble("Remuneracao"),
                        rs.getDate("DataContratacao").toLocalDate().format(dataFormatter),
                        new ContatoInfo(
                                rs.getString("Fone"),
                                rs.getString("Email"),
                                rs.getString("FoneContato")));
                return v;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar vigilante: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca um vigilante pelo login e senha.
     * 
     * @param login login do vigilante.
     * @param senha senha do vigilante.
     * @return objeto Vigilante correspondente ou null se não encontrado.
     */
    public Vigilante buscarPeloLoginSenha(String login, String senha) {
        String sql = "SELECT * FROM \"Vigilante\" WHERE \"Login\" = ? AND \"Senha\" = ?";
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Vigilante(
                        rs.getString("Login"),
                        rs.getString("Senha"),
                        rs.getString("Turno"),
                        rs.getTime("CargaHoraria").toLocalTime().format(timeFormatter),
                        rs.getDouble("Remuneracao"),
                        rs.getDate("DataContratacao").toLocalDate().format(dataFormatter),
                        new ContatoInfo(
                                rs.getString("Fone"),
                                rs.getString("Email"),
                                rs.getString("FoneContato")));
            } else {
                System.out.println("Vigilante nao encontrado");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar vigilante: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca um vigilante pelo login.
     * 
     * @param login login do vigilante.
     * @return objeto Vigilante correspondente ou null se não encontrado.
     */
    public Vigilante buscarPeloLogin(String login) {
        String sql = "SELECT * FROM \"Vigilante\" WHERE \"Login\" = ?";
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
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
                        new ContatoInfo(
                                rs.getString("Fone"),
                                rs.getString("Email"),
                                rs.getString("FoneContato")));
            }

            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar vigilante: " + e.getMessage());
            return null;
        }
    }

    /**
     * Atualiza os dados de um vigilante existente no banco.
     * 
     * @param oldVigilante objeto Vigilante contendo o login atual (identificador).
     * @param newVigilante objeto Vigilante com os novos dados para atualização.
     */
    public void atualizar(Vigilante oldVigilante, Vigilante newVigilante) {
        String sql = "UPDATE \"Vigilante\" SET " +
                "\"Login\" = ?, \"Senha\" = ?, \"Turno\" = ?, \"CargaHoraria\" = ?, \"Remuneracao\" = ?, " +
                "\"DataContratacao\" = ?, \"Fone\" = ?, \"Email\" = ?, \"FoneContato\" = ? " +
                "WHERE \"Login\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, newVigilante.getLogin());
            stmt.setString(2, newVigilante.getSenha());
            stmt.setString(3, newVigilante.getTurno());
            stmt.setTime(4, Time.valueOf(newVigilante.getCargaHoraria()));
            stmt.setDouble(5, newVigilante.getRemuneracao());
            stmt.setDate(6, Date.valueOf(newVigilante.getDataContratacao()));
            stmt.setString(7, newVigilante.getContatoInfo().getFone());
            stmt.setString(8, newVigilante.getContatoInfo().getEmail());
            stmt.setString(9, newVigilante.getContatoInfo().getFoneContato());
            stmt.setString(10, oldVigilante.getLogin());

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

    /**
     * Remove um vigilante do banco com base no login.
     * 
     * @param login login do vigilante a ser removido.
     */
    public void deletar(String login) {
        String sql = "DELETE FROM \"Vigilante\" WHERE \"Login\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
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

    /**
     * Busca o ID do vigilante pelo login.
     * 
     * @param login login do vigilante.
     * @return o ID do vigilante encontrado ou -1 caso não exista ou ocorra erro.
     */
    public int buscarIdPorLogin(String login) {
        String sql = "SELECT id FROM \"Vigilante\" WHERE \"Login\" = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar ID do vigilante: " + e.getMessage());
        }
        return -1;
    }

}
