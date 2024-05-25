package com.nniett.kikaishin.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KikaishinAPI {
	private static final Logger logger = LoggerFactory.getLogger(KikaishinAPI.class);
	public static void main(String[] args) {
		logger.debug("KikaishinAPI kick-started.");
		SpringApplication.run(KikaishinAPI.class, args);
	}
}
