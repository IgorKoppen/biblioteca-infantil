package com.github.igorkoppen.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emprestimo_sequence")
    @SequenceGenerator(name = "emprestimo_sequence", sequenceName = "emprestimo_seq")
    private Long id;

    @Column(nullable = false)
    private LocalDate dataEmprestimo;
    @Column(nullable = false)
    private LocalDate dataDevolucao;

    private LocalDate dataDevolvido;

    @ManyToOne
    @JoinColumn(name = "livroId",nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    public Emprestimo(LocalDate dataEmprestimo, LocalDate dataDevolucao, LocalDate dataDevolvido, Livro livro, Usuario usuario) {
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.dataDevolvido = dataDevolvido;
        this.livro = livro;
        this.usuario = usuario;
    }

    public Emprestimo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public LocalDate getDataDevolvido() {
        return dataDevolvido;
    }

    public void setDataDevolvido(LocalDate dataDevolvido) {
        this.dataDevolvido = dataDevolvido;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Emprestimo that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
