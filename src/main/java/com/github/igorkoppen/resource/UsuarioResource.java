package com.github.igorkoppen.resource;

import com.github.igorkoppen.dto.UsuarioDTO;
import com.github.igorkoppen.service.UsuarioService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.net.URI;
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @Inject
    UsuarioService usuarioService;


    @POST
    public Uni<Response> create(@Valid UsuarioDTO usuarioDTO) {
        return usuarioService.create(usuarioDTO)
                .map(createdUsuario -> Response.created(URI.create("/usuarios/" + createdUsuario.getId()))
                        .entity(createdUsuario)
                        .build());
    }

    @GET
    @Path("/{id}")
    public Uni<Response> findById(@PathParam("id") Long id) {
        return usuarioService.findById(id)
                .map(usuario -> Response.ok(usuario).build());
    }

    @GET
    public Uni<Response> findAll() {
        return usuarioService.findAll().map(usuarios -> Response.ok(usuarios).build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> update(@PathParam("id") Long id, @Valid UsuarioDTO usuarioDTO) {
        return usuarioService.update(id, usuarioDTO)
                .map(updatedUsuario -> Response.ok(updatedUsuario).build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return usuarioService.delete(id)
                .replaceWith(Response.noContent().build());
    }


}