package com.analyticsfox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.analyticsfox","com.analyticsfox.advice","com.analyticsfox.config","com.analyticsfox.constants","com.analyticsfox.controller","com.analyticsfox.dto"
		,"com.analyticsfox.exception","com.analyticsfox.model","com.analyticsfox.repositories","com.analyticsfox.service","com.analyticsfox.service.Implementation"})
@EntityScan("com.analyticsfox.model")
public class LFIFinhaatService {

	public static void main(String[] args) {
		SpringApplication.run(LFIFinhaatService.class, args);
	}

}
