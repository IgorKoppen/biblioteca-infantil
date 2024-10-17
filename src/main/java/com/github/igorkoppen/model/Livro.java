package com.github.igorkoppen.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "livro_sequence")
    @SequenceGenerator(name = "livro_sequence", sequenceName = "livro_seq")
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private Boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "autorId",nullable = false)
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "categoriaId",nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "livro")
    private List<Emprestimo> emprestimos;

    public Livro(Long id, String titulo, Boolean disponivel, Autor autor, Categoria categoria, List<Emprestimo> emprestimos) {
        this.id = id;
        this.titulo = titulo;
        this.disponivel = disponivel;
        this.autor = autor;
        this.categoria = categoria;
        this.emprestimos = emprestimos;
    }

    public Livro() {
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

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livro livro)) return false;
        return Objects.equals(id, livro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
