package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.projetofinalpoo.models.Produto;
import com.projetofinalpoo.models.Endereco;
import com.projetofinalpoo.viewmodels.ProdutoMonitoradoViewModel;

/**
 * Classe responsável por realizar operações de acesso a dados (DAO) da entidade Produto.
 * Contém métodos para cadastrar, buscar, atualizar e deletar produtos, bem como consultas
 * específicas relacionadas a clientes, vigilantes e endereços.
 */
@Repository
public class ProdutoDAO {

    /** Conexão com o banco de dados. */
    private Connection conn = new ConexaoDAO().conectar();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Converte um LocalDate em java.sql.Date ou retorna null.
     */
    private Date toSql(LocalDate data) {
        return (data != null ? Date.valueOf(data) : null);
    }

    /**
     * Converte um java.sql.Date em String formatada dd/MM/yyyy
     */
    private String formatDate(Date d) {
        return (d != null ? d.toLocalDate().format(FORMATTER) : null);
    }

    /**
     * Cadastra um novo produto no banco de dados.
     *
     * @param produto Objeto Produto a ser cadastrado.
     */
    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO \"Produto\" (\"DataInst\", \"DataRet\", \"Defeito\", \"IdEndereco\") VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, toSql(produto.getDataInst()));
            stmt.setDate(2, toSql(produto.getDataRet()));
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
     * @return Lista de produtos ou vazia em caso de erro.
     */
    public ArrayList<Produto> buscarTodos() {
        String sql = "SELECT * FROM \"Produto\"";
        ArrayList<Produto> produtos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Date dtInst = rs.getDate("DataInst");
                Date dtRet = rs.getDate("DataRet");

                Produto p = new Produto(
                        rs.getInt("id"),
                        formatDate(dtInst),  // ← Agora seguro
                        formatDate(dtRet),   // ← Agora seguro
                        rs.getBoolean("Defeito"),
                        rs.getInt("IdEndereco")
                );

                produtos.add(p);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
        }

        return produtos;
    }

    /**
     * Busca um produto pelo ID.
     *
     * @param id Identificador do produto.
     * @return Produto ou null caso não encontrado.
     */
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM \"Produto\" WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Date dtInst = rs.getDate("DataInst");
                Date dtRet = rs.getDate("DataRet");

                return new Produto(
                        rs.getInt("id"),
                        formatDate(dtInst),  
                        formatDate(dtRet),  
                        rs.getBoolean("Defeito"),
                        rs.getInt("IdEndereco")
                );
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar produto por id: " + e.getMessage());
        }

        return null;
    }

    /**
     * Atualiza os dados de um produto existente.
     *
     * @param id ID do produto a ser atualizado.
     * @param produto Objeto contendo os novos dados.
     */
    public void atualizar(int id, Produto produto) {
        String sql = "UPDATE \"Produto\" SET \"DataInst\" = ?, \"DataRet\" = ?, \"Defeito\" = ?, \"IdEndereco\" = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, toSql(produto.getDataInst()));
            stmt.setDate(2, toSql(produto.getDataRet()));
            stmt.setBoolean(3, produto.isDefeito());
            stmt.setInt(4, produto.getIdEndereco());
            stmt.setInt(5, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0)
                System.out.println("Produto atualizado com sucesso!");
            else
                System.out.println("Produto não encontrado.");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    /**
     * Remove um produto do banco de dados.
     */
    public void deletar(int id) {
        String sql = "DELETE FROM \"Produto\" WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0)
                System.out.println("Produto deletado com sucesso!");
            else
                System.out.println("Produto não encontrado.");

        } catch (Exception e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
    }

    /**
     * Busca todos os produtos monitorados, com cliente e endereço.
     */
    public List<ProdutoMonitoradoViewModel> buscarProdutosComClienteEndereco() {

        List<ProdutoMonitoradoViewModel> lista = new ArrayList<>();

        String sql = """
            SELECT p.id AS produto_id, 
                   c."Login" AS cliente_nome, c."Fone" AS cliente_telefone,
                   e."Rua", e."Numero", e."Bairro", e."Cidade", e."Estado"
            FROM "Produto" p
            JOIN "Endereco" e ON p."IdEndereco" = e.id
            JOIN "Contrato_Endereco" ce ON ce."IdEndereco" = e.id
            JOIN "Contrato" ct ON ct.id = ce."IdContrato"
            JOIN "Cliente" c ON c.id = ct."IdCliente"
        """;

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

                lista.add(new ProdutoMonitoradoViewModel(
                        rs.getInt("produto_id"),
                        rs.getString("cliente_nome"),
                        rs.getString("cliente_telefone"),
                        endereco
                ));
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar produtos monitorados: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Busca produtos por vigilante.
     */
    public List<Produto> buscarPorVigilante(int idVigilante) {

        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"Produto\" WHERE \"IdVigilante\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVigilante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Produto p = new Produto(
                        rs.getInt("id"),
                        rs.getString("DataInst"),
                        rs.getString("DataRet"),
                        rs.getBoolean("Defeito"),
                        rs.getInt("IdEndereco")
                );

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar produtos por vigilante: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Busca endereços monitorados por um vigilante.
     */
    public List<Endereco> buscarEnderecosDoVigilante(int idVigilante) {

        List<Endereco> lista = new ArrayList<>();

        String sql = """
                SELECT DISTINCT e.id, e."Rua", e."Numero", e."Bairro", e."Cidade", e."Estado"
                FROM "Produto" p
                JOIN "Endereco" e ON p."IdEndereco" = e.id
                JOIN "Trajeto" t ON t."IdVigilante" = ?
                JOIN "Endereco_Rota" er ON er."IdEndereco" = e.id AND er."IdRota" = t."IdRota"
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVigilante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Endereco e = new Endereco();
                e.setId(rs.getInt("id"));
                e.setRua(rs.getString("Rua"));
                e.setNumero(rs.getString("Numero"));
                e.setBairro(rs.getString("Bairro"));
                e.setCidade(rs.getString("Cidade"));
                e.setEstado(rs.getString("Estado"));
                lista.add(e);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar endereços monitorados: " + e.getMessage());
        }

        return lista;
    }
}
