package com.github.igorkoppen.repository;

import com.github.igorkoppen.model.Emprestimo;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class EmprestimoRepository implements PanacheRepository<Emprestimo> {

    public Uni<Emprestimo> findByLivroId(Long livroId) {
        return find("livro.id", livroId).firstResult();
    }


    public Uni<Emprestimo> findByUsuarioId(Long usuarioId) {
        return find("usuario.id", usuarioId).firstResult();
    }

}
