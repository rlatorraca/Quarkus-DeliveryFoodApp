package ca.com.rlsp.delivery.registration;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name= "dish")
public class Dish extends PanacheEntityBase {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public String name;
	
	public String description;
	
	@ManyToOne
	public Restaurant restaurant;
	
	public BigDecimal price;
	
}
