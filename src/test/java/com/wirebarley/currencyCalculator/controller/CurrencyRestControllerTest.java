package com.wirebarley.currencyCalculator.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyControllerTest {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	public void mainTest() {
		ResponseEntity<String> result = restTemplate.getForEntity("/main", String.class);
		
		String currecyData = result.getBody();
		log.info(currecyData);
		
		assertThat(currecyData).isNotNull();
	}
	
}
