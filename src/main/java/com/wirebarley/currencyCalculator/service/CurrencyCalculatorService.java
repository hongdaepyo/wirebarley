package com.wirebarley.currencyCalculator.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

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
	
	public String getCurrencyData() {
		String targetUrl = yamlApi.getBaseurl() + yamlApi.getEndpoint()
						+ "?access_key=" + yamlApi.getAccesskey();
		
		log.info("targetUrl = " + targetUrl);
		
		String result = null;
		try {
			URL url = new URL(targetUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			result = br.readLine();
			
			conn.disconnect();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
