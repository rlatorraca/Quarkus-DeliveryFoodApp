package ca.com.rlsp.delivery.registration.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDTOValidator implements ConstraintValidator<ValidDTO, DTO>{

	@Override
	public void initialize(ValidDTO constraintAnnotation) {
		
	}
	
	@Override
	public boolean isValid(DTO dto, ConstraintValidatorContext context) {
		return dto.isValid(context);
	}

}
