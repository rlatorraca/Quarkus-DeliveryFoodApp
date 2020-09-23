package ca.com.rlsp.delivery.registration.utils;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class ConstraintViolationImpl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Atribute Path, ex: initial date, person.address.number", required = false)
    private final String attribute;

    @Schema(description = "Descriptive error message possibly associated with the path" , required = true)
    private final String message;

    private ConstraintViolationImpl(ConstraintViolation<?> violation) {
        this.message = violation.getMessage();
        this.attribute = Stream.of(violation.getPropertyPath().toString().split("\\.")).skip(2).collect(Collectors.joining("."));
    }

    public ConstraintViolationImpl(String atributo, String mensagem) {
        this.message = mensagem;
        this.attribute = atributo;
    }

    public static ConstraintViolationImpl of(ConstraintViolation<?> violation) {
        return new ConstraintViolationImpl(violation);
    }

    public static ConstraintViolationImpl of(String violation) {
        return new ConstraintViolationImpl(null, violation);
    }

	public String getAttribute() {
		return attribute;
	}

	public String getMessage() {
		return message;
	}

   
}
