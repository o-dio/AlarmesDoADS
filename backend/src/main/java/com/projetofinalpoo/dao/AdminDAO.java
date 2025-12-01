package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.projetofinalpoo.models.Admin;
import org.springframework.stereotype.Repository;

/**
 * Classe responsável pelo acesso e manipulação dos dados da entidade Admin no
 * banco de dados.
 */
@Repository
public class AdminDAO {
    private Connection conn = new ConexaoDAO().conectar();

    /**
     * Cadastra um novo administrador no banco de dados.
     *
     * @param admin Objeto Admin a ser cadastrado.
     */
    public void cadastrar(Admin admin) {
        String sql = "INSERT INTO \"Admin\" " +
                "(\"Login\", \"Senha\")" +
                "VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, admin.getLogin());
            stmt.setString(2, admin.getSenha());

            stmt.executeUpdate();
            System.out.println("Admin cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar admin: " + e.getMessage());
        }
    }

    /**
     * Retorna uma lista com todos os administradores cadastrados.
     *
     * @return Lista de objetos Admin ou null se nenhum for encontrado.
     */
    public ArrayList<Admin> buscarTodos() {
        String sql = "SELECT * FROM \"Admin\"";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();
            ArrayList<Admin> admins = new ArrayList<Admin>();

            while (rs.next()) {
                admins.add(new Admin(
                        rs.getString("Login"),
                        rs.getString("Senha")));
            }

            if (admins.size() > 0) {
                return admins;
            }

            return null;

        } catch (Exception e) {
            System.out.println("Erro ao buscar admins: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca um administrador pelo login.
     *
     * @param admin Objeto Admin com o login preenchido.
     * @return Objeto Admin completo ou null se não encontrado.
     */
    public Admin buscar(Admin admin) {
        String sql = "SELECT * FROM \"Admin\" WHERE \"Login\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, admin.getLogin());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getString("Login"),
                        rs.getString("Senha"));
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar admin: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca um administrador pelo login e senha.
     *
     * @param login Login do administrador.
     * @param senha Senha do administrador.
     * @return Objeto Admin correspondente ou null se não encontrado.
     */
    public Admin buscarPeloLoginSenha(String login, String senha) {
        String sql = "SELECT * FROM \"Admin\" WHERE \"Login\" = ? AND \"Senha\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getString("Login"),
                        rs.getString("Senha"));
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar admin: " + e.getMessage());
            return null;
        }
    }

    /**
     * Atualiza os dados de um administrador existente.
     *
     * @param oldAdmin Objeto Admin com o login antigo.
     * @param newAdmin Objeto Admin com os novos dados.
     */
    public void atualizar(Admin oldAdmin, Admin newAdmin) {
        String sql = "UPDATE \"Admin\" SET " +
                "\"Login\" = ?, " +
                "\"Senha\" = ? " +
                "WHERE \"Login\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, newAdmin.getLogin());
            stmt.setString(2, newAdmin.getSenha());
            stmt.setString(3, oldAdmin.getLogin());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Admin atualizado com sucesso!");
            } else {
                System.out.println("Admin nao encontrado para atualizacao.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao atualizar admin: " + e.getMessage());
        }
    }

    /**
     * Deleta um administrador do banco de dados.
     *
     * @param admin Objeto Admin a ser deletado.
     */
    public void deletar(Admin admin) throws Exception {
        String sql = "DELETE FROM \"Admin\" WHERE \"Login\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, admin.getLogin());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Admin deletado com sucesso!");
            } else {
                System.out.println("Admin nao encontrado para excluir.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao deletar admin: " + e.getMessage());
        }
    }
}
