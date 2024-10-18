package com.github.igorkoppen.repository;

import com.github.igorkoppen.model.Livro;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LivroRepository implements PanacheRepository<Livro> {
}
