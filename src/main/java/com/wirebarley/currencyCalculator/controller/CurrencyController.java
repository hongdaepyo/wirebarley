package com.wirebarley.currencyCalculator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wirebarley.currencyCalculator.service.CurrencyCalculatorService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping
@AllArgsConstructor
@Log4j2
public class CurrencyController {
	
	private CurrencyCalculatorService currencyCalculatorService;
	
	@GetMapping("/main")
	public String main() {
		String result = currencyCalculatorService.getCurrencyData();
		
		log.debug(result);
		return result;
	}
}
