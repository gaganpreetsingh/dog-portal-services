package org.uk.dog.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DogPortalServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogPortalServicesApplication.class, args);
	}

}
