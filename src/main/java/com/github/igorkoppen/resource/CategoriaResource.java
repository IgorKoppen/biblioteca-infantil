package com.github.igorkoppen.resource;


import com.github.igorkoppen.dto.CategoriaDTO;
import com.github.igorkoppen.service.CategoriaService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @POST
    public Uni<Response> create(@Valid CategoriaDTO categoriaDTO) {
        return categoriaService.create(categoriaDTO)
                .map(categoriaDTOCreated -> Response.created(URI.create("/categorias/" + categoriaDTOCreated.getId()))
                        .entity(categoriaDTOCreated)
                        .build());
    }

    @GET
    @Path("/{id}")
    public Uni<Response> findById(@PathParam("id") Long id) {
        return categoriaService.findById(id)
                .map(categoria -> Response.ok(categoria).build());
    }

    @GET
    public Uni<Response> findAll() {
        return categoriaService.findAll().map(categoria -> Response.ok(categoria).build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> update(@PathParam("id") Long id, @Valid CategoriaDTO categoriaDTO) {
        return categoriaService.update(id, categoriaDTO)
                .map(updatedCategoria -> Response.ok(updatedCategoria).build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return categoriaService.delete(id)
                .replaceWith(Response.noContent().build());
    }

}
