package com.wirebarley.currencyCalculator.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wirebarley.currencyCalculator.config.YamlApi;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CurrencyCalculatorService {
	
	@NonNull
	private YamlApi yamlApi;
	
	public JsonNode getCurrencyData(String targetContry) throws Exception {
		String targetUrl = yamlApi.getUrl() + "&currencies=" + targetContry;
		
		log.info("targetUrl = " + targetUrl);
		
		return callCurrencyAPI(targetUrl);
	}
	
	public JsonNode getCurrencyData() throws Exception {
		String targetUrl = yamlApi.getUrl() + "&currencies=" + String.join(",", yamlApi.getCurrencies());
		
		log.info("targetUrl = " + targetUrl);
		
		return callCurrencyAPI(targetUrl);
	}
	
	public JsonNode callCurrencyAPI(String targetUrl) {
		String result = null;
		JsonNode currency = null;
		
		try {
			URL url = new URL(targetUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			result = br.readLine();
			
			ObjectMapper mapper = new ObjectMapper();
			currency = mapper.readTree(result);
			
			conn.disconnect();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return currency;
	}
}
