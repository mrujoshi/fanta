package com.fanta.fanta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
		excludeFilters = {
				@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.fanta.fanta.dataLoader.*")
		})
public class FantaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantaApplication.class, args);
	}

}
