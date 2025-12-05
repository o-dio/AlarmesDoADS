package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.Contrato;
import org.springframework.stereotype.Repository;

/**
 * Classe DAO responsável pelo acesso aos dados da tabela "Contrato" no banco de dados.
 * Fornece métodos para buscar contratos por ID, por cliente e todos os contratos.
 */
@Repository
public class ContratoDAO {

    /** Conexão com o banco de dados. */
    private Connection conn = new ConexaoDAO().conectar(); 

    /**
     * Mapeia um {@link ResultSet} para um objeto {@link Contrato}.
     *
     * @param rs ResultSet contendo os dados do contrato.
     * @return Objeto Contrato preenchido com os dados do ResultSet.
     * @throws Exception Caso ocorra algum erro ao acessar os dados do ResultSet.
     */
    private Contrato mapResultSetToContrato(ResultSet rs) throws Exception {
        Contrato contrato = new Contrato();
        contrato.setId(rs.getInt("id"));
        contrato.setValor(rs.getDouble("Valor"));
        
        if (rs.getDate("DataInicio") != null) {
             contrato.setDataInicio(rs.getDate("DataInicio").toLocalDate());
        }
        if (rs.getDate("DataFim") != null) {
            contrato.setDataFim(rs.getDate("DataFim").toLocalDate());
        }
        
        contrato.setContrato(rs.getString("Contrato"));
        contrato.setStatus(rs.getString("Status"));
        contrato.setFormaPagamento(rs.getString("FormaPagamento"));
        contrato.setParcelas(rs.getInt("Parcelas"));

        Cliente c = new Cliente();
        c.setId(rs.getInt("IdCliente")); 
        contrato.setCliente(c);
        
        return contrato;
    }

    /** Consulta SQL base para seleção de todos os contratos. */
    private static final String SELECT_ALL_SQL = 
        "SELECT \"id\", \"Valor\", \"DataInicio\", \"DataFim\", \"Contrato\", \"IdCliente\", \"Status\", \"FormaPagamento\", \"Parcelas\" FROM \"Contrato\" ";

    /**
     * Busca um contrato pelo seu ID.
     *
     * @param id ID do contrato a ser buscado.
     * @return Objeto {@link Contrato} correspondente ao ID, ou null se não encontrado.
     */
    public Contrato buscarPeloID(int id) {
        String sql = SELECT_ALL_SQL + " WHERE \"id\" = ?";
        Contrato contrato = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                contrato = mapResultSetToContrato(rs);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar contrato por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return contrato;
    }

    /**
     * Busca todos os contratos relacionados a um determinado cliente.
     *
     * @param idCliente ID do cliente.
     * @return Lista de contratos do cliente ou uma lista vazia se nenhum contrato for encontrado.
     */
    public ArrayList<Contrato> buscarPorIdCliente(int idCliente) {
        ArrayList<Contrato> contratos = new ArrayList<>();
        String sql = SELECT_ALL_SQL + " WHERE \"IdCliente\" = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                contratos.add(mapResultSetToContrato(rs));
            }
        } catch (Exception e) {
             System.err.println("Erro ao buscar contratos por IdCliente: " + e.getMessage());
             e.printStackTrace();
        }

        return contratos;
    }
    
    /**
     * Busca todos os contratos cadastrados no sistema.
     *
     * @return Uma lista de {@link Contrato} ou uma lista vazia em caso de erro.
     */
    public ArrayList<Contrato> buscarTodos() {
        ArrayList<Contrato> contratos = new ArrayList<>();
        String sql = SELECT_ALL_SQL;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                contratos.add(mapResultSetToContrato(rs));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os contratos: " + e.getMessage());
            e.printStackTrace();
        }

        return contratos;
    }
}
