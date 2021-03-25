package com.fastcode.timesheetapp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.fastcode.timesheetapp1","org.springframework.versions"})
public class Timesheetapp1Application {

	public static void main(String[] args) {
		SpringApplication.run(Timesheetapp1Application.class, args);
	}

}

