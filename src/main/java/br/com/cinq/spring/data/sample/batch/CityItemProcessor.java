package br.com.cinq.spring.data.sample.batch;

import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;

import org.springframework.batch.item.ItemProcessor;

/**
 * Processor to convert CityDTO to an City entity of the database
 * 
 * @author vfontoura
 *
 */
public class CityItemProcessor implements ItemProcessor<CityDTO, City> {

	@Override
	public City process(CityDTO city) throws Exception {

		String cityName = city.getCity();
		City cityRet = new City();
		cityRet.setName(cityName);
		Country countryRet = new Country();
		String countryName = city.getCountry();
		countryRet.setName(countryName);
		cityRet.setCountry(countryRet);
		return cityRet;
	}
}