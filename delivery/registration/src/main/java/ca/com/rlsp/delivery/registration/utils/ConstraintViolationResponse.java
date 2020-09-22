package ca.com.rlsp.delivery.registration.utils;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

public class ConstraintViolationResponse {

    private final List<ConstraintViolationImpl> violationsList = new ArrayList<>();

    private ConstraintViolationResponse(ConstraintViolationException exception) {
        exception.getConstraintViolations().forEach(violation -> violationsList.add(ConstraintViolationImpl.of(violation)));
    }

    public static ConstraintViolationResponse of(ConstraintViolationException exception) {
        return new ConstraintViolationResponse(exception);
    }

	public List<ConstraintViolationImpl> getViolationsList() {
		return violationsList;
	}

	  

}