package ca.com.rlsp.delivery.registration.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import ca.com.rlsp.delivery.registration.Locale;

public class AddRestaurantDTO {

	
	
	public String owner;
	
	public String name_on_CRA;
	
	public String registerNumber;
	
	public LocaleDTO localization;

}
