package com.github.igorkoppen.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class LivroDTO {

    private Long id;

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @NotNull(message = "O autor ID é obrigatório.")
    private Long autorId;

    @NotNull(message = "A categoria ID é obrigatória.")
    private Long categoriaId;


    private Boolean disponivel;

    public LivroDTO() {
    }

    public LivroDTO(String titulo, Long autorId, Long categoriaId, Boolean disponivel) {
        this.titulo = titulo;
        this.autorId = autorId;
        this.categoriaId = categoriaId;
        this.disponivel = disponivel;
    }

    public LivroDTO(Long id, String titulo, Long autorId, Long categoriaId, Boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autorId = autorId;
        this.categoriaId = categoriaId;
        this.disponivel = disponivel;
    }

    public Long getId() {
        return id;
    }


    public String getTitulo() {
        return titulo;
    }


    public Long getAutorId() {
        return autorId;
    }


    public Long getCategoriaId() {
        return categoriaId;
    }


    public Boolean getDisponivel() {
        return disponivel;
    }

}