package com.fiap.singleton;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoSingletonTest {
    public static void main(String[] args) {
        try {
            Connection conexao = ConexaoSingleton.getInstancia().getConexao();

            if (conexao == null) {
                throw new AssertionError("Conexão retornou nula");
            }

            boolean valida = conexao.isValid(5);
            if (!valida) {
                throw new AssertionError("Conexão não está válida");
            }

            System.out.println("Teste de conexão OK");
            System.out.println("URL ativa: " + conexao.getMetaData().getURL());
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao validar a conexão com o banco", e);
        }
    }
}
