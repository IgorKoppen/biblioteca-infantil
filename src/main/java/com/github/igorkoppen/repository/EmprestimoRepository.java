package com.github.igorkoppen.repository;

import com.github.igorkoppen.model.Emprestimo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmprestimoRepository implements PanacheRepository<Emprestimo> {
}
