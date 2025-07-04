package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.ContatoInfo;

/**
 * Classe responsável por realizar operações de acesso a dados (DAO) da entidade Cliente.
 */
public class ClienteDAO {
    private Connection conn = new ConexaoDAO().conectar();

    /**
     * Cadastra um novo cliente no banco de dados.
     *
     * @param cliente Objeto Cliente a ser cadastrado.
     */
    public void cadastrar(Cliente cliente) {
        String sql = "INSERT INTO \"Cliente\" " +
        "(\"Login\", \"Senha\", \"CPF\", \"DataNasc\", \"Fone\", \"Email\", \"FoneContato\")" +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, cliente.getLogin());
            stmt.setString(2, cliente.getSenha());
            stmt.setString(3, cliente.getCpf());
            stmt.setDate(4, Date.valueOf(cliente.getDataNasc()));
            stmt.setString(5, cliente.getContatoInfo().getFone());
            stmt.setString(6, cliente.getContatoInfo().getEmail());
            stmt.setString(7, cliente.getContatoInfo().getFoneContato());
            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    /**
     * Retorna uma lista com todos os clientes cadastrados.
     *
     * @return Lista de objetos Cliente ou null se nenhum cliente for encontrado.
     */
    public ArrayList<Cliente> buscarTodos() {
        String sql = "SELECT * FROM \"Cliente\"";
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                   Date dataNasc = rs.getDate("DataNasc");
                    String dataFormatada = dataNasc != null ? new SimpleDateFormat("dd/MM/yyyy").format(dataNasc) : null;
System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DataNasc")));
                clientes.add(new Cliente(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("CPF"),

                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DataNasc")),
                    new ContatoInfo(
                        rs.getString("Fone"),
                        rs.getString("Email"),
                        rs.getString("FoneContato")
                    )
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

    /**
     * Busca um cliente pelo login.
     *
     * @param cliente Objeto Cliente com o login preenchido.
     * @return Cliente correspondente ou null se não encontrado.
     */
    public Cliente buscar(Cliente cliente) {
        String sql = "SELECT * FROM \"Cliente\" WHERE \"CPF\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, cliente.getCpf());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente findClient = new Cliente(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("CPF"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DataNasc")),
                    new ContatoInfo(
                        rs.getString("Fone"),
                        rs.getString("Email"),
                        rs.getString("FoneContato")
                    )
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

    /**
     * Busca um cliente pelo login e senha.
     *
     * @param login Login do cliente.
     * @param senha Senha do cliente.
     * @return Cliente correspondente ou null se não encontrado.
     */
    public Cliente buscarPeloLoginSenha(String login, String senha) {
        String sql = "SELECT * FROM \"Cliente\" WHERE \"Login\" = ? AND \"Senha\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente findClient = new Cliente(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("CPF"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DataNasc")),
                    new ContatoInfo(
                        rs.getString("Fone"),
                        rs.getString("Email"),
                        rs.getString("FoneContato")
                    )
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

    /**
     * Busca um Cliente pelo CPF.
     *
     * @param cpf String CPF a ser consultada no banco.
     * @return Objeto Cliente completo ou null se não encontrado.
     */
    public Cliente buscarPeloCpf(String cpf) {
        String sql = "SELECT * FROM \"Cliente\" WHERE \"CPF\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente findClient = new Cliente(
                    rs.getString("Login"),
                    rs.getString("Senha"),
                    rs.getString("CPF"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DataNasc")),
                    new ContatoInfo(
                        rs.getString("Fone"),
                        rs.getString("Email"),
                        rs.getString("FoneContato")
                    )
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


    /**
     * Atualiza os dados de um cliente existente.
     *
     * @param cliente Objeto Cliente a ser alterado.
     */
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE \"Cliente\" SET " +
        "\"Login\" = ?, " +
        "\"Senha\" = ?, " +
        "\"CPF\" = ?, " +
        "\"DataNasc\" = ?, " +
        "\"Fone\" = ?, " +
        "\"Email\" = ?, " +
        "\"FoneContato\" = ? "+
        "WHERE \"CPF\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, cliente.getLogin());
            stmt.setString(2, cliente.getSenha());
            stmt.setString(3, cliente.getCpf());
            stmt.setDate(4, Date.valueOf(cliente.getDataNasc()));
            stmt.setString(5, cliente.getContatoInfo().getFone());
            stmt.setString(6, cliente.getContatoInfo().getEmail());
            stmt.setString(7, cliente.getContatoInfo().getFoneContato());
            stmt.setString(8, cliente.getCpf());

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

    /**
     * Remove um cliente do banco de dados.
     *
     * @param cliente Objeto Cliente a ser deletado.
     */
    public void deletar(Cliente cliente) {
        String sql = "DELETE FROM \"Cliente\" WHERE \"CPF\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {

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
