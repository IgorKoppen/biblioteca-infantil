package com.github.igorkoppen.exception.mapper;

import com.github.igorkoppen.exception.ErrorResponse;
import com.github.igorkoppen.exception.ResourceNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    @Override
    public Response toResponse(ResourceNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now(),
                Response.Status.NOT_FOUND.getStatusCode()
        );
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}