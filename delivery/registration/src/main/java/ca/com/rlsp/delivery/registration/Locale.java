package ca.com.rlsp.delivery.registration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name= "localization")
public class Locale extends PanacheEntityBase {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public double latitudee;
	
	public double longetude;
}
