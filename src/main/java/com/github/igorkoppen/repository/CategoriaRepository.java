package com.github.igorkoppen.repository;

import com.github.igorkoppen.model.Categoria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaRepository  implements PanacheRepository<Categoria> {
}
