package com.projetofinalpoo.projetofinalpoo;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.projetofinalpoo.dao.ClienteDAO;
import com.projetofinalpoo.models.Cliente;
import com.projetofinalpoo.models.ContatoInfo;

@SpringBootTest
public class testeClienteDAO {
    private static final Logger logger = LoggerFactory.getLogger(ClienteDAO.class);
    private ClienteDAO clienteDao = new ClienteDAO();


    public void cadastrar() {
		logger.info("\n\n==== Inicio teste Cadastrar Cliente ====\n");
		Cliente cliente = new Cliente("fabio.silva","senhaSegura123","01123456789","20/05/1990",
									  new ContatoInfo("11988887777","joao.silva@email.com", "11999996666"));
		clienteDao.cadastrar(cliente);
		logger.info("\n\n==== Fim ====\n");
    }
	
	public void buscarTodos() {
		logger.info("\n\n==== Inicio teste buscar todos os clientes ====\n");
		ArrayList<Cliente> findClientes = clienteDao.buscarTodos();
		findClientes.forEach(c -> System.out.println(c.getLogin()));
		logger.info("\n\n==== Fim ====\n");
	}
	
	public void buscar() {
		logger.info("\n\n==== Inicio teste buscar cliente ====\n");
		Cliente cliente = new Cliente("fabio.silva","senhaSegura123","01123456789","20/05/1990",
									  new ContatoInfo("11988887777","joao.silva@email.com", "11999996666"));
		Cliente findCliente= clienteDao.buscar(cliente);
		System.out.println(findCliente.getLogin());
		logger.info("\n\n==== Fim ====\n");
	}

	public void buscarPeloLoginSenha() {
		logger.info("\n\n==== Inicio teste buscar cliente pelo login e senha ====\n");
		Cliente findCliente= clienteDao.buscarPeloLoginSenha("fabio.silva", "senhaSegura123");
		System.out.println(findCliente.getLogin());
		logger.info("\n\n==== Fim ====\n");
	}

	public void buscarPeloCpf() {
		logger.info("\n\n==== Inicio teste buscar cliente pelo cpf ====\n");
		Cliente findClienteCpf = clienteDao.buscarPeloCpf("01123456789");
		System.out.println(findClienteCpf.getLogin());
		logger.info("\n\n==== Fim ====\n");
	} 

	
	public void atualizar() {
		logger.info("\n\n==== Inicio teste atualizar cliente ====\n");
		Cliente cliente = new Cliente("fabio.silva","senhaSegura123","01123456789","20/05/1990",
									  new ContatoInfo("11988887777","joao.silva@email.com", "11999996666"));
		System.out.println("Antes da atualizacao: " + clienteDao.buscarPeloCpf("01123456789").getLogin());
		cliente.setLogin("paulao.silva");
		clienteDao.atualizar(cliente);
		System.out.println("Apos atualizacao: " + clienteDao.buscarPeloCpf(cliente.getCpf()).getLogin());
		logger.info("\n\n==== Fim ====\n");
	}
	
	@Test
	public void deletarCliente() {
		logger.info("\n\n==== Inicio teste deletar cliente ====\n");
		Cliente cliente = new Cliente("fabio.silva","senhaSegura123","01123456789","20/05/1990",
									  new ContatoInfo("11988887777","joao.silva@email.com", "11999996666"));
		try {
			System.out.println("Cliente encontrado antes de deletar: " + clienteDao.buscarPeloCpf(cliente.getCpf()).getLogin());
			clienteDao.deletar(cliente);
			clienteDao.buscarPeloCpf(cliente.getCpf());
		} catch (Exception e) {}
		logger.info("\n\n==== Fim ====\n");
	}
}
