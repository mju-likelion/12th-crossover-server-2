package org.example.crossoverserver2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CrossoverServer2Application {

	public static void main(String[] args) {
		SpringApplication.run(CrossoverServer2Application.class, args);
	}

}
