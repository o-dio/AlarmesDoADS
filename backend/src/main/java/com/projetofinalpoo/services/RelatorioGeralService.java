package com.projetofinalpoo.services;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.AreaBreak;

import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.Contrato;
import com.projetofinalpoo.models.Endereco;
import com.projetofinalpoo.models.Vigilante;
import com.projetofinalpoo.models.Rota;
import com.projetofinalpoo.models.Trajeto;
import com.projetofinalpoo.models.Produto;
import com.projetofinalpoo.models.Ocorrencia;
import com.projetofinalpoo.models.Gravacao;
import com.projetofinalpoo.models.Admin;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.dao.ContratoDAO;
import com.projetofinalpoo.dao.EnderecoDAO;
import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.dao.RotaDAO;
import com.projetofinalpoo.dao.TrajetoDAO;
import com.projetofinalpoo.dao.ProdutoDAO;
import com.projetofinalpoo.dao.OcorrenciaDAO;
import com.projetofinalpoo.dao.GravacaoDAO;
import com.projetofinalpoo.dao.AdminDAO;

/**
 * Serviço responsável por gerar relatórios gerais do sistema em formato PDF,
 * incluindo informações de clientes, vigilantes, contratos, endereços, rotas,
 * trajetos, produtos, ocorrências, gravações e administradores.
 */
@Service
public class RelatorioGeralService {

    @Autowired 
    private ClienteDAO clienteDAO;
    @Autowired 
    private ContratoDAO contratoDAO;
    @Autowired 
    private EnderecoDAO enderecoDAO;
    @Autowired 
    private VigilanteDAO vigilanteDAO;
    @Autowired 
    private RotaDAO rotaDAO;
    @Autowired 
    private TrajetoDAO trajetoDAO;
    @Autowired 
    private ProdutoDAO produtoDAO;
    @Autowired 
    private OcorrenciaDAO ocorrenciaDAO;
    @Autowired 
    private GravacaoDAO gravacaoDAO;
    @Autowired 
    private AdminDAO adminDAO;

    /** Formato de data padrão "dd/MM/yyyy". */
    private static final DateTimeFormatter dtfData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    /** Formato de hora padrão "HH:mm". */
    private static final DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Gera o relatório geral do sistema em formato PDF.
     *
     * @return um array de bytes representando o PDF gerado, ou null em caso de erro.
     */
    public byte[] gerarRelatorio() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            Paragraph titulo = new Paragraph("RELATÓRIO GERAL DO SISTEMA")
                    .setBold()
                    .setFontSize(20)
                    .setMarginBottom(20);
            doc.add(titulo);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            doc.add(new Paragraph("Gerado em: " + sdf.format(new Date()))
                    .setFontSize(11)
                    .setMarginBottom(20));

            adicionarClientes(doc);
            adicionarVigilantes(doc);
            adicionarContratos(doc);
            adicionarEnderecos(doc);
            adicionarRotas(doc);
            adicionarTrajetos(doc);
            adicionarProdutos(doc);
            adicionarOcorrencias(doc);
            adicionarGravacoes(doc);
            adicionarAdmins(doc);

            doc.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Adiciona um título de seção ao documento PDF.
     *
     * @param doc   Documento PDF onde o título será adicionado.
     * @param titulo Texto do título da seção.
     */
    private void adicionarTituloSecao(Document doc, String titulo) {
        doc.add(new Paragraph(titulo)
                .setFontSize(16)
                .setBold()
                .setMarginTop(20)
                .setMarginBottom(10));
    }

    /**
     * Cria uma tabela com cabeçalhos fornecidos.
     *
     * @param colunas Nomes das colunas da tabela.
     * @return Tabela pronta para inserção de dados.
     */
    private Table criarTabela(String... colunas) {
        float[] widths = new float[colunas.length];
        Arrays.fill(widths, 1);
        Table table = new Table(widths);
        for (String col : colunas) {
            table.addHeaderCell(new Cell().add(new Paragraph(col).setBold()));
        }
        return table;
    }

    /**
     * Adiciona a seção de clientes ao PDF.
     *
     * @param doc Documento PDF.
     */
    private void adicionarClientes(Document doc) {
        adicionarTituloSecao(doc, "Clientes");
        ArrayList<Cliente> lista = clienteDAO.buscarTodos();
        if (lista == null || lista.isEmpty()) {
            doc.add(new Paragraph("Nenhum cliente cadastrado."));
            return;
        }
        Table tabela = criarTabela("Login", "CPF", "Nascimento", "Fone", "Email");
        for (Cliente c : lista) {
            tabela.addCell(c.getLogin());
            tabela.addCell(c.getCpf());
            tabela.addCell(c.getDataNasc() != null ? c.getDataNasc().format(dtfData) : "N/A");
            tabela.addCell(c.getContatoInfo().getFone());
            tabela.addCell(c.getContatoInfo().getEmail());
        }
        doc.add(tabela);
    }

