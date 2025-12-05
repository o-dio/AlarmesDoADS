package com.projetofinalpoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.Contrato;
import org.springframework.stereotype.Repository;

@Repository
public class ContratoDao {

    private Connection conn = new ConexaoDAO().conectar(); 
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

    private static final String SELECT_ALL_SQL = 
        "SELECT \"id\", \"Valor\", \"DataInicio\", \"DataFim\", \"Contrato\", \"IdCliente\", \"Status\", \"FormaPagamento\", \"Parcelas\" FROM \"Contrato\" ";
        
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
}