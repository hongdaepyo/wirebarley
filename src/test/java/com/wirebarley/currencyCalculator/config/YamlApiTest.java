package com.wirebarley.currencyCalculator.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class YamlApiTest {
	@Autowired
	private YamlApi yamlApi;
	
	@Test
	public void yamlApiTest() {
		String accssKey = yamlApi.getAccesskey();
		String baseUrl = yamlApi.getBaseurl();
		String[] currencies = yamlApi.getCurrencies();
		
		log.info("accessKey = {}, baseUrl = {}, currencies = {}", accssKey, baseUrl, currencies);
		
		assertThat(accssKey).isNotNull();
		assertThat(accssKey.length()).isEqualTo(32);
		assertThat(baseUrl).isEqualTo("http://api.currencylayer.com/");
	}
}
