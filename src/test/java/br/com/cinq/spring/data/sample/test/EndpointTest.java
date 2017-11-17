package br.com.cinq.spring.data.sample.test;

// import br.com.cinq.spring.data.sample.entity.City;
import br.com.cinq.spring.data.Application;
import br.com.cinq.spring.data.sample.entity.City;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import jersey.repackaged.com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class EndpointTest {

    Logger logger = LoggerFactory.getLogger(EndpointTest.class);

    private final String localhost = "http://localhost:";

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
   

    @Test
    public void testGetCities() throws InterruptedException {

        String country = "France";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder =
            UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities/").queryParam("country",
                country);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<City[]> response =
            this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, City[].class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Thread.sleep(2000L);

        City[] cities = response.getBody();

        Assert.assertEquals(2, cities.length);

    }
    
    
    @Test
    public void testGetAllCities() throws InterruptedException {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities/");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<City[]> response =
            this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, City[].class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Thread.sleep(2000L);

        City[] cities = response.getBody();

        Assert.assertEquals(9, cities.length);

    }

    @Test
    public void testGetEmptyCities() throws InterruptedException {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder =
            UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities/").queryParam("country",
                "Thailand");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response =
            this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, Void.class);

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testPutCities() throws JsonParseException, JsonMappingException, IOException {

        String jsonCities =
            "[{\"name\":\"Ponta Grossa\",\"country\":{\"name\":\"Brazil\"}}"
                + ",{\"name\":\"Cascavel\",\"country\":{\"name\":\"Brazil\"}}]";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities/");

        ObjectMapper objectMapper = new ObjectMapper();
        List<City> cities = objectMapper.readValue(jsonCities, new TypeReference<List<City>>() {
        });
        HttpEntity<?> entity = new HttpEntity<>(cities,headers);
        
        ResponseEntity<Void> response =
            this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.PUT, entity, Void.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String country = "Brazil";

        HttpHeaders headersGet = new HttpHeaders();
        headersGet.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        builder =
            UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities/").queryParam("country",
                country);

        entity = new HttpEntity<>(headers);

        ResponseEntity<City[]> responseGet =
            this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, City[].class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        City[] citiesGet = responseGet.getBody();

        Assert.assertEquals(6, citiesGet.length);

    }
    
    
    @Test
    public void testPutEmptyList() throws JsonParseException, JsonMappingException, IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/rest/cities/");

        List<City> cities = Lists.newArrayList();
        HttpEntity<?> entity = new HttpEntity<>(cities,headers);
        
        ResponseEntity<Void> response =
            this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.PUT, entity, Void.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
