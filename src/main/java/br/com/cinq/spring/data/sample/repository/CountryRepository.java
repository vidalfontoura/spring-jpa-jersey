package br.com.cinq.spring.data.sample.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cinq.spring.data.sample.entity.Country;

/**
 * CountryRepository to provide Country data access
 * 
 * @author vfontoura
 *
 */
public interface CountryRepository extends CrudRepository<Country, Long> {

	List<Country> findByNameStartingWith(String name);

}