    /**
     * Adiciona a seção de vigilantes ao PDF.
     *
     * @param doc Documento PDF.
     */
    private void adicionarVigilantes(Document doc) {
        adicionarTituloSecao(doc, "Vigilantes");
        ArrayList<Vigilante> lista = vigilanteDAO.buscarTodos();
        if (lista == null || lista.isEmpty()) {
            doc.add(new Paragraph("Nenhum vigilante cadastrado."));
            return;
        }

        Table tabela = criarTabela("Login", "Turno", "Carga Horária", "Remuneração", "Data Contratação");
        
        DateTimeFormatter dtfData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        for (Vigilante v : lista) {
            tabela.addCell(v.getLogin());
            tabela.addCell(v.getTurno());
            tabela.addCell(v.getCargaHoraria() != null ? v.getCargaHoraria().format(dtfHora) : "N/A");
            tabela.addCell(String.format("R$ %.2f", v.getRemuneracao()));
            tabela.addCell(v.getDataContratacao() != null ? v.getDataContratacao().format(dtfData) : "N/A");
        }

        doc.add(tabela);
    }

    /**
     * Adiciona a seção de contratos ao PDF.
     *
     * @param doc Documento PDF.
     */
    private void adicionarContratos(Document doc) {
        adicionarTituloSecao(doc, "Contratos");
        ArrayList<Contrato> lista = contratoDAO.buscarTodos();
        if (lista == null || lista.isEmpty()) {
            doc.add(new Paragraph("Nenhum contrato cadastrado."));
            return;
        }

        Table tabela = criarTabela("ID", "Cliente CPF", "Início", "Fim", "Pagamento", "Valor", "Parcelas", "Status", "Contrato");

        for (Contrato c : lista) {
            tabela.addCell(String.valueOf(c.getId()));
            tabela.addCell(c.getCliente() != null && c.getCliente().getCpf() != null ? c.getCliente().getCpf() : "N/A");
            tabela.addCell(c.getDataInicio() != null ? c.getDataInicio().format(dtfData) : "N/A");
            tabela.addCell(c.getDataFim() != null ? c.getDataFim().format(dtfData) : "N/A");
            tabela.addCell(c.getFormaPagamento() != null ? c.getFormaPagamento() : "N/A");
            tabela.addCell(String.format("R$ %.2f", c.getValor()));
            tabela.addCell(String.valueOf(c.getParcelas()));
            tabela.addCell(c.getStatus() != null ? c.getStatus() : "N/A");
            tabela.addCell(c.getContrato() != null ? c.getContrato() : "N/A");
        }

        doc.add(tabela);
    }

    /**
     * Adiciona a seção de endereços ao PDF.
     *
     * @param doc Documento PDF.
     */
    private void adicionarEnderecos(Document doc) {
        adicionarTituloSecao(doc, "Endereços");
        List<Endereco> lista = enderecoDAO.buscarTodos();
        if (lista.isEmpty()) {
            doc.add(new Paragraph("Nenhum endereço cadastrado."));
            return;
        }
        Table tabela = criarTabela("ID", "Rua", "Número", "Bairro", "Cidade", "Estado", "Rotas");
        for (Endereco e : lista) {
            tabela.addCell(String.valueOf(e.getId()));
            tabela.addCell(e.getRua());
            tabela.addCell(e.getNumero());
            tabela.addCell(e.getBairro());
            tabela.addCell(e.getCidade());
            tabela.addCell(e.getEstado());
            tabela.addCell(e.getRotas().toString());
        }
        doc.add(tabela);
    }

    /**
     * Adiciona a seção de rotas ao PDF.
     *
     * @param doc Documento PDF.
     */
    private void adicionarRotas(Document doc) {
        adicionarTituloSecao(doc, "Rotas");
        ArrayList<Rota> lista = rotaDAO.buscarTodos();
        if (lista.isEmpty()) {
            doc.add(new Paragraph("Nenhuma rota cadastrada."));
            return;
        }
        Table tabela = criarTabela("ID", "Nome", "Bairro", "Descrição", "Observação", "Endereços");
        for (Rota r : lista) {
            tabela.addCell(String.valueOf(r.getId()));
            tabela.addCell(r.getNome());
            tabela.addCell(r.getBairro());
            tabela.addCell(r.getDescricao());
            tabela.addCell(r.getObservacao());
            tabela.addCell(r.getEnderecos().toString());
        }
        doc.add(tabela);
    }

