package ca.com.rlsp.delivery.registration;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name= "restaurant")
public class Restaurant extends PanacheEntityBase {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public String owner;
	
	public String name;
	
	public String registerNumber;
	
	@OneToOne
	public Localization localization;
	
	@CreationTimestamp
	public Date createdDate;
	
	@UpdateTimestamp
	public Date updatedDate;
	
	
}
