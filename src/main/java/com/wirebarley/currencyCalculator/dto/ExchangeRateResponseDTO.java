package com.wirebarley.currencyCalculator.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExchangeRateResponseDTO {
	private final boolean success;
	private final String fromCountry;
	private final String toCountry;
	private final String rate;
}
