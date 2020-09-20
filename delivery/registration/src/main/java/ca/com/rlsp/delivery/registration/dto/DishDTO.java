package ca.com.rlsp.delivery.registration.dto;

import java.math.BigDecimal;


public class DishDTO {

	public Long id;
	
	public String name;
	
	public String description;	
	
	public RestaurantDTO restaurant;
	
	public BigDecimal price;
}
