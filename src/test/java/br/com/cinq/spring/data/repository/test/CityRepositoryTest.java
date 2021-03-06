package br.com.cinq.spring.data.repository.test;

//import br.com.cinq.spring.data.sample.entity.City;
//import br.com.cinq.spring.data.sample.entity.Country;
//import br.com.cinq.spring.data.sample.repository.CityRepository;
import br.com.cinq.spring.data.Application;
import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.sample.entity.Country;
import br.com.cinq.spring.data.sample.repository.CityRepository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Eye candy: implements a sample in using JpaRespositories
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
public class CityRepositoryTest {

    @Autowired
    private CityRepository dao;

    @Test
    public void testQueryPerson() {

        Assert.assertNotNull(dao);
        
        Assert.assertTrue(dao.count() > 0);

        Country country = new Country();
        country.setId(3); // Should be France

        List<City> list = dao.findByCountry(country);

        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testFindByCountryNameStartingWith() {

        Assert.assertNotNull(dao);

        List<City> cities = dao.findByCountryNameStartingWith("Uni");

        Assert.assertEquals(3, cities.size());

    }
}
