package com.fiap.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoSingleton {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USUARIO = "rm565710";
    private static final String SENHA = "090906";

    private static ConexaoSingleton instancia;
    private final Connection conexao;

    private ConexaoSingleton() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            garantirEstrutura();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Nao foi possivel obter a conexao com o banco", e);
        }
    }

    public static synchronized ConexaoSingleton getInstancia() {
        if (instancia == null) {
            instancia = new ConexaoSingleton();
        }
        return instancia;
    }

    public Connection getConexao() {
        return conexao;
    }

    private void garantirEstrutura() throws SQLException {
        try (Statement st = conexao.createStatement()) {
            if (!existe(st, "USER_TABLES", "TABLE_NAME", "FILME")) {
                st.executeUpdate(
                        "CREATE TABLE FILME (" +
                                "ID NUMBER PRIMARY KEY, " +
                                "TITULO VARCHAR2(150) NOT NULL, " +
                                "GENERO VARCHAR2(50) NOT NULL, " +
                                "DURACAO NUMBER(3) NOT NULL, " +
                                "DIRETOR VARCHAR2(100) NOT NULL)");
            }
            if (!existe(st, "USER_SEQUENCES", "SEQUENCE_NAME", "SEQ_FILME")) {
                st.executeUpdate("CREATE SEQUENCE SEQ_FILME START WITH 1 INCREMENT BY 1");
            }
        }
    }

    private boolean existe(Statement st, String visao, String coluna, String nome) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + visao + " WHERE " + coluna + " = '" + nome + "'";
        try (ResultSet rs = st.executeQuery(sql)) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
