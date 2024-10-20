package com.github.igorkoppen.dto;

import com.github.igorkoppen.model.TipoUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class UsuarioDTO {


    private Long id;
    @NotEmpty(message = "O nome não pode estar vazio.")
    private String nome;

    @NotEmpty(message = "O email não pode estar vazio.")
    private String email;

    @NotEmpty(message = "A senha não pode estar vazia.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @NotNull(message = "tipo não pode estar vazio.")
    private TipoUser tipo;

    public UsuarioDTO(Long id, String nome, String email, String senha, TipoUser tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public UsuarioDTO(String nome, String email, String senha, TipoUser tipo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public UsuarioDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public TipoUser getTipo() {
        return tipo;
    }
}
