package com.wirebarley.currencyCalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
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
		JsonNode result = currencyCalculatorService.getCurrencyData();
		model.addAttribute("test", result);
		
		return "main";
	}
}
