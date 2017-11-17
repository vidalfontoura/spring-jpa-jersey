package br.com.cinq.spring.data.sample.application;

import br.com.cinq.spring.data.resource.CityResource;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Register Jersey modules
 * 
 * @author Adriano Kretschmer
 */
@Configuration
@ApplicationPath("rest")
public class Config extends ResourceConfig {

	public Config() {
		register(CityResource.class);
	}


}