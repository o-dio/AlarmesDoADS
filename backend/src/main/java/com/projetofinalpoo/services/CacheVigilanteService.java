package com.projetofinalpoo.services;

import com.projetofinalpoo.dao.VigilanteDAO;
import com.projetofinalpoo.models.Vigilante;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço de cache em memória para objetos {@link Vigilante}.
 */
@Service
public class CacheVigilanteService {

    /** Armazena os vigilantes em cache, indexados pelo login. */
    private HashMap<String, Vigilante> cacheVigilantes = new HashMap<>();

    /** DAO responsável pelas interações com o banco de dados. */
    private final VigilanteDAO vigilanteDAO;

    /**
     * Construtor do serviço com injeção de dependência do DAO.
     *
     * @param vigilanteDAO DAO responsável pelo acesso ao banco de dados.
     */
    @Autowired
    public CacheVigilanteService(VigilanteDAO vigilanteDAO) {
        this.vigilanteDAO = vigilanteDAO;
    }

    /**
     * Carrega todos os vigilantes do banco para o cache.
     * Limpa o cache anterior antes de carregar os novos dados.
     */
    public void carregarDoBanco() {
        ArrayList<Vigilante> lista = vigilanteDAO.buscarTodos();
        if (lista != null) {
            limparMemoria();
            for (Vigilante v : lista) {
                cacheVigilantes.put(v.getLogin(), v);
            }
        }
        System.out.println("Vigilantes carregados do banco para a memoria.");
    }

    /**
     * Cadastra um novo vigilante no cache.
     *
     * @param vigilante O {@link Vigilante} a ser adicionado ao cache.
     */
    public void cadastrar(Vigilante vigilante) {
        cacheVigilantes.put(vigilante.getLogin(), vigilante);
        System.out.println("Vigilante cadastrado na memoria.");
    }

    /**
     * Retorna todos os vigilantes presentes no cache.
     *
     * @return Lista de vigilantes em memória.
     */
    public ArrayList<Vigilante> buscarTodos() {
        return new ArrayList<>(cacheVigilantes.values());
    }

    /**
     * Busca um vigilante no cache com base no login contido no objeto.
     *
     * @param vigilante O {@link Vigilante} de referência.
     * @return O vigilante correspondente ou {@code null} se não encontrado.
     */
    public Vigilante buscar(Vigilante vigilante) {
        return cacheVigilantes.get(vigilante.getLogin());
    }

    /**
     * Busca um vigilante no cache pelo login.
     *
     * @param login Login do vigilante.
     * @return O {@link Vigilante} correspondente ou {@code null} se não encontrado.
     */
    public Vigilante buscarPeloLogin(String login) {
        return cacheVigilantes.get(login);
    }

    /**
     * Busca um vigilante no cache com base no login e senha.
     *
     * @param login Login do vigilante.
     * @param senha Senha do vigilante.
     * @return O {@link Vigilante} correspondente ou {@code null} se não encontrado.
     */
    public Vigilante buscarPeloLoginSenha(String login, String senha) {
        for (Vigilante v : cacheVigilantes.values()) {
            if (v.getLogin().equals(login) && v.getSenha().equals(senha)) {
                return v;
            }
        }
        System.out.println("Vigilante nao encontrado.");
        return null;
    }

    /**
     * Atualiza os dados de um vigilante já presente no cache.
     *
     * @param antigo O {@link Vigilante} original.
     * @param novo   O {@link Vigilante} com os dados atualizados.
     */
    public void atualizar(Vigilante antigo, Vigilante novo) {
        if (cacheVigilantes.containsKey(antigo.getLogin())) {
            cacheVigilantes.remove(antigo.getLogin());
            cacheVigilantes.put(novo.getLogin(), novo);
            System.out.println("Vigilante atualizado na memoria.");
        } else {
            System.out.println("Vigilante nao encontrado para atualizacao.");
        }
    }

    /**
     * Remove um vigilante do cache com base no login.
     *
     * @param login Login do vigilante a ser removido.
     */
    public void deletar(String login) {
        if (cacheVigilantes.remove(login) != null) {
            System.out.println("Vigilante removido da memoria.");
        } else {
            System.out.println("Vigilante nao encontrado para exclusao.");
        }
    }

    /**
     * Sincroniza o cache de vigilantes com o banco de dados.
     * Vigilantes novos são cadastrados e os existentes são atualizados.
     */
    public void sincronizarComBanco() {
        for (Vigilante v : cacheVigilantes.values()) {
            Vigilante existente = vigilanteDAO.buscarPeloLogin(v.getLogin());

            if (existente == null) {
                vigilanteDAO.cadastrar(v);
            } else {
                vigilanteDAO.atualizar(existente, v);
            }
        }
        System.out.println("Sincronizacao com o banco concluida.");
    }

    /**
     * Limpa completamente o cache de vigilantes.
     */
    public void limparMemoria() {
        cacheVigilantes.clear();
    }
}
