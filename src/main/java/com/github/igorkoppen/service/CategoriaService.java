package com.github.igorkoppen.service;


import com.github.igorkoppen.dto.CategoriaDTO;
import com.github.igorkoppen.exception.DatabaseOperationException;
import com.github.igorkoppen.exception.ResourceNotFoundException;
import com.github.igorkoppen.model.Categoria;
import com.github.igorkoppen.repository.CategoriaRepository;
import com.github.igorkoppen.repository.LivroRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class CategoriaService {

    @Inject
    Logger logger;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    LivroRepository livroRepository;



    @WithTransaction
    public Uni<CategoriaDTO> create(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        copyToEntity(categoria,categoriaDTO);
        return categoriaRepository.persistAndFlush(categoria).onItem().ifNotNull().transform(this::toDTO);
    }

    @WithSession
    public Uni<CategoriaDTO> findById(Long id) {
        return categoriaRepository.findById(id)
                .onItem().ifNotNull().transform(this::toDTO)
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("Categoria com ID " + id + " não encontrado"));
    }


    @WithSession
    public Uni<List<CategoriaDTO>> findAll() {
        return categoriaRepository.listAll()
                .map(categorias -> categorias.stream().map(this::toDTO).toList());
    }

    @WithTransaction
    public Uni<CategoriaDTO> update(Long id, CategoriaDTO categoriaDTO) {
        return categoriaRepository.findById(id)
                .onItem().ifNotNull().transformToUni(categoria -> {
                    copyToEntity(categoria, categoriaDTO);
                    return categoriaRepository.persist(categoria);
                })
                .map(this::toDTO)
                .invoke(dto -> logger.info("Categoria atualizado: " + dto.getNome()))
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("Categoria com ID " + id + " não encontrado"));
    }


    @WithTransaction
    public Uni<Void> delete(Long id) {
        return categoriaRepository.findById(id)
                .onItem().ifNotNull().transformToUni(existingLivro ->
                        livroRepository.findById(id)
                                .onItem().ifNotNull().transformToUni(emprestimo ->
                                        Uni.createFrom().failure(
                                                new DatabaseOperationException("Não é possível excluir o Categoria que está vinculada a um filme."))
                                )
                                .onItem().ifNull().switchTo(() -> {
                                    logger.error("tentou deletar");
                                    return categoriaRepository.deleteById(id).onItem().ifNull().failWith(() -> new DatabaseOperationException("Erro ao deletar o categoria com ID: " + id));
                                })
                )
                .onFailure().invoke(ex -> logger.error("Erro ao deletar o categoria com ID: " + id, ex))
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("categoria com ID " + id + " não encontrado"))
                .replaceWithVoid();
    }

    private void copyToEntity(Categoria categoria, CategoriaDTO categoriaDTO) {
        categoria.setNome(categoriaDTO.getNome());
    }

    private CategoriaDTO toDTO(Categoria categoria) {
        return new CategoriaDTO(
               categoria.getId(),
                categoria.getNome());
    }
}