    /**
     * Adiciona a seção de trajetos ao PDF.
     *
     * @param doc Documento PDF.
     */
    private void adicionarTrajetos(Document doc) {
        adicionarTituloSecao(doc, "Trajetos");

        ArrayList<Trajeto> lista = trajetoDAO.buscarTodos();
        if (lista == null || lista.isEmpty()) {
            doc.add(new Paragraph("Nenhum trajeto registrado."));
            return;
        }

        Table tabela = criarTabela("Data Início", "Data Fim", "ID Vigilante", "ID Rota");

        DateTimeFormatter dtfData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Trajeto t : lista) {
            tabela.addCell(t.getDataIni() != null ? t.getDataIni().format(dtfData) : "N/A");
            tabela.addCell(t.getDataFim() != null ? t.getDataFim().format(dtfData) : "N/A");
            tabela.addCell(String.valueOf(t.getIdVigilante()));
            tabela.addCell(String.valueOf(t.getIdRota()));
        }

        doc.add(tabela);
    }

    /**
     * Adiciona a seção de produtos ao PDF.
     *
     * @param doc Documento PDF.
     */
    private void adicionarProdutos(Document doc) {
        adicionarTituloSecao(doc, "Produtos");
        ArrayList<Produto> lista = produtoDAO.buscarTodos();
        if (lista.isEmpty()) {
            doc.add(new Paragraph("Nenhum produto cadastrado."));
            return;
        }
        Table tabela = criarTabela("ID", "Data Instalação", "Data Retirada", "Defeito", "Endereço");
        for (Produto p : lista) {
            tabela.addCell(String.valueOf(p.getId()));
            tabela.addCell(p.getDataInst() != null ? p.getDataInst().format(dtfData) : "N/A");
            tabela.addCell(p.getDataRet() != null ? p.getDataRet().format(dtfData) : "N/A");
            tabela.addCell(p.isDefeito() ? "Sim" : "Não");
            tabela.addCell(String.valueOf(p.getIdEndereco()));
        }
        doc.add(tabela);
    }

    /**
     * Adiciona a seção de ocorrências ao PDF.
     *
     * @param doc Documento PDF.
     */
    private void adicionarOcorrencias(Document doc) {
        adicionarTituloSecao(doc, "Ocorrências");

        ArrayList<Ocorrencia> lista = ocorrenciaDAO.buscarTodos();
        DateTimeFormatter dtfData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        if (lista.isEmpty()) {
            doc.add(new Paragraph("Nenhuma ocorrência registrada."));
            return;
        }

        Table tabela = criarTabela("ID", "Data", "Duração", "ID Vigilante", "ID Produto");

        for (Ocorrencia o : lista) {
            tabela.addCell(String.valueOf(o.getId()));
            tabela.addCell(o.getData() != null ? o.getData().format(dtfData) : "N/A");
            tabela.addCell(o.getDuracao() != null ? o.getDuracao().format(dtfHora) : "N/A");
            tabela.addCell(String.valueOf(o.getIdVigilante()));
            tabela.addCell(String.valueOf(o.getIdProduto()));
        }

        doc.add(tabela);
    }

    /**
     * Adiciona a seção de gravações ao PDF.
     *
     * @param doc Documento PDF.
     */
    private void adicionarGravacoes(Document doc) {
        adicionarTituloSecao(doc, "Gravações");

        List<Gravacao> lista = gravacaoDAO.buscarTodos();
        DateTimeFormatter dtfData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        if (lista.isEmpty()) {
            doc.add(new Paragraph("Nenhuma gravação registrada."));
            return;
        }

        Table tabela = criarTabela("ID", "Data", "Duração", "Arquivo", "Descrição", "Produto");

        for (Gravacao g : lista) {
            tabela.addCell(String.valueOf(g.getId()));
            tabela.addCell(g.getData() != null ? g.getData().format(dtfData) : "N/A");
            tabela.addCell(g.getDuracao() != null ? g.getDuracao().format(dtfHora) : "N/A");
            tabela.addCell(g.getArquivo());
            tabela.addCell(g.getDescricao());
            tabela.addCell(String.valueOf(g.getIdProduto()));
        }

        doc.add(tabela);
    }

    /**
     * Adiciona a seção de administradores ao PDF.
     *
     * @param doc Documento PDF.
     */
    private void adicionarAdmins(Document doc) {
        doc.add(new AreaBreak());

        adicionarTituloSecao(doc, "Administradores");

        ArrayList<Admin> lista = adminDAO.buscarTodos();
        if (lista == null || lista.isEmpty()) {
            doc.add(new Paragraph("Nenhum administrador cadastrado."));
            return;
        }

        Table tabela = criarTabela("Login");
        for (Admin a : lista) {
            tabela.addCell(a.getLogin());
        }

        doc.add(tabela);
    }

}
