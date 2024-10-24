package com.github.igorkoppen.repository;

import com.github.igorkoppen.model.Livro;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LivroRepository implements PanacheRepository<Livro> {

    public Uni<Boolean> existsByCategoriaId(Long categoriaId) {
        return find("categoriaId", categoriaId).firstResult()
                .onItem().ifNotNull().transform(livro -> true)
                .onItem().ifNull().continueWith(false);
    }
}
