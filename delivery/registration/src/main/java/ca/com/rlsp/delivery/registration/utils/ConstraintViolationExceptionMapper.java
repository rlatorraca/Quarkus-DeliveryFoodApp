package ca.com.rlsp.delivery.registration.utils;


import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider // Preve um Excecao especifica
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(Status.BAD_REQUEST).entity(ConstraintViolationResponse.of(exception)).build();
    }

}