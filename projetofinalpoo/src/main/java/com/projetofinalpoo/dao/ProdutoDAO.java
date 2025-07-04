package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.projetofinalpoo.controllers.OcorrenciaController.ProdutoMonitoradoViewModel;
import com.projetofinalpoo.models.Produto;

/**
 * Classe responsável por realizar operações de acesso a dados (DAO) da entidade Produto.
 */
public class ProdutoDAO {
    private Connection conn = new ConexaoDAO().conectar();

    /**
     * Cadastra um novo produto no banco de dados.
     *
     * @param produto Objeto Produto a ser cadastrado.
     */
    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO \"Produto\" (\"dataInst\", \"dataRet\", \"defeito\", \"idEndereco\") VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setDate(1, Date.valueOf(produto.getDataInst()));
            stmt.setDate(2, Date.valueOf(produto.getDataRet()));
            stmt.setBoolean(3, produto.isDefeito());
            stmt.setInt(4, produto.getIdEndereco());

            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    /**
     * Retorna uma lista com todos os produtos cadastrados.
     *
     * @return Lista de objetos Produto ou null em caso de erro.
     */
    public ArrayList<Produto> buscarTodos() {
        String sql = "SELECT * FROM \"Produto\"";
        ArrayList<Produto> produtos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getInt("id"),
                    rs.getDate("dataInst").toLocalDate().format(formatter),
                    rs.getDate("dataRet").toLocalDate().format(formatter),
                    rs.getBoolean("defeito"),
                    rs.getInt("idEndereco")
                );
                produtos.add(p);
            }

            return produtos;
        } catch (Exception e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca um produto pelo ID.
     *
     * @param id Identificador do produto.
     * @return Objeto Produto correspondente ou null se não encontrado.
     */
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM \"Produto\" WHERE \"id\" = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produto(
                    rs.getInt("id"),
                    rs.getDate("dataInst").toLocalDate().format(formatter),
                    rs.getDate("dataRet").toLocalDate().format(formatter),
                    rs.getBoolean("defeito"),
                    rs.getInt("idEndereco")
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar produto por id: " + e.getMessage());
            return null;
        }
    }

    /**
     * Atualiza os dados de um produto existente.
     *
     * @param id ID do produto a ser atualizado.
     * @param produto Objeto Produto com os novos dados.
     */
    public void atualizar(int id, Produto produto) {
        String sql = "UPDATE \"Produto\" SET \"dataInst\" = ?, \"dataRet\" = ?, \"defeito\" = ?, \"idEndereco\" = ? WHERE \"id\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setDate(1, Date.valueOf(produto.getDataInst()));
            stmt.setDate(2, Date.valueOf(produto.getDataRet()));
            stmt.setBoolean(3, produto.isDefeito());
            stmt.setInt(4, produto.getIdEndereco());
            stmt.setInt(5, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Produto nao encontrado para atualizacao.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    /**
     * Remove um produto do banco de dados.
     *
     * @param id ID do produto a ser removido.
     */
    public void deletar(int id) {
        String sql = "DELETE FROM \"Produto\" WHERE \"id\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Produto deletado com sucesso!");
            } else {
                System.out.println("Produto nao encontrado para exclusao.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
    }

    /**
     * Busca todos os produtos monitorados, incluindo informações do cliente e endereço.
     *
     * @return Lista de ProdutoMonitoradoViewModel com dados de produto, cliente e endereço.
     */
    public List<ProdutoMonitoradoViewModel> buscarProdutosComClienteEndereco() {
        List<ProdutoMonitoradoViewModel> lista = new ArrayList<>();

        String sql = "SELECT p.id AS produto_id, " +
                     "c.\"Login\" AS cliente_nome, c.\"Fone\" AS cliente_telefone, " +
                     "e.\"Rua\", e.\"Numero\", e.\"Bairro\", e.\"Cidade\", e.\"Estado\" " +
                     "FROM \"Produto\" p " +
                     "JOIN \"Endereco\" e ON p.\"IdEndereco\" = e.id " +
                     "JOIN \"Cliente\" c ON e.\"IdCliente\" = c.id";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idProduto = rs.getInt("produto_id");
                String nomeCliente = rs.getString("cliente_nome");
                String telefoneCliente = rs.getString("cliente_telefone");

                String enderecoCompleto = String.format("%s, %s - %s, %s - %s",
                        rs.getString("Rua"),
                        rs.getString("Numero"),
                        rs.getString("Bairro"),
                        rs.getString("Cidade"),
                        rs.getString("Estado"));

                ProdutoMonitoradoViewModel p = new ProdutoMonitoradoViewModel(
                        idProduto,
                        nomeCliente,
                        telefoneCliente,
                        enderecoCompleto
                );

                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar produtos monitorados: " + e.getMessage());
        }

        return lista;
    }
}
