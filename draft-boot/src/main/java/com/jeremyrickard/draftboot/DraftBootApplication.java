package com.jeremyrickard.draftboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.jeremyrickard.draftboot.models"})
public class DraftBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DraftBootApplication.class, args);
	}

}