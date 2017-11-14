package br.com.cinq.spring.data.sample.repository;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * CityRepository interface to provide City data access
 * 
 * @author vfontoura
 *
 */
public interface CityRepository extends CrudRepository<City, Long> {

	List<City> findByCountryNameStartingWith(String country);

	List<City> findByCountry(Country country);

}
