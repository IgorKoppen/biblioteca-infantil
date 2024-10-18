package com.github.igorkoppen.repository;

import com.github.igorkoppen.model.Emprestimo;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class EmprestimoRepository implements PanacheRepository<Emprestimo> {

    public Uni<Long> countByLivroId(Long livroId) {
        return count("livro.id", livroId);
    }
}
