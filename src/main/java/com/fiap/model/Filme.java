package com.fiap.model;

import java.util.Objects;

public class Filme {
    private Long id;
    private String titulo;
    private String genero;
    private int duracao;
    private String diretor;

    public Filme() {
    }

    public Filme(String titulo, String genero, int duracao, String diretor) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracao = duracao;
        this.diretor = diretor;
    }

    public Filme(Long id, String titulo, String genero, int duracao, String diretor) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.duracao = duracao;
        this.diretor = diretor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Filme filme = (Filme) o;
        return duracao == filme.duracao &&
                Objects.equals(id, filme.id) &&
                Objects.equals(titulo, filme.titulo) &&
                Objects.equals(genero, filme.genero) &&
                Objects.equals(diretor, filme.diretor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, genero, duracao, diretor);
    }

    @Override
    public String toString() {
        return "Filme{id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", duracao=" + duracao +
                ", diretor='" + diretor + '\'' +
                '}';
    }
}
