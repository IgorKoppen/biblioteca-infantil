package com.github.igorkoppen.dto;


import com.github.igorkoppen.model.Categoria;
import jakarta.validation.constraints.NotBlank;

public class CategoriaDTO {

    private final Long id;
    @NotBlank(message = "Nome da categoria obrigatorio!")
    private String nome;

    public CategoriaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
