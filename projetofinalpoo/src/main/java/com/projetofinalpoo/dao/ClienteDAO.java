package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

import com.projetofinalpoo.models.Cliente;

public class ClienteDAO {
    private Connection conn = new ConexaoDAO().conectar();

    public void cadastrar(Cliente cliente) {
        String sql = "INSERT INTO \"Cliente\" " +
        "(\"Login\", \"Senha\", \"CPF\", \"DataNasc\", \"Fone\", \"Email\", \"FoneContato\", \"Role\")" +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getLogin());
            stmt.setString(2, cliente.getSenha());
            stmt.setString(3, cliente.getCpf());
            stmt.setDate(4, Date.valueOf(cliente.getDataNasc()));
            stmt.setString(5, cliente.getFone());
            stmt.setString(6, cliente.getEmail());
            stmt.setString(7, cliente.getFoneContato());
            stmt.setString(8, cliente.getCargo());

            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public ArrayList<Cliente> buscarTodos() {
        String sql = "SELECT * FROM \"Cliente\"";
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("CPF"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DataNasc")),
                    rs.getString("Fone"),
                    rs.getString("Email"),
                    rs.getString("FoneContato"),
                    rs.getString("Role")
                ));
            }

            if(clientes.size() > 0) {
                return clientes;
            }

            return null;

        } catch (Exception e) {
            System.out.println("Erro ao buscar clientes: " + e.getMessage());
            return null;
        }
    }

    public Cliente buscar(Cliente cliente) {
        String sql = "SELECT * FROM \"Cliente\" WHERE \"CPF\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getCpf());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente findClient = new Cliente(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("CPF"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DataNasc")),
                    rs.getString("Fone"),
                    rs.getString("Email"),
                    rs.getString("FoneContato"),
                    rs.getString("Role")
                );
                return findClient;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
            return null;
        }
    }

    public Cliente buscarPeloLoginSenha(String login, String senha) {
        String sql = "SELECT * FROM \"Cliente\" WHERE \"Login\" = ? AND \"Senha\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente findClient = new Cliente(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("CPF"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DataNasc")),
                    rs.getString("Fone"),
                    rs.getString("Email"),
                    rs.getString("FoneContato"),
                    rs.getString("Role")
                );
                return findClient;
            } else {
                System.out.println("Cliente nao encontrado");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
            return null;
        }
    }

    public Cliente buscarPeloCpf(String cpf) {
        String sql = "SELECT * FROM \"Cliente\" WHERE \"CPF\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente findClient = new Cliente(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("CPF"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DataNasc")),
                    rs.getString("Fone"),
                    rs.getString("Email"),
                    rs.getString("FoneContato"),
                    rs.getString("Role")
                );
                return findClient;
            } else {
                System.out.println("Cliente nao encontrado com CPF: " + cpf);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
            return null;
        }
    }


    public void atualizar(Cliente cliente) {
        String sql = "UPDATE \"Cliente\" SET " +
        "\"Login\" = ?, " +
        "\"Senha\" = ?, " +
        "\"CPF\" = ?, " +
        "\"DataNasc\" = ?, " +
        "\"Fone\" = ?, " +
        "\"Email\" = ?, " +
        "\"FoneContato\" = ?, " +
        "\"Role\" = ? " +
        "WHERE \"CPF\" = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getLogin());
            stmt.setString(2, cliente.getSenha());
            stmt.setString(3, cliente.getCpf());
            stmt.setDate(4, Date.valueOf(cliente.getDataNasc()));
            stmt.setString(5, cliente.getFone());
            stmt.setString(6, cliente.getEmail());
            stmt.setString(7, cliente.getFoneContato());
            stmt.setString(8, cliente.getCargo());
            stmt.setString(9, cliente.getCpf());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Cliente nao encontrado para atualizacao.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void deletar(Cliente cliente) {
        String sql = "DELETE FROM \"Cliente\" WHERE \"CPF\" = ?";
    
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, cliente.getCpf());
    
            int linhasAfetadas = stmt.executeUpdate();
    
            if (linhasAfetadas > 0) {
                System.out.println("Cliente deletado com sucesso!");
            } else {
                System.out.println("Cliente nao encontrado para excluir.");
            }
    
        } catch (Exception e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }
}
