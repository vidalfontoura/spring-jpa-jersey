package br.com.cinq.spring.data.resource;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.repository.CityRepository;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * City Resource to provide operations for the City entity.
 * 
 * @author vfontoura
 *
 */
@Component
@Path("/cities")
public class CityResource {

    private static final String GET_CITIES_MSG = "Querying cities with country name {}";

    private static final String SAVING_CITIES_MSG = "Saving {} cities";

    private Logger LOGGER = LoggerFactory.getLogger(CityResource.class);

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

        LOGGER.info(GET_CITIES_MSG, country);
        return Optional
            .ofNullable(country)
            .map(param -> cityRepository.findByCountryNameStartingWith(country))
            .map(
                cities -> cities.isEmpty() ? Response.status(Status.NOT_FOUND).build() : Response.status(Status.OK)
                    .entity(cities).build())
            .orElse(Response.status(Status.OK)
                    .entity(cityRepository.findAll()).build());
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

        LOGGER.info(SAVING_CITIES_MSG);
        if (cities.isEmpty()) {
            return Response.status(Status.BAD_REQUEST).build();
        }
		cityRepository.save(cities);
        return Response.ok().build();

	}

}
