package com.omeshwar.project.airBNB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AirBnbCloneBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirBnbCloneBackendApplication.class, args);
	}

}
