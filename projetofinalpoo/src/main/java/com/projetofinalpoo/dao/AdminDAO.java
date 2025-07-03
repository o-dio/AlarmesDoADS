package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList; 

import com.projetofinalpoo.models.Admin;

public class AdminDAO {
    private Connection conn = new ConexaoDAO().conectar();

    public void cadastrar(Admin admin) {
        String sql = "INSERT INTO \"Admin\" " +
        "(\"Login\", \"Senha\")" +
        "VALUES (?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, admin.getLogin());
            stmt.setString(2, admin.getSenha());

            stmt.executeUpdate();
            System.out.println("Admin cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar admin: " + e.getMessage());
        }
    }

    public ArrayList<Admin> buscarTodos() {
        String sql = "SELECT * FROM \"Admin\"";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Admin> admins = new ArrayList<Admin>();

            while (rs.next()) {
                admins.add(new Admin(
                    rs.getString("Login"),
                    rs.getString("Senha")
                ));
            }

            if(admins.size() > 0) {
                return admins;
            }

            return null;

        } catch (Exception e) {
            System.out.println("Erro ao buscar admins: " + e.getMessage());
            return null;
        }
    }

    public Admin buscar(Admin admin) {
        String sql = "SELECT * FROM \"Admin\" WHERE \"Login\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, admin.getLogin());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Admin findAdmin = new Admin(
                    rs.getString("Login"),
                    rs.getString("Senha")
                );
                return findAdmin;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar admin: " + e.getMessage());
            return null;
        }
    }

    public void atualizar(Admin oldAdmin, Admin newAdmin) {
        String sql = "UPDATE \"Admin\" SET " +
        "\"Login\" = ?, " +
        "\"Senha\" = ? " +
        "WHERE \"Login\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
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

    public void deletar(Admin admin) throws Exception {
        String sql = "DELETE FROM \"Admin\" WHERE \"Login\" = ?";
    
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

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
