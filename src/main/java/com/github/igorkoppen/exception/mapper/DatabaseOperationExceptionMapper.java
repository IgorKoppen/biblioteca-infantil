package com.github.igorkoppen.exception.mapper;

import com.github.igorkoppen.exception.DatabaseOperationException;
import com.github.igorkoppen.exception.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class DatabaseOperationExceptionMapper implements ExceptionMapper<DatabaseOperationException> {
    @Override
    public Response toResponse(DatabaseOperationException e) {
        ErrorResponse errorResponse = new ErrorResponse("Operação falhou: " + e.getMessage(),
                LocalDateTime.now(),
                Response.Status.NOT_FOUND.getStatusCode());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}