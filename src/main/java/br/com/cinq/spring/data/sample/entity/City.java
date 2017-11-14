package br.com.cinq.spring.data.sample.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * City Entity
 * 
 * @author vfontoura
 *
 */
@Entity
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	private Country country;

	public Country getCountry() {

		return country;
	}

	public void setCountry(Country country) {

		this.country = country;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

}
