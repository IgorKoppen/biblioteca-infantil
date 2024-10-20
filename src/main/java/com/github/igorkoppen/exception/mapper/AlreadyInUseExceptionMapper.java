package com.github.igorkoppen.exception.mapper;

import com.github.igorkoppen.exception.AlreadyInUseException;
import com.github.igorkoppen.exception.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class AlreadyInUseExceptionMapper implements ExceptionMapper<AlreadyInUseException> {
    @Override
    public Response toResponse(AlreadyInUseException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
                LocalDateTime.now(),
                Response.Status.BAD_REQUEST.getStatusCode());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}