package com.fiap.dao;

import com.fiap.model.Filme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAOImpl implements FilmeDAO {
    private final Connection conexao;

    public FilmeDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public Filme salvar(Filme filme) {
        String sqlId = "SELECT SEQ_FILME.NEXTVAL FROM DUAL";
        String sql = "INSERT INTO FILME (ID, TITULO, GENERO, DURACAO, DIRETOR) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement psId = conexao.prepareStatement(sqlId);
                ResultSet rs = psId.executeQuery()) {
            if (!rs.next()) {
                throw new SQLException("Nao foi possivel gerar o ID do filme");
            }
            filme.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar ID do filme", e);
        }

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            preencherDados(ps, filme, 2);
            ps.setLong(1, filme.getId());
            ps.executeUpdate();
            return filme;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar filme", e);
        }
    }

    @Override
    public Filme buscarPorId(Long id) {
        String sql = "SELECT ID, TITULO, GENERO, DURACAO, DIRETOR FROM FILME WHERE ID = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar filme por id", e);
        }
    }

    @Override
    public List<Filme> listarTodos() {
        String sql = "SELECT ID, TITULO, GENERO, DURACAO, DIRETOR FROM FILME ORDER BY ID";
        List<Filme> filmes = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                filmes.add(mapear(rs));
            }
            return filmes;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar filmes", e);
        }
    }

    @Override
    public Filme atualizar(Filme filme) {
        if (filme.getId() == null) {
            return salvar(filme);
        }

        String sql = "UPDATE FILME SET TITULO = ?, GENERO = ?, DURACAO = ?, DIRETOR = ? WHERE ID = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            preencherDados(ps, filme, 1);
            ps.setLong(5, filme.getId());
            int linhas = ps.executeUpdate();
            if (linhas == 0) {
                throw new RuntimeException("Filme nao encontrado para atualizacao: id=" + filme.getId());
            }
            return filme;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar filme", e);
        }
    }

    @Override
    public void excluir(Long id) {
        String sql = "DELETE FROM FILME WHERE ID = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir filme", e);
        }
    }

    private void preencherDados(PreparedStatement ps, Filme filme, int inicio) throws SQLException {
        ps.setString(inicio, filme.getTitulo());
        ps.setString(inicio + 1, filme.getGenero());
        ps.setInt(inicio + 2, filme.getDuracao());
        ps.setString(inicio + 3, filme.getDiretor());
    }

    private Filme mapear(ResultSet rs) throws SQLException {
        Filme filme = new Filme();
        filme.setId(rs.getLong("ID"));
        filme.setTitulo(rs.getString("TITULO"));
        filme.setGenero(rs.getString("GENERO"));
        filme.setDuracao(rs.getInt("DURACAO"));
        filme.setDiretor(rs.getString("DIRETOR"));
        return filme;
    }
}
