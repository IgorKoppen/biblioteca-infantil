package com.github.igorkoppen.resource;

import com.github.igorkoppen.dto.LivroDTO;
import com.github.igorkoppen.service.LivroService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/livros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LivroResource {
    @Inject
    LivroService livroService;

    @Context
    UriInfo uriInfo;

    @POST
    public Uni<Response> create(@Valid LivroDTO livroDTO) {
        return livroService.create(livroDTO)
                .map(createdLivro -> {
                    URI uri = UriBuilder.fromUri(uriInfo.getAbsolutePath())
                            .path(Long.toString(createdLivro.getId()))
                            .build();
                    return Response.created(uri)
                            .entity(createdLivro)
                            .link(uri, "self")
                            .build();
                });
    }

    @GET
    @Path("/{id}")
    public Uni<Response> findById(@PathParam("id") Long id) {
        return livroService.findById(id)
                .map(livro -> Response.ok(livro).build());
    }

    @GET
    public Uni<List<LivroDTO>> findAll() {
        return livroService.findAll();
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> update(@PathParam("id") Long id, @Valid LivroDTO livroDTO) {
        return livroService.update(id, livroDTO)
                .map(updatedLivro -> Response.ok(updatedLivro).build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return livroService.delete(id)
                .replaceWith(Response.noContent().build());
    }


}