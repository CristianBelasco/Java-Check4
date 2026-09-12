package com.fiap.view;

import com.fiap.dao.FilmeDAO;
import com.fiap.factory.DAOFactory;
import com.fiap.model.Filme;
import com.fiap.singleton.ConexaoSingleton;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== CRUD de Filmes (DAO + Factory + Singleton) ===");

        Connection conexaoSingleton = ConexaoSingleton.getInstancia().getConexao();
        Connection mesmaConexao = ConexaoSingleton.getInstancia().getConexao();
        System.out.println("Singleton ativo: mesma conexao compartilhada? " + (conexaoSingleton == mesmaConexao));

        FilmeDAO dao = DAOFactory.criarFilmeDAO();

        System.out.println("\n-- CREATE --");
        Filme matriz = dao.salvar(new Filme("Matrix", "Ação", 136, "Lana Wachowski"));
        Filme interstellar = dao.salvar(new Filme("Interestelar", "Sci-Fi", 169, "Christopher Nolan"));
        System.out.println("Salvo: " + matriz);
        System.out.println("Salvo: " + interstellar);

        System.out.println("\n-- READ (buscar por id) --");
        Filme encontrado = dao.buscarPorId(matriz.getId());
        System.out.println("Encontrado: " + encontrado);

        System.out.println("\n-- READ (listar todos) --");
        List<Filme> filmes = dao.listarTodos();
        for (Filme filme : filmes) {
            System.out.println(filme);
        }

        System.out.println("\n-- UPDATE --");
        encontrado.setDiretor("Lana e Lilly Wachowski");
        encontrado.setDuracao(144);
        Filme atualizado = dao.atualizar(encontrado);
        System.out.println("Atualizado: " + atualizado);

        System.out.println("\n-- DELETE --");
        dao.excluir(interstellar.getId());
        System.out.println("Filme id=" + interstellar.getId() + " excluido. Ainda existe? "
                + (dao.buscarPorId(interstellar.getId()) != null));

        System.out.println("\n-- Lista final --");
        for (Filme filme : dao.listarTodos()) {
            System.out.println(filme);
        }
    }
}
