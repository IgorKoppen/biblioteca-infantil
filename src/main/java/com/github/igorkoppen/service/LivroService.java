package com.github.igorkoppen.service;


import com.github.igorkoppen.dto.LivroDTO;
import com.github.igorkoppen.exception.DatabaseOperationException;
import com.github.igorkoppen.exception.ResourceNotFoundException;
import com.github.igorkoppen.model.Autor;
import com.github.igorkoppen.model.Categoria;
import com.github.igorkoppen.model.Livro;
import com.github.igorkoppen.repository.AutorRepository;
import com.github.igorkoppen.repository.CategoriaRepository;
import com.github.igorkoppen.repository.EmprestimoRepository;
import com.github.igorkoppen.repository.LivroRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class LivroService {

    @Inject
    Logger logger;

    @Inject
    LivroRepository livroRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    AutorRepository autorRepository;

    @Inject
    EmprestimoRepository emprestimoRepository;

    @WithTransaction
    public Uni<LivroDTO> create(LivroDTO livroDTO) {
        Livro livro = new Livro();
        return populateEntity(livro, livroDTO)
                .flatMap(livroAtualizado -> livroRepository.persistAndFlush(livroAtualizado))
                .map(l -> {
                    logger.info("Livro criado: " + l.getTitulo());
                    return toDTO(l);
                });
    }

    @WithSession
    public Uni<LivroDTO> findById(Long id) {
        return livroRepository.findById(id)
                .onItem().ifNotNull().transform(this::toDTO)
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("Livro com ID " + id + " não encontrado"));
    }


    @WithSession
    public Uni<List<LivroDTO>> findAll() {
        return livroRepository.listAll()
                .map(livros -> livros.stream().map(this::toDTO).toList());
    }

    @WithTransaction
    public Uni<LivroDTO> update(Long id, LivroDTO livroDTO) {
        return livroRepository.findById(id)
                .onItem().ifNotNull().transformToUni(existingLivro -> {
                    populateEntity(existingLivro, livroDTO);
                    return livroRepository.persist(existingLivro)
                            .replaceWith(existingLivro);
                })
                .map(this::toDTO)
                .invoke(updatedLivro -> logger.info("Livro atualizado: " + updatedLivro.getTitulo()))
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("Livro com ID " + id + " não encontrado"));
    }

    @WithTransaction
    public Uni<Void> delete(Long id) {
        return livroRepository.findById(id)
                .onItem().ifNotNull().transformToUni(existingLivro ->
                        emprestimoRepository.findByLivroId(id)
                                .onItem().ifNotNull().transformToUni(emprestimo ->
                                        Uni.createFrom().failure(
                                                new DatabaseOperationException("Não é possível excluir o livro enquanto ele tiver emprestimos pendentes."))
                                )
                                .onItem().ifNull().switchTo(() -> {
                                    logger.error("tentou deletar");
                                    return livroRepository.deleteById(id).onItem().ifNull().failWith(() -> new DatabaseOperationException("Erro ao deletar o livro com ID: " + id));
                                })
                )
                .onFailure().invoke(ex -> logger.error("Erro ao deletar o livro com ID: " + id, ex))
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("Livro com ID " + id + " não encontrado"))
                .replaceWithVoid();
    }

    private Uni<Livro> populateEntity(Livro livro, LivroDTO livroDTO) {
        livro.setTitulo(livroDTO.getTitulo());
        livro.setDisponivel(livroDTO.getDisponivel());

        Uni<Autor> autorUni = autorRepository.findById(livroDTO.getAutorId())
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("Autor inválido com id: " + livroDTO.getAutorId()));

        Uni<Categoria> categoriaUni = categoriaRepository.findById(livroDTO.getCategoriaId())
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("Categoria inválida com id: " + livroDTO.getCategoriaId()));

        return autorUni
                .flatMap(autor -> categoriaUni.map(categoria -> {
                    livro.setAutor(autor);
                    livro.setCategoria(categoria);
                    livro.setDisponivel(true);
                    return livro;
                }));
    }

    private LivroDTO toDTO(Livro livro) {
        return new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor() != null ? livro.getAutor().getId() : null,
                livro.getCategoria() != null ? livro.getCategoria().getId() : null,
                livro.getDisponivel());
    }
}