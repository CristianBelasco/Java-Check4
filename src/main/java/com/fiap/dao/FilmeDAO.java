package com.fiap.dao;

import com.fiap.model.Filme;

import java.util.List;

public interface FilmeDAO {
    Filme salvar(Filme filme);

    Filme buscarPorId(Long id);

    List<Filme> listarTodos();

    Filme atualizar(Filme filme);

    void excluir(Long id);
}
