package com.github.igorkoppen.service;

import com.github.igorkoppen.model.Usuario;
import com.github.igorkoppen.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;

    

}
