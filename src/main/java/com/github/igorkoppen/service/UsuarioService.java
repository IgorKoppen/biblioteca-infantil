package com.github.igorkoppen.service;

import com.github.igorkoppen.dto.UsuarioDTO;
import com.github.igorkoppen.exception.AlreadyInUseEmailException;
import com.github.igorkoppen.exception.DatabaseOperationException;
import com.github.igorkoppen.exception.ResourceNotFoundException;
import com.github.igorkoppen.model.Usuario;
import com.github.igorkoppen.repository.*;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class UsuarioService {

    @Inject
    Logger logger;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    EmprestimoRepository emprestimoRepository;


    @WithTransaction
    public Uni<UsuarioDTO> create(UsuarioDTO usuarioDTO) {
        return usuarioRepository.findByEmail(usuarioDTO.getEmail())
                .onItem().ifNotNull().failWith(() ->
                        new AlreadyInUseEmailException("O email já está sendo usado por outro usuário."))
                .onItem().ifNull().continueWith(() -> {
                    Usuario usuario = new Usuario();
                    copyToEntity(usuario, usuarioDTO);
                    return usuario;
                })
                .onItem().transformToUni(usuario ->
                        usuarioRepository.persistAndFlush(usuario)
                                .onItem().ifNotNull().transform(this::toDTO)
                                .onFailure().recoverWithUni(() ->
                                        Uni.createFrom().failure(new DatabaseOperationException("Falha ao criar o usuário!"))
                                )
                );
    }

    @WithSession
    public Uni<UsuarioDTO> findById(Long id) {
        return usuarioRepository.findById(id)
                .onItem().ifNotNull().transform(this::toDTO)
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));
    }


    @WithSession
    public Uni<List<UsuarioDTO>> findAll() {
        return usuarioRepository.listAll()
                .map(usuarios -> usuarios.stream().map(this::toDTO).toList());
    }

    @WithTransaction
    public Uni<UsuarioDTO> update(Long id, UsuarioDTO usuarioDTO) {
        return usuarioRepository.findById(id)
                .onItem().ifNotNull().transformToUni(existingUsuario -> {
                    copyToEntity(existingUsuario, usuarioDTO);
                    return usuarioRepository.persist(existingUsuario)
                            .replaceWith(existingUsuario);
                })
                .map(this::toDTO)
                .invoke(updatedUser -> logger.info("Usuário atualizado: " + updatedUser.getNome()))
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));
    }

    @WithTransaction
    public Uni<Void> delete(Long id) {
        return usuarioRepository.findById(id)
                .onItem().ifNotNull().transformToUni(existingLivro ->
                        emprestimoRepository.findByUsuarioId(id)
                                .onItem().ifNotNull().transformToUni(emprestimo ->
                                        Uni.createFrom().failure(
                                                new DatabaseOperationException("Não é possível excluir o usuário enquanto ele tiver emprestimos pendentes."))
                                )
                                .onItem().ifNull().switchTo(() -> {
                                    logger.error("tentou deletar");
                                    return usuarioRepository.deleteById(id).onItem().ifNull().failWith(() -> new DatabaseOperationException("Erro ao deletar o usuário com ID: " + id));
                                })
                )
                .onFailure().invoke(ex -> logger.error("Erro ao deletar o usuário com ID: " + id, ex))
                .onItem().ifNull().failWith(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"))
                .replaceWithVoid();
    }

    private void copyToEntity(Usuario usuario, UsuarioDTO usuarioDTO) {
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setTipo(usuarioDTO.getTipo());
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getTipo());
    }
}

