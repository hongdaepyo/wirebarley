package com.wirebarley.currencyCalculator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyWebControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void mainTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/main"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("main"));
	}
	
	@Test
	public void getCurrencyTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/currency")
				.param("country", "KRW"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(print())
				.andReturn();
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode responseJson = mapper.readTree(actualResponseBody);
		
		assertThat(responseJson.get("success").asBoolean()).isEqualTo(true);
		assertThat(responseJson.get("fromCountry").asText()).isEqualTo("USD");
		assertThat(responseJson.get("toCountry").asText()).isEqualTo("KRW");
		assertThat(responseJson.get("rate").asText()).matches("([\\d,]*\\.\\d{2})");
	}
}
