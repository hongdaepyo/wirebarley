package com.wirebarley.currencyCalculator.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.wirebarley.currencyCalculator.dto.ExchangeRateResponseDTO;
import com.wirebarley.currencyCalculator.service.CurrencyCalculatorService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping
@AllArgsConstructor
@Log4j2
public class CurrencyWebController {
	
	private CurrencyCalculatorService currencyCalculatorService;
	
	@GetMapping("/main")
	public String main(Model model) throws Exception {
		return "main";
	}
	
	@PostMapping("/currency")
	@ResponseBody
	public ExchangeRateResponseDTO getCurrency(HttpServletRequest request) throws Exception {
		String targetCountry = request.getParameter("country");
		
		log.info("targetCountry = {}", targetCountry);
		
		JsonNode currencyData = currencyCalculatorService.getCurrencyData(targetCountry);
		ExchangeRateResponseDTO result = currencyCalculatorService.makeExchageRateResponse(currencyData);
		
		return result;
	}
}
