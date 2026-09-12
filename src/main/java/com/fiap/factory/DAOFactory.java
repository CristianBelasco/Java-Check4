package com.fiap.factory;

import com.fiap.dao.FilmeDAO;
import com.fiap.dao.FilmeDAOImpl;
import com.fiap.singleton.ConexaoSingleton;

import java.sql.Connection;

public class DAOFactory {
    private DAOFactory() {
    }

    public static FilmeDAO criarFilmeDAO() {
        Connection conexao = ConexaoSingleton.getInstancia().getConexao();
        return new FilmeDAOImpl(conexao);
    }
}
