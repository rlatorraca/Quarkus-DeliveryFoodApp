package ca.com.rlsp.delivery.registration.dto;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ca.com.rlsp.delivery.registration.model.Restaurant;
import ca.com.rlsp.delivery.registration.utils.DTO;
import ca.com.rlsp.delivery.registration.utils.ValidDTO;

@ValidDTO
public class AddRestaurantDTO implements DTO{

	
	@NotEmpty
	@NotNull
	public String owner;
	
	@Size(min=3, max=50)
	public String nameOnCRA;
	
	@Pattern(regexp="[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")
	@NotNull
	public String registerNumber;
	
	public MappositionDTO mapPosition;
	
	
	 @Override
	    public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
	        constraintValidatorContext.disableDefaultConstraintViolation();
	        if (Restaurant.find("registerNumber", registerNumber).count() > 0) {
	            constraintValidatorContext.buildConstraintViolationWithTemplate("Duplicate Register number on CRA database")
	                    .addPropertyNode("registerNumber")
	                    .addConstraintViolation();
	            return false;
	        }
	        return true;
	    }



}
