package br.com.cinq.spring.data.sample.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Greeting Service.
 */
@SpringBootApplication
// @ComponentScan(basePackages = { "br.com.cinq.spring.data.sample" })
@EntityScan(basePackages = { "br.com.cinq.spring.data.sample.entity" })
@EnableJpaRepositories("br.com.cinq.spring.data.sample.repository")
@EnableAutoConfiguration
public class Application {


	public static void main(String[] args) {

        SpringApplication.run(Application.class);
	}
}
