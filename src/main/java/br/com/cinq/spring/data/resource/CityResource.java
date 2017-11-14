package br.com.cinq.spring.data.resource;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Path("/cities")
/**
 * City Resource to provide operations for the City entity.
 * 
 * @author vfontoura
 *
 */
public class CityResource {

    Logger logger = LoggerFactory.getLogger(CityResource.class);

	@Autowired
	private CityRepository cityRepository;

	/**
	 * GET Method to retrieve a list of cities. If the query parameter country is
	 * provided will return a list of cities from the given country otherwise will
	 * return all the cities from the data store.
	 * 
	 * @param country
	 *            parameter to filter the cities.
	 * @return
	 */
	@GET
	@Produces("application/json")
    public Response getCitiesByCountryName(@QueryParam(value = "country") String country) {

		if (StringUtils.isEmpty(country)) {
            Iterable<City> findAll = cityRepository.findAll();
            return Response.ok(findAll).build();
		}
        List<City> findByCountryNameStartingWith = cityRepository.findByCountryNameStartingWith(country);
        return Response.ok(findByCountryNameStartingWith).build();

	}

	/**
	 * PUT method to insert a list of cities into the data store.
	 * 
	 * @param cities
	 *            to insert in the data store.
	 */
	@PUT
	@Consumes("application/json")
    public Response putCities(List<City> cities) {

		cityRepository.save(cities);
        return Response.ok().build();

	}

}
