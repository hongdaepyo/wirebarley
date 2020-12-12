package com.wirebarley.currencyCalculator.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wirebarley.currencyCalculator.config.YamlApi;
import com.wirebarley.currencyCalculator.dto.ExchangeRateResponseDTO;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CurrencyCalculatorService {
	
	@NonNull
	private YamlApi yamlApi;
	
	public JsonNode getCurrencyData(String targetCountry) throws Exception {
		String targetUrl = yamlApi.getUrl();
		if (targetCountry != null && !targetCountry.equals("")) {
			targetUrl += "&currencies=" + targetCountry;
		}
		
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
	
	public ExchangeRateResponseDTO makeExchageRateResponse(JsonNode currencyData) {
		boolean success = currencyData.get("success").booleanValue();
		
		if (!success) {
			return new ExchangeRateResponseDTO(false, "", "", "");
		}
		
		JsonNode exchageRateData = currencyData.get("quotes");
		String key = exchageRateData.fieldNames().next();
		String fromCountry = key.substring(0, 3);
		String toCountry = key.substring(3);
		double rate = exchageRateData.get(key).doubleValue();
		String strRate = getStringExchangeRate(rate);
		
		log.info(rate);
		
		return new ExchangeRateResponseDTO(success, fromCountry, toCountry, strRate);
	}
	
	private String getStringExchangeRate(double rate) {
		StringBuilder sb = new StringBuilder();
		double tempRate = (double)Math.round(rate * 100) / 100;
		String tempString = String.format("%.2f", tempRate);
		
		if (tempRate > 1000) {
			int lastDotPos = -1;
			for (int i = tempString.length() - 1; i >= 0; i--) {
				char ch = tempString.charAt(i);
				
				if (lastDotPos > 0 && (lastDotPos - i) % 3 == 0) {
					sb.append(ch).append(',');
				} else {
					sb.append(ch);
				}
				
				if (ch == '.') {
					lastDotPos = i;
				}
			}
		}
		
		return sb.reverse().toString();
	}
}
