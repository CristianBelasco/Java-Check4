package com.fiap.dao;

import com.fiap.factory.DAOFactory;
import com.fiap.model.Filme;

import java.util.List;

public class FilmeDAOImplTest {

    public static void main(String[] args) {
        deveSalvarEConsultarFilme();
        deveListarTodosFilmes();
        deveAtualizarFilme();
        deveExcluirFilme();
        System.out.println("Todos os testes passaram.");
    }

    private static void deveSalvarEConsultarFilme() {
        FilmeDAO dao = DAOFactory.criarFilmeDAO();

        Filme filme = new Filme("O Senhor dos Anéis", "Fantasia", 178, "Peter Jackson");
        Filme salvo = dao.salvar(filme);

        assertNotNull(salvo.getId(), "ID do filme nao pode ser nulo");
        assertEquals("O Senhor dos Anéis", salvo.getTitulo(), "Titulo do filme invalido");

        Filme encontrado = dao.buscarPorId(salvo.getId());
        assertNotNull(encontrado, "Filme nao encontrado");
        assertEquals("O Senhor dos Anéis", encontrado.getTitulo(), "Filme encontrado com titulo incorreto");
        assertEquals("Fantasia", encontrado.getGenero(), "Genero incorreto");
        assertEquals(178, encontrado.getDuracao(), "Duracao incorreta");
        assertEquals("Peter Jackson", encontrado.getDiretor(), "Diretor incorreto");

        dao.excluir(salvo.getId());
    }

    private static void deveListarTodosFilmes() {
        FilmeDAO dao = DAOFactory.criarFilmeDAO();
        Filme primeiro = dao.salvar(new Filme("Matrix", "Ação", 136, "Lana Wachowski"));
        Filme segundo = dao.salvar(new Filme("Interestelar", "Sci-Fi", 169, "Christopher Nolan"));

        List<Filme> filmes = dao.listarTodos();

        assertTrue(filmes.size() >= 2, "Lista de filmes deve ter pelo menos 2 itens");

        dao.excluir(primeiro.getId());
        dao.excluir(segundo.getId());
    }

    private static void deveAtualizarFilme() {
        FilmeDAO dao = DAOFactory.criarFilmeDAO();
        Filme filme = dao.salvar(new Filme("Duna", "Ficção", 155, "Denis Villeneuve"));

        filme.setTitulo("Duna: Parte 2");
        filme.setGenero("Sci-Fi");
        filme.setDuracao(166);
        filme.setDiretor("Denis Villeneuve");

        Filme atualizado = dao.atualizar(filme);

        assertEquals("Duna: Parte 2", atualizado.getTitulo(), "Titulo nao foi atualizado");
        assertEquals("Sci-Fi", atualizado.getGenero(), "Genero nao foi atualizado");
        assertEquals(166, atualizado.getDuracao(), "Duracao nao foi atualizada");
        assertEquals("Denis Villeneuve", atualizado.getDiretor(), "Diretor nao foi atualizado");

        Filme persistido = dao.buscarPorId(filme.getId());
        assertEquals("Duna: Parte 2", persistido.getTitulo(), "Titulo persistido incorreto");
        assertEquals(166, persistido.getDuracao(), "Duracao persistida incorreta");

        dao.excluir(filme.getId());
    }

    private static void deveExcluirFilme() {
        FilmeDAO dao = DAOFactory.criarFilmeDAO();
        Filme filme = dao.salvar(new Filme("Whiplash", "Drama", 106, "Damien Chazelle"));

        dao.excluir(filme.getId());

        assertNull(dao.buscarPorId(filme.getId()), "Filme ainda existe apos exclusao");
    }

    private static void assertNotNull(Object value, String message) {
        if (value == null) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " | esperado=" + expected + ", atual=" + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertNull(Object value, String message) {
        if (value != null) {
            throw new AssertionError(message + " | valor atual=" + value);
        }
    }
}
