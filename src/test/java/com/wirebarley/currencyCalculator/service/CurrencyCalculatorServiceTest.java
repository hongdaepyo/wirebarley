package com.wirebarley.currencyCalculator.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.wirebarley.currencyCalculator.dto.ExchangeRateResponseDTO;

@SpringBootTest
public class CurrencyCalculatorServiceTest {
	@Autowired
	private CurrencyCalculatorService service;
	
	@Test
	public void getCurrencyDataTest() {
		try {
			String targetCountry = "KRW";
			
			JsonNode data = service.getCurrencyData(targetCountry);
			
			assertThat(data).isNotNull();
			assertThat(data.get("success").asBoolean()).isEqualTo(true);
			assertThat(data.get("quotes").size()).isEqualTo(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void makeExchageRateResponseTest() {
		JsonNode jsonData = null;
		try {
			jsonData = service.getCurrencyData("KRW");
			ExchangeRateResponseDTO dto = service.makeExchageRateResponse(jsonData);
			
			assertThat(dto).isNotNull();
			assertThat(dto.getFromCountry().length()).isEqualTo(3);
			assertThat(dto.getRate()).matches("([\\d,]*\\.\\d{2})");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
