package br.com.cinq.spring.data.repository.test;

import br.com.cinq.spring.data.sample.application.Application;
//import br.com.cinq.spring.data.sample.entity.Country;
//import br.com.cinq.spring.data.sample.repository.CountryRepository;
import br.com.cinq.spring.data.sample.entity.Country;
import br.com.cinq.spring.data.sample.repository.CountryRepository;

import java.util.Iterator;
import java.util.List;

import jersey.repackaged.com.google.common.collect.Iterators;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
@IntegrationTest("server.port=9000")
@ActiveProfiles("unit")
public class CountryRepositoryTest {

	@Autowired
	private CountryRepository dao;

	@Test
	public void testGelAllCountries() {

		Assert.assertNotNull(dao);

		long count = dao.count();

		Assert.assertTrue(count > 0);

		Iterator<Country> countries = dao.findAll().iterator();

		Assert.assertEquals((int) count, Iterators.size(countries));

	}

	@Test
	public void testFindOneCountry() {

		Assert.assertNotNull(dao);

		List<Country> countries = dao.findByNameStartingWith("Fra");

		Assert.assertEquals(1, countries.size());

	}

}
